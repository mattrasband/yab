package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/group_open">group_open</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupOpen extends AbstractEvent {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("channel")
    private String channelId;
}
