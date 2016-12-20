package com.mrasband.yab.slack.api.model;

import lombok.Data;

/**
 * @author matt.rasband
 */
@Data
public class RPCResult {
    /**
     * All responses from the Slack API that are handled
     * by a server instance will return at least the "ok"
     * parameter.
     */
    private Boolean ok;

    /**
     * Usually null, but it can be populated if !ok.
     * Typically this will be null.
     */
    private String error;

    /**
     * In some cases, the request was still processed
     * but it was still considered problematic. Typically
     * this will be null.
     */
    private String warning;

    /**
     * In the case of a call that is unauthorized, Slack is nice
     * and tells you want scope you need to have to perform the
     * action. Typically this will be null.
     */
    private String needed;


    /**
     * In the case of a call that is unauthorized, Slack is nice
     * and tells you want scopes your token user has. Typically
     * this will be null.
     */
    private String provided;
}
