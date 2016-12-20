package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 *
 * https://api.slack.com/events/channel_deleted
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelDeleted extends AbstractEvent {
    @JsonProperty("channel")
    private String channelId;
}
