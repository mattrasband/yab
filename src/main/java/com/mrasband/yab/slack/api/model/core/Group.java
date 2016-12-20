package com.mrasband.yab.slack.api.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrasband.yab.slack.api.model.Purpose;
import com.mrasband.yab.slack.api.model.core.Chat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Private Channel
 *
 * @author matt.rasband
 * @see <a href="https://api.slack.com/types/group">types/group</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Group extends Chat {
    @JsonProperty("is_group")
    private String isGroup;

    @JsonProperty("is_archived")
    private Boolean archived;

    @JsonProperty("is_mpim")
    private Boolean mpim;

    private Purpose topic;

    private Purpose purpose;

}
