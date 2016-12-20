package com.mrasband.yab.slack.api.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrasband.yab.slack.api.model.core.Chat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Multiparty IM
 *
 * @author matt.rasband
 * @see <a href="https://api.slack.com/types/mpim">types/mpim</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MultipartyMessage extends Chat {
    @JsonProperty("is_mpim")
    private Boolean isMpim;

    @JsonProperty("is_group")
    private Boolean isGroup;
}
