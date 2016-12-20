package com.mrasband.yab.slack.api.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author matt.rasband
 */
@Data
public class Reaction {
    private String name;

    private Integer count;

    @JsonProperty("users")
    private List<String> userIds;
}
