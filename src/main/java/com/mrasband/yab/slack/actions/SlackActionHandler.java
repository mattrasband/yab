package com.mrasband.yab.slack.actions;

import com.mrasband.yab.slack.api.model.ActionResponse;
import com.mrasband.yab.slack.api.model.messaging.Message;

/**
 * @author matt.rasband
 */
public interface SlackActionHandler {
    /**
     * Which {@link ActionResponse#callbackId} the handler
     * cares about.
     *
     * @return The callbackId this handler cares about.
     */
    String getCallbackId();

    /**
     * Handle the action.
     *
     * @param action Action to handle
     * @return Either a message, or null. If null is returned, you can still respond to and
     * update the message with the {@link ActionResponse#responseUrl} up to 5x over 30m
     */
    Message handle(ActionResponse action);
}
