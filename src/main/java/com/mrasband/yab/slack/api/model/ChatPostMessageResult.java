package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrasband.yab.slack.api.model.messaging.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/methods/chat.postMessage>chat.postMessage</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChatPostMessageResult extends RPCResult {
    @JsonProperty("ts")
    private String timestamp;

    @JsonProperty("channel")
    private String channelId;

    private Message message;
}
