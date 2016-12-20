package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mrasband.yab.slack.api.model.core.Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 *
 * @see <a href="https://api.slack.com/events/channel_created">channel_created
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("channel_created")
public class ChannelCreated extends Event {
    private Channel channel;
}
