package com.mrasband.yab.slack.api.model.messaging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Main message type for slack, simple messages, meaning those with no attachments, can be sent
 * via the RTM Api. Anything else needs to be sent to the richer `chat.postMessage` endpoint.
 *
 * @author matt.rasband
 */
@Data
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Message {
    /**
     * Basic message text, only required if no attachments are present.
     */
    private String text;

    private final String type = "message";

    @NotNull
    private String channel;

    /**
     * Array of attachment objects, this is size limited by slack to 100, but they
     * recommend no more than 20.
     */
    @Setter(AccessLevel.NONE)
    @Singular("attachment")
    private List<Attachment> attachments;

    /**
     * This defines if the message is visible to the channel, or the invoking user.
     *
     * Only applicable when responding to HTTP events/Commands
     */
    @JsonProperty("response_type")
    private Visibility visibility;

    /**
     * This is only used when using the update_url, received from a slack initiated web event
     */
    @JsonProperty("replace_original")
    private Boolean replaceOriginal;

    /**
     * This is only used when using the update_url, received from a slack initiated web event
     */
    @JsonProperty("delete_original")
    private Boolean deleteOriginal;

    /**
     * Sets the text for the message, filtering out the
     * special characters (per Slack's definition).
     *
     * @param text new text value
     */
    public void setText(String text) {
        this.text = text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    @JsonIgnore
    public boolean isComplex() {
        return this.attachments != null && this.attachments.size() > 0;
    }

    /**
     * Visibility of a message in a channel, useful only for
     * replies to slash commands or actions.
     *
     * @author matt.rasband
     */
    public enum Visibility {
        // Visible to all channel members
        IN_CHANNEL("in_channel"),
        // Visible to only the invoking user (Slack's default)
        EPHEMERAL("ephemeral");

        private final String value;

        Visibility(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }
    }
}
