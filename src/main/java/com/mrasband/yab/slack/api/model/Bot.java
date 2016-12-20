package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author matt.rasband
 *
 * @see <a href="https://api.slack.com/events/bot_added">Slack API</a>
 */
@Data
public class Bot {
    private String id;

    @JsonProperty("app_id")
    private String appId;

    private Boolean deleted;

    private String name;

    /**
     * I am not sure what is all in here, usually I think
     * it's just image_{36,48,72}
     */
    @Setter(AccessLevel.NONE)
    private Map<String, String> icons = new HashMap<>();
}
