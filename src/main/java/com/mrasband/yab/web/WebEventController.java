package com.mrasband.yab.web;

import com.google.common.collect.ImmutableMap;
import com.mrasband.yab.slack.SlackService;
import com.mrasband.yab.slack.rtm.event.WebEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

/**
 * @author matt.rasband
 */
@Slf4j
@RestController
@RequestMapping("/slack")
@ConditionalOnProperty(prefix = "slack.webevent", value = "enabled")
public class WebEventController {
    private final SlackService slackService;

    @Autowired
    public WebEventController(SlackService slackService) {
        this.slackService = slackService;
    }

    /**
     * Handle an event from the http event api. Note, that you cannot respond
     * to this to post a message. You need to use the SlackClient#sendMessage
     * option.
     *
     * Note, events try to re-deliver up to 3x, if you want to ignore the
     * event - send a 2xx or add `X-Slack-No-Retry: 1` to the error response.
     */
    @PostMapping(value = "/webevent",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object onSlackWebEvent(@RequestBody WebEvent event) {
        try {
            slackService.validateVerificationToken(event.getToken());
        } catch (ValidationException e) {
            log.error("Invalid slack client", e);
            return null;
        }

        if (event.getType().equals("url_verification")) {
            log.debug("Responding to Slack challenge, 'proving' who we are.");
            return ImmutableMap.of("challenge", event.getChallenge());
        }

        log.debug("Received web event: {}.{}", event.getType(), event.getEvent().getType());

        // TODO: Now you need to handle the event :)
        // It should probably be backgrounded, returning 200 to slack as fast as possible.

        return null;
    }
}
