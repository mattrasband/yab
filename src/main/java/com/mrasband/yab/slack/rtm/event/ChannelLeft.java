package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 *
 * https://api.slack.com/events/channel_left
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("channel_left")
public class ChannelLeft extends Event {
    @JsonProperty("channel")
    private String channelId;
}
