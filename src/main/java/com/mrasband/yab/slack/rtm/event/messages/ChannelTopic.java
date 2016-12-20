package com.mrasband.yab.slack.rtm.event.messages;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("channel_topic")
public class ChannelTopic extends BaseMessage {
    private String topic;
}
