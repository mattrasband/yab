package com.mrasband.yab.slack.api.model;

import com.mrasband.yab.slack.api.model.core.Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelCreate extends RPCResult {
    private Channel channel;
}
