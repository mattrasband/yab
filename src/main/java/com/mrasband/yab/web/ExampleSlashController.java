package com.mrasband.yab.web;

import com.mrasband.yab.slack.SlackService;
import com.mrasband.yab.slack.api.SlackClient;
import com.mrasband.yab.slack.api.model.SlashCommand;
import com.mrasband.yab.slack.api.model.messaging.Attachment;
import com.mrasband.yab.slack.api.model.messaging.Button;
import com.mrasband.yab.slack.api.model.messaging.Message;
import com.mrasband.yab.slack.api.model.messaging.TextField;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example endpoint that handles a slash command ("/weather [U.S. zip code]")
 *
 * @author matt.rasband
 */
@RestController
@RequestMapping("/slash")
@ConditionalOnProperty(prefix = "slack.slash", value = "enabled")
public class ExampleSlashController {
    private final SlackService slackService;
    private final SlackClient slackClient;

    public ExampleSlashController(SlackService slackService,
                                  SlackClient slackClient) {
        this.slackService = slackService;
        this.slackClient = slackClient;
    }

    /**
     * Example controller for handling slash commands, basically you should just
     * return a BaseMessage type, adding whatever fields and actions you may want.
     * Note that callback_ids should be registered to the slack service for
     * delegation.
     */
    @PostMapping(value = "/weather",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Message> weather(SlashCommand cmd) {
        slackService.validateVerificationToken(cmd.getToken());

        Attachment at = Attachment.builder()
                .field(TextField.builder()
                        .isShort(true)
                        .title("Winning?")
                        .value("Nope")
                        .build())
                .action(Button.builder()
                        .text("Win")
                        .value("win")
                        .name("win")
                        .build())
                .action(Button.builder()
                        .text("Lose")
                        .value("lose")
                        .name("lose")
                        .confirm(Button.Confirmation.builder()
                                .title("Are you sure...?")
                                .build())
                        .build())
                .build();

        return ResponseEntity.ok(Message.builder()
                .text("Hello there")
                .visibility(Message.Visibility.EPHEMERAL)
                .attachment(at)
                .build());
    }
}
