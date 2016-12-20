package com.mrasband.yab.slack.rtm.event.messages;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelTopic extends BaseMessage {
    private String topic;
}
