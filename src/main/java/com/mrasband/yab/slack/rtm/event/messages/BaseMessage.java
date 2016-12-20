package com.mrasband.yab.slack.rtm.event.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mrasband.yab.slack.rtm.event.*;
import com.mrasband.yab.slack.rtm.event.ChannelArchive;
import com.mrasband.yab.slack.rtm.event.ChannelUnarchive;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/message">message</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "subtype",
        include = JsonTypeInfo.As.PROPERTY, visible = true,
        defaultImpl = BaseMessage.class)
@JsonSubTypes({
        @JsonSubTypes.Type(BotMessage.class),
        @JsonSubTypes.Type(ChannelArchive.class),
        @JsonSubTypes.Type(ChannelJoin.class),
        @JsonSubTypes.Type(ChannelLeave.class),
        @JsonSubTypes.Type(ChannelName.class),
        @JsonSubTypes.Type(ChannelPurpose.class),
        @JsonSubTypes.Type(ChannelTopic.class),
        @JsonSubTypes.Type(ChannelUnarchive.class),
        @JsonSubTypes.Type(MessageDeleted.class),
})
@JsonTypeName("message")
public class BaseMessage extends Event {
    @JsonProperty("channel")
    private String channelId;
    private String text;
    @JsonProperty("ts")
    private String timestamp;
    // optional, likely null in most cases
    private Edit edited;
    @JsonProperty("user")
    private String userId;
    private String subtype;

    @Data
    public static class Edit {
        @JsonProperty("user")
        private String userId;
        @JsonProperty("ts")
        private String timestamp;
    }
}
