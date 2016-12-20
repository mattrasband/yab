package com.mrasband.yab.slack.api.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @JsonProperty("url_private")
    private String urlPrivate;

    @JsonProperty("url_private_download")
    private String urlPrivateDownload;

    @JsonProperty("thumb_64")
    private String thumb64;

    @JsonProperty("thumb_80")
    private String thumb80;

    @JsonProperty("thumb_360")
    private String thumb360;

    @JsonProperty("thumb_360_gif")
    private String thumb360Gif;

    @JsonProperty("thumb_360_w")
    private String thumb360W;

    @JsonProperty("thumb_360_h")
    private String thumb360H;

    @JsonProperty("thumb_480")
    private String thumb480;

    @JsonProperty("thumb_480_w")
    private String thumb480W;

    @JsonProperty("thumb_480_h")
    private String thumb480H;

    @JsonProperty("thumb_160")
    private String thumb160;

    private String permalink;

    @JsonProperty("permalink_public")
    private String permalinkPublic;

    @JsonProperty("edit_link")
    private String editLink;

    private String preview;

    @JsonProperty("preview_highlight")
    private String previewHighlight;

    private Integer lines;

    @JsonProperty("lines_public")
    private Integer linesMore;

    @JsonProperty("is_public")
    private Boolean isPublic;

    @JsonProperty("public_url_shared")
    private Boolean publicUrlShared;

    @JsonProperty("display_as_bot")
    private Boolean displayAsBot;

    @JsonProperty("channels")
    private List<String> channelIds = new ArrayList<>();

    @JsonProperty("groups")
    private List<String> groupIds = new ArrayList<>();

    @JsonProperty("ims")
    private List<String> imIds = new ArrayList<>();

    private Map<String, Object> initialComment = new HashMap<>();

    @JsonProperty("num_stars")
    private Integer numStars;

    @JsonProperty("is_starred")
    private Boolean isStarred;

    @JsonProperty("pinned_to")
    private List<String> pinnedToIds = new ArrayList<>();

    private List<Reaction> reactions = new ArrayList<>();

    @JsonProperty("comments_count")
    private Integer commentCount;

}
