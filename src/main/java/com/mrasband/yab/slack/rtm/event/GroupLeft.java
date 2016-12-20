package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/group_left">group_left</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupLeft extends AbstractEvent {
    @JsonProperty("channel")
    private String channelId;
}
