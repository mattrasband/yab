package com.mrasband.yab.slack.api.model;

import lombok.Data;

/**
 * Format of a slash command, received via HTTP
 *
 * @author matt.rasband
 */
@Data
public class SlashCommand {
    private String token;

    private String team_id;

    private String team_domain;

    private String channel_id;

    private String channel_name;

    private String user_id;

    private String user_name;

    private String command;

    private String text;

    private String response_url;
}
