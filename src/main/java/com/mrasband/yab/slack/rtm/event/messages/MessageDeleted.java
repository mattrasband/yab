package com.mrasband.yab.slack.rtm.event.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("message_deleted")
public class MessageDeleted extends BaseMessage {
    @JsonProperty("deleted_ts")
    private String deletedTimestamp;
    private Boolean hidden;
    @JsonProperty("previous_message")
    private BaseMessage previousMessage;
    @JsonProperty("event_ts")
    private String eventTimestamp;
}
