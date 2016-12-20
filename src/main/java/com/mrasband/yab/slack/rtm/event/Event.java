package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mrasband.yab.slack.rtm.event.messages.BaseMessage;
import lombok.Data;

/**
 * This are primarily focused around the RTM API, but should
 * translate to the HTTP Events API as well.
 *
 * Adding Events is as simple as defining the POJO, annotating
 * it with {@link JsonTypeName#value()} and listing it in the
 * {@link JsonSubTypes} list here.
 *
 * @author matt.rasband
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type",
        include = JsonTypeInfo.As.PROPERTY, visible = true,
        defaultImpl = GenericEvent.class)
// This feels ugly, but auto-discovery with the
// @JsonTypeIdResolver requires a bit of convention
// giving up a little flexibility that I'd rather retain
@JsonSubTypes({
        @JsonSubTypes.Type(BaseMessage.class),
        @JsonSubTypes.Type(BotAdded.class),
        @JsonSubTypes.Type(BotChanged.class),
        @JsonSubTypes.Type(ChannelArchive.class),
        @JsonSubTypes.Type(ChannelCreated.class),
        @JsonSubTypes.Type(ChannelDeleted.class),
        @JsonSubTypes.Type(ChannelJoined.class),
        @JsonSubTypes.Type(ChannelLeft.class),
        @JsonSubTypes.Type(ChannelRename.class),
        @JsonSubTypes.Type(ChannelUnarchive.class),
        @JsonSubTypes.Type(GroupArchive.class),
        @JsonSubTypes.Type(GroupClose.class),
        @JsonSubTypes.Type(GroupJoined.class),
        @JsonSubTypes.Type(GroupLeft.class),
        @JsonSubTypes.Type(GroupOpen.class),
        @JsonSubTypes.Type(GroupRename.class),
        @JsonSubTypes.Type(GroupUnarchive.class),
        @JsonSubTypes.Type(Hello.class),
        @JsonSubTypes.Type(PresenceChange.class),
        @JsonSubTypes.Type(ReactionAdded.class),
        @JsonSubTypes.Type(ReconnectUrl.class),
        @JsonSubTypes.Type(UserTyping.class),
})
@Data
public abstract class Event {
    protected String type;
}
