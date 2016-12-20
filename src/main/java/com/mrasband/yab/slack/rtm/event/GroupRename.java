package com.mrasband.yab.slack.rtm.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mrasband.yab.slack.api.model.core.Group;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/group_rename">group_rename</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("group_rename")
public class GroupRename extends Event {
    // This will be a partial, see the docs
    private Group channel;
}
