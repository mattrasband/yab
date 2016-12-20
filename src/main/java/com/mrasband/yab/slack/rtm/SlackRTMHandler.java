package com.mrasband.yab.slack.rtm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrasband.yab.slack.api.SlackClient;
import com.mrasband.yab.slack.api.model.RTMConnection;
import com.mrasband.yab.slack.rtm.event.Event;
import com.mrasband.yab.slack.rtm.event.ReconnectUrl;
import com.mrasband.yab.slack.rtm.event.messages.BaseMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;
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
 * will be converted into the appropriate {@link com.mrasband.yab.slack.rtm.event.Event}
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

        this.slackClient.connect(this.botToken)
                .enqueue(new Callback<RTMConnection>() {
                    @Override
                    public void onResponse(Call<RTMConnection> call, Response<RTMConnection> response) {
                        if (!response.isSuccessful()) {
                            log.error("Non successful response type, unable to connect to Slack: %s",
                                    response.code());
                            return;
                        }

                        synchronized (handler) {
                            handler.wssUrl = response.body().getUrl();
                        }
                        connect();
                    }

                    @Override
                    public void onFailure(Call<RTMConnection> call, Throwable throwable) {
                        log.error("Failed to connect to slack", throwable);
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
            Event e = objectMapper.readValue(message.getPayload(), Event.class);

            // This is a hack around multiple nesting of polymorphic types with Jackson deserialization.
            // As far as I have been able to tell, Jackson neither supports this nor do they intend to:
            //      https://github.com/FasterXML/jackson-databind/issues/374
            // So instead we are just unmarshalling again with the root type as the message class instead.
            // It's a little wasteful to do it twice, but until we have metrics that it's an issue - whatever!
            if (!StringUtils.isEmpty(e.getType()) && e.getType().equals("message")) {
                e = objectMapper.readValue(message.getPayload(), BaseMessage.class);
            }

            if (e instanceof ReconnectUrl) {
                log.debug("Received new reconnect url");
                synchronized (this) {
                    this.wssUrl = ((ReconnectUrl) e).getUrl();
                }
            }

            log.debug("Event deserialized, broadcasting: {}", e);
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
