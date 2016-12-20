package com.mrasband.yab.slack.rtm.event.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrasband.yab.slack.rtm.event.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/message">message</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseMessage extends AbstractEvent {
    @JsonProperty("channel")
    private String channelId;

    private String text;

    @JsonProperty("ts")
    private String timestamp;

    // optional, likely null in most cases
    private Edit edited;

    @JsonProperty("user")
    private String userId;

    private String subtype;

    @Data
    @SuppressWarnings("WeakerAccess")
    public static class Edit {
        @JsonProperty("user")
        private String userId;

        @JsonProperty("ts")
        private String timestamp;
    }
}
