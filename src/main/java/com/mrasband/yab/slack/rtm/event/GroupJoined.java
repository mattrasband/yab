package com.mrasband.yab.slack.rtm.event;

import com.mrasband.yab.slack.api.model.core.Group;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/events/group_joined">group_joined</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupJoined extends AbstractEvent {
    private Group channel;
}
