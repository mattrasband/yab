package com.mrasband.yab.slack.api.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author matt.rasband
 *
 * @see <a href="https://api.slack.com/types/user">types/user</a>
 */
@Data
public class User {
    private String id;

    private String name;

    private Boolean deleted;

    private String color;

    /**
     * Not really a deterministic set, possible has things
     * like first_name, last_name, real_name, email, skype,
     * phone, image_*
     */
    private Map<String, String> profile = new HashMap<>();

    @JsonProperty("is_admin")
    private Boolean admin;

    @JsonProperty("is_owner")
    private Boolean owner;

    @JsonProperty("is_primary_owner")
    private Boolean primaryOwner;

    @JsonProperty("is_restricted")
    private Boolean restricted;

    @JsonProperty("is_ultra_restricted")
    private Boolean ultraRestricted;

    @JsonProperty("has_2fa")
    private Boolean twoFactorAuth;

    @JsonProperty("two_factor_type")
    private AuthType twoFactorType;

    @JsonProperty("has_files")
    private Boolean hasFiles;

    public enum AuthType {
        APP("app"),
        SMS("sms");

        private final String value;

        AuthType(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }
    }
}
