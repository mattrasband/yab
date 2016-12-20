package com.mrasband.yab.slack.rtm.event.messages;

import com.mrasband.yab.slack.api.model.core.File;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileCreated extends BaseMessage {
    private File file;
}
