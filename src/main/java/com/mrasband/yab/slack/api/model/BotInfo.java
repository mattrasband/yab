package com.mrasband.yab.slack.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BotInfo extends RPCResult {
    private Bot bot;
}
