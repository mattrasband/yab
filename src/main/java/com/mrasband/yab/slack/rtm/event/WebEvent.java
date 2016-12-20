package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * The Web AbstractEvent API wraps all events in a little meta
 * data about the event and team.
 *
 * @author matt.rasband
 */
@Data
public class WebEvent {
    private String token;

    private AbstractEvent event;

    @JsonProperty("team_id")
    private String teamId;

    @JsonProperty("api_app_id")
    private String apiAppId;

    private String type;  // usually just event_callback

    @JsonProperty("authed_users")
    private List<String> authedUsers;

    private String challenge;  // only used for install
}
