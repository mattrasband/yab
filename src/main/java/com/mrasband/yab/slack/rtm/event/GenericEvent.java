package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenericEvent extends AbstractEvent {
    private Boolean ok;

    @JsonProperty("reply_to")
    private String replyTo;

    @JsonProperty("ts")
    private String timestamp;

    private String text;
}
