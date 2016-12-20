package com.mrasband.yab.slack.api.model.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Text fields are rendered as tables, effectively. Use the
 * {@link #isShort} field to define if other fields could be
 * rendered next to it.
 *
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class TextField extends Field {
    /**
     * Field title, this is bold
     */
    private String title;

    /**
     * Field value, the regular text
     */
    private String value;

    /**
     * Defines if the field can be rendered next to
     * another field.
     */
    @JsonProperty("short")
    private Boolean isShort = false;
}
