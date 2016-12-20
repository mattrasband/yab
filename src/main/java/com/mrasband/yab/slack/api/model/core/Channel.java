package com.mrasband.yab.slack.api.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrasband.yab.slack.api.model.Purpose;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Public channel
 *
 * @author matt.rasband
 * @see <a href="https://api.slack.com/types/channel">channel</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Channel extends Chat {
    @JsonProperty("is_channel")
    private String isChannel;

    @JsonProperty("is_archived")
    private Boolean isArchived;

    @JsonProperty("is_general")
    private Boolean isGeneral;

    private Purpose topic;

    private Purpose purpose;

    @JsonProperty("is_member")
    private Boolean isMember;
}
