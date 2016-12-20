package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Format of a slash command, received via HTTP
 *
 * @author matt.rasband
 */
@Data
public class SlashCommand {
    private String token;

    @JsonProperty("team_id")
    private String teamId;

    @JsonProperty("team_domain")
    private String teamDomain;

    @JsonProperty("channel_id")
    private String channelId;

    @JsonProperty("channel_name")
    private String channelName;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_name")
    private String userName;

    private String command;

    private String text;

    @JsonProperty("response_url")
    private String responseUrl;
}
