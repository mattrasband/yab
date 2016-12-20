package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mrasband.yab.slack.api.model.core.Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 *
 * https://api.slack.com/events/channel_rename
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("channel_rename")
public class ChannelRename extends Event {
    /**
     * The channel object updated, note that this will
     * just be a subset of properties. Check the API docs.
     */
    private Channel channel;
}
