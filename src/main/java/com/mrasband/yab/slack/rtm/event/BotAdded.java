package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mrasband.yab.slack.api.model.Bot;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("bot_added")
public class BotAdded extends Event {
    private Bot bot;
}
