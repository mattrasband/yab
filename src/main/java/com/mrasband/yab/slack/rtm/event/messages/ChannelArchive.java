package com.mrasband.yab.slack.rtm.event.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("channel_archive")
public class ChannelArchive extends BaseMessage {
    @JsonProperty("members")
    private List<String> memberIds = new ArrayList<>();
}
