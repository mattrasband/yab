package com.mrasband.yab.slack.api.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for "chat" types (channels, groups, mpims...)
 *
 * @author matt.rasband
 *
 * @see <a href="https://api.slack.com/types/channel">Channel</a>
 *
 */
@Data
class Chat {
    private String id;

    /**
     * Channel name, without the leading hash sign
     */
    private String name;

    /**
     * User ID of whoever created the chat
     */
    @JsonProperty("creator")
    private String creatorId;

    private Date created;

    @JsonProperty("members")
    @Setter(AccessLevel.NONE)
    private List<String> memberIds = new ArrayList<>();

    /**
     * May be null/empty
     */
    @JsonProperty("last_read")
    private String lastRead;

    /**
     * Latest message in the channel
     *
     * TODO: model this, is it the same as our {@link com.mrasband.yab.slack.api.model.messaging.Message}?
     */
    @Setter(AccessLevel.NONE)
    private Map<String, Object> latest = new HashMap<>();

    /**
     * Full count of visible messages the user hasn't read
     */
    @JsonProperty("unread_count")
    private Integer unreadCount;

    /**
     * Count of messages minus the noise (joins/leaves, etc)
     */
    @JsonProperty("unread_count_display")
    private Integer unreadCountDisplay;
}
