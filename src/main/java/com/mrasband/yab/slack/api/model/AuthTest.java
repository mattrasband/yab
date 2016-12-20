package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthTest extends RPCResult {
    private String url;

    private String team;

    private String user;

    @JsonProperty("team_id")
    private String teamId;

    @JsonProperty("user_id")
    private String userId;
}
