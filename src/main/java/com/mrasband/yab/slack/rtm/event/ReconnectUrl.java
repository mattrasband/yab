package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("reconnect_url")
public class ReconnectUrl extends Event {
    private String url;
}
