package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author matt.rasband
 */
@Data
public class Team {
    @JsonProperty("avatar_base_url")
    private String avatarBaseUrl;

    private String domain;

    @JsonProperty("email_domain")
    private String emailDomain;

    private Map<String, String> icon = new HashMap<>();

    private String id;

    @JsonProperty("msg_edit_window_mins")
    private Integer msgEditWindowMins;

    private String name;

    @JsonProperty("over_integrations_limit")
    private Boolean overIntegrationsLimit;

    @JsonProperty("over_storage_limit")
    private Boolean overStorageLimit;

    private String plan;
    // private Preferences prefs;
}
