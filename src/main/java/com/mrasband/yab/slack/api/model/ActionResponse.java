package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrasband.yab.slack.api.model.core.Channel;
import com.mrasband.yab.slack.api.model.core.User;
import com.mrasband.yab.slack.rtm.event.messages.BaseMessage;
import lombok.Data;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Callback for actions (buttons) added to the channel.
 *
 * @author matt.rasband
 */
@Data
public class ActionResponse {
    @JsonProperty("callback_id")
    private String callbackId;

    private Team team;

    private Channel channel;

    private User user;

    @JsonProperty("action_ts")
    private String actionTimestamp;

    @JsonProperty("message_ts")
    private String messageTimestamp;

    @JsonProperty("attachment_id")
    private String attachmentId;

    @JsonProperty("original_message")
    private BaseMessage message;

    private String token;

    @JsonProperty("response_url")
    private String responseUrl;

    public String responsePath() throws MalformedURLException {
        return new URL(this.responseUrl).getPath();
    }
}
