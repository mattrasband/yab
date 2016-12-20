package com.mrasband.yab.slack.rtm.event.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("channel_join")
public class ChannelJoin extends BaseMessage {
    @JsonProperty("inviter")
    private String invitingUserId;
}
