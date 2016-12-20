package com.mrasband.yab.slack.api.model;

import com.mrasband.yab.slack.api.model.core.File;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author matt.rasband
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileInfo extends RPCResult {
    private File file;
}
