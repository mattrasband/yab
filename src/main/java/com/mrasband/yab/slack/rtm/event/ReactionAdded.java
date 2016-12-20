package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("reaction_added")
public class ReactionAdded extends Event {
    @JsonProperty("user")
    private String userId;
    private Item item;
    private String reaction;
    @JsonProperty("item_user")
    private String itemUser;
    @JsonProperty("event_ts")
    private String eventTimestamp;

    @Data
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type",
            include = JsonTypeInfo.As.PROPERTY, visible = true)
    @JsonSubTypes({
            @JsonSubTypes.Type(MessageItem.class),
    })
    public static class Item {
        private String type;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @JsonTypeName("message")
    public static class MessageItem extends Item {
        @JsonProperty("channel")
        private String channelId;

        @JsonProperty("ts")
        private String timestamp;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @JsonTypeName("file")
    public static class FileItem extends Item {
        private String file;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @JsonTypeName("file_comment")
    public static class FileComment extends Item {
        private String file;
        @JsonProperty("file_comment")
        private String fileComment;
    }
}
