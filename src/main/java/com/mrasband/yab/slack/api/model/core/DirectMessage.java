package com.mrasband.yab.slack.api.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * IM type, a direct message
 *
 * @author matt.rasband
 * @see <a href="https://api.slack.com/types/im">types/im</a>
 */
@Data
public class DirectMessage {
    private String id;

    @JsonProperty("is_im")
    private Boolean isIm;

    @JsonProperty("user")
    private String userId;

    private Date created;

    @JsonProperty("is_user_deleted")
    private Boolean isUserDeleted;

    @JsonProperty("is_open")
    private Boolean isOpen;

    @JsonProperty("last_read")
    private String lastRead;

    @JsonProperty("unread_count")
    private Integer unreadCount;

    // TODO: Model the 'latest' object
}
