package com.mrasband.yab.slack.api.model;

import com.mrasband.yab.slack.api.model.core.File;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author matt.rasband
 * @see <a href="https://api.slack.com/methods/files.list">files.list</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileList extends RPCResult {
    private List<File> files = new ArrayList<>();

    private Page paging;

    @Data
    @SuppressWarnings("WeakerAccess")
    public static class Page {
        private Integer count;

        private Integer total;

        private Integer page;

        private Integer pages;
    }
}
