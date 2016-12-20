package com.mrasband.yab.slack.api.model.messaging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Button extends Action {
    /**
     * Name of the element, sent to the webhook
     */
    private String name;

    /**
     * Button title text, visible to the user
     */
    private String text;

    /**
     * Action type, only button is available currently.
     */
    private final String type = "button";

    /**
     * Value of the button, this is sent to the callback
     * URL to identify what the user did.
     */
    private String value;

    /**
     * Styling options, pretty much danger|warning|primary|default
     */
    private Style style = Style.DEFAULT;

    /**
     * Optional confirmation prompt for the action to be performed.
     */
    private Confirmation confirm;

    /**
     * Button styling, this is limited to predefined defaults.
     */
    public enum Style {
        // Simple default, usually default to this
        DEFAULT("default"),
        // Use it sparingly, at most 1x/set
        PRIMARY("primary"),
        // Only use in exceptional cases, e.g. something destructive
        DANGER("danger");

        private final String value;

        Style(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }
    }

    @SuppressWarnings("unused")
    public static class ButtonActionBuilder {
        private Style style = Style.DEFAULT;
    }

    /**
     * Optional confirmation dialog settings for an action.
     *
     * @author matt.rasband
     *
     * @see <a href="https://api.slack.com/docs/message-buttons#confirmation_fields">confirmation_fields</a>
     */
    @Data
    @Builder
    public static class Confirmation {
        /**
         * Model title, does not support markdown
         */
        private String title;

        /**
         * Modal body text, supports markdown, required
         */
        @NotNull
        @Size(min = 1)
        private String text;

        /**
         * Optional okay text, if not provided
         * Slack will provide a default ("Okay")
         */
        @JsonProperty("ok_text")
        private String okText;

        /**
         * Optional dismiss text, if not provided
         * Slack will provide a default ("Cancel")
         */
        @JsonProperty("dismiss_text")
        private String dismissText;
    }
}
