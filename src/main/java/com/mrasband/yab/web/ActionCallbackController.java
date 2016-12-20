package com.mrasband.yab.web;

import com.mrasband.yab.slack.SlackService;
import com.mrasband.yab.slack.api.model.ActionResponse;
import com.mrasband.yab.slack.api.model.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sample action callback controller, generally you should be good to just use
 * this since you can only register one and we delegate out to a central point that managers handlers for
 * the various action types you may define.
 *
 * @author matt.rasband
 */
@RestController
@RequestMapping("/actions")
@Slf4j
@ConditionalOnProperty(prefix = "slack.actions", value = "enabled")
public class ActionCallbackController {
    private final SlackService slackService;

    @Autowired
    public ActionCallbackController(SlackService slackService) {
        this.slackService = slackService;
    }

    /**
     * Handle the action callback, delegating to the slack service to actually dispatch it. If a user response to
     * the call directly - the message will be sent back to slack (note: this must happen within 3 seconds).
     * Alternative the handler can use the {@link ActionResponse#responseUrl} to respond up to 5 times within
     * a 30 minute window.
     *
     * @param actionResponse Action response from slack
     * @return Response entity
     *
     * @see <a href="https://api.slack.com/docs/message-buttons#how_to_respond_to_message_button_actions">How to respond to message button actions</a>
     */
    @PostMapping(value = "/callback",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Message onActionCallback(@RequestParam("payload") ActionResponse actionResponse) {
        slackService.validateVerificationToken(actionResponse.getToken());
        log.debug("Handling action response for {}", actionResponse.getCallbackId());

        try {
            return slackService.handleActionCallback(actionResponse);
        } catch (Throwable e) {
            log.error("Error handing action response!", e);
            return Message.builder()
                    .text("Sorry, that didn't work: " + e.getMessage())
                    .visibility(Message.Visibility.EPHEMERAL)
                    .build();
        }
    }
}
