package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("user_typing")
public class UserTyping extends Event {
    @JsonProperty("channel")
    private String channelId;
    @JsonProperty("user")
    private String userId;
}
