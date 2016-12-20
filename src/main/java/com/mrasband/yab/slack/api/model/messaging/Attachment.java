package com.mrasband.yab.slack.api.model.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Attachment to a message.
 *
 * Note: Not all fields may be required - their requirement depends on if there are other elements
 * present, such as callbackId - which is only mandatory if there are actions attached.
 *
 * @author matt.rasband
 */
@Data
@Builder(toBuilder = true)
public class Attachment {
    private String title;

    @JsonProperty("title_link")
    private String titleLink;

    @NotNull
    @Size(min = 1)
    private String fallback; // required!

    private String pretext;
    private String text;

    /**
     * Can be defaults (good, warning, danger) or any #hex
     */
    private String color;

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("author_link")
    private String authorLink;

    @JsonProperty("author_icon")
    private String authorIcon;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("thumb_url")
    private String thumbUrl;

    private String footer;

    @JsonProperty("footer_icon")
    private String footerIcon;

    @JsonProperty("ts")
    private String timestamp;

    @JsonProperty("attachment_type")
    private final String attachmentType = "default";  // AFAIK - no other options exist

    @Singular("field")
    @Setter(AccessLevel.NONE)
    private List<Field> fields;

    @Singular("action")
    @Setter(AccessLevel.NONE)
    private List<Action> actions;

    /**
     * Required if you include Actions!
     * TODO: validate
     */
    @JsonProperty("callback_id")
    private String callbackId;
}
