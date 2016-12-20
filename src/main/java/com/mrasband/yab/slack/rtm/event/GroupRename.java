package com.mrasband.yab.slack.rtm.event;

import com.mrasband.yab.slack.api.model.core.Group;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/group_rename">group_rename</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupRename extends AbstractEvent {
    // This will be a partial, see the docs
    private Group channel;
}
