package com.mrasband.yab.slack.rtm.event;

import lombok.Data;

/**
 * This are primarily focused around the RTM API, but should
 * translate to the HTTP Events API as well.
 *
 * Any event subclass that matches the Slack naming should
 * automatically be picked up due to the {@link com.mrasband.yab.config.JsonConfig}
 * setting for {@link AbstractEvent} types.
 *
 * @author matt.rasband
 */
@Data
public abstract class AbstractEvent {
    protected String type;
}
