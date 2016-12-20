package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/group_left">group_left</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("group_left")
public class GroupLeft extends Event {
    @JsonProperty("channel")
    private String channelId;
}
