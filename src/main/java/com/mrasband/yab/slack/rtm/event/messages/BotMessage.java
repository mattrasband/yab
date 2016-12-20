package com.mrasband.yab.slack.rtm.event.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BotMessage extends BaseMessage {
    @JsonProperty("bot_id")
    private String botId;

    private String username;

    private Map<String, String> icons = new HashMap<>();
}
