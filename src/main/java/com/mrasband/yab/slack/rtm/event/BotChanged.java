package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mrasband.yab.slack.api.model.Bot;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 *
 * @see <a href="https://api.slack.com/events/bot_changed">Slack API Docs</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("bot_changed")
public class BotChanged extends Event {
    private Bot bot;
}
