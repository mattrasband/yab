package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/group_close">group_close
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupClose extends AbstractEvent {
    @JsonProperty("user")
    private String userId;
    @JsonProperty("channel")
    private String channelId;
}
