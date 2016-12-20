package com.mrasband.yab.slack.rtm.event;

import com.mrasband.yab.slack.api.model.Bot;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BotAdded extends AbstractEvent {
    private Bot bot;
}
