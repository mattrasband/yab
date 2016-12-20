package com.mrasband.yab.slack.rtm.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReconnectUrl extends AbstractEvent {
    private String url;
}
