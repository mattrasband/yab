package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author matt.rasband
 */
@Data
public class Me {
    private Date created;

    private String id;

    @JsonProperty("manual_presence")
    private String manualPresence;

    private String name;
    // private Preferences prefs;
}
