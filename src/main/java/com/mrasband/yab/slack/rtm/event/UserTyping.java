package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserTyping extends AbstractEvent {
    @JsonProperty("channel")
    private String channelId;
    @JsonProperty("user")
    private String userId;
}
