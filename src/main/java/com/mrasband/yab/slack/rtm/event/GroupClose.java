package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/group_close">group_close
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("group_close")
public class GroupClose extends Event {
    @JsonProperty("user")
    private String userId;
    @JsonProperty("channel")
    private String channelId;
}
