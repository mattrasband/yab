package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * Topic/Purpose for a channel, dm, pmim
 *
 * @author matt.rasband
 */
@Data
public class Purpose {
    @JsonProperty("creator")
    private String creatorId;

    @JsonProperty("last_set")
    private Date lastSet;

    private String value;
}
