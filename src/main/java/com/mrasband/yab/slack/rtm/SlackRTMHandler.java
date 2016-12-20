package com.mrasband.yab.slack.rtm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrasband.yab.slack.api.SlackClient;
import com.mrasband.yab.slack.api.model.RTMStart;
import com.mrasband.yab.slack.rtm.event.AbstractEvent;
import com.mrasband.yab.slack.rtm.event.ReconnectUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

/**
 * Websocket handler specific for the concerns of the Slack RTM API. Messages received
 * will be converted into the appropriate {@link AbstractEvent}
 * type and broadcast as an application event. Any interested users can listen to the specific
 * types they care about, or all.
 *
 * @author matt.rasband
 */
@Slf4j
public class SlackRTMHandler extends AbstractWebSocketHandler {
    private final String botToken;
    private final SlackClient slackClient;
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    private String wssUrl;
    private WebSocketSession session;
    private WebSocketConnectionManager webSocketConnectionManager;

    public SlackRTMHandler(String botToken,
                           SlackClient slackClient,
                           ApplicationEventPublisher eventPublisher,
                           ObjectMapper objectMapper) {
        this.botToken = botToken;
        this.slackClient = slackClient;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;

        initConnection();
    }

    /**
     * This should only be necessary on the first connection, it gets the initial websocket url
     * from the slack client, later re-connections (if needed) should be able to use the websocket
     * reconnection url that is provided via the events.
     */
    private void initConnection() {
        SlackRTMHandler handler = this;

        this.slackClient.rtmStart(this.botToken)
                .enqueue(new Callback<RTMStart>() {
                    @Override
                    public void onResponse(Call<RTMStart> call, Response<RTMStart> response) {
                        if (!response.isSuccessful()) {
                            log.error("Non successful response type, unable to rtmStart to Slack: %s",
                                    response.code());
                            return;
                        }

                        synchronized (handler) {
                            handler.wssUrl = response.body().getUrl();
                        }
                        connect();
                    }

                    @Override
                    public void onFailure(Call<RTMStart> call, Throwable throwable) {
                        log.error("Failed to rtmStart to slack", throwable);
                    }
                });
    }

    /**
     * Connect and Start the websocket connection
     */
    private void connect() {
        webSocketConnectionManager = new WebSocketConnectionManager(new StandardWebSocketClient(),
                this, this.wssUrl);
        webSocketConnectionManager.start();
    }

    /**
     * On error, try to reconnect
     *
     * TODO: research a bit about the WebSocketConnectionManager
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("Websocket transport error", exception);
        this.webSocketConnectionManager.stop();
        this.connect();
    }

    /**
     * Unmarshal the message into an event type and broadcast that out for any interested listeners.
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        synchronized (this) {
            this.session = session;
        }

        log.info("Received event: {}", message.getPayload());

        try {
            AbstractEvent e = objectMapper.readValue(message.getPayload(), AbstractEvent.class);

            // TODO: manage membership state (channels, users, etc) - if we decide to care
            if (e instanceof ReconnectUrl) {
                log.debug("Received new reconnect url");
                synchronized (this) {
                    this.wssUrl = ((ReconnectUrl) e).getUrl();
                }
            }

            log.debug("AbstractEvent deserialized, broadcasting: {}", e);
            eventPublisher.publishEvent(e);
        } catch (Exception e) {
            log.error("Error deserializing:", e);
        }
    }

    /**
     * Handle sending a payload over the
     *
     * @param payload
     * @throws IOException
     */
    public void send(String payload) throws IOException {
        log.debug("Sending payload: {}", payload);
        this.session.sendMessage(new TextMessage(payload));
    }
}
