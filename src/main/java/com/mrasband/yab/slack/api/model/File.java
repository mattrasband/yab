package com.mrasband.yab.slack.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/types/file">types/file</a>
 */
@Data
public class File {
    private String id;
    private Date created;
    private Date timestamp;
    private String name;
    private String title;
    private String mimetype;
    private String filetype;
    @JsonProperty("pretty_type")
    private String prettyType;
    @JsonProperty("user")
    private String userId;
    private String mode;
    private Boolean editable;
    @JsonProperty("is_external")
    private Boolean isExternal;
    @JsonProperty("external_type")
    private String externalType;
    private String username;
    private Integer size;

}
