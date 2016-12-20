package com.mrasband.yab.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrasband.yab.slack.actions.SlackActionHandler;
import com.mrasband.yab.slack.api.SlackClient;
import com.mrasband.yab.slack.api.model.ActionResponse;
import com.mrasband.yab.slack.api.model.ChatPostMessageResult;
import com.mrasband.yab.slack.api.model.messaging.Message;
import com.mrasband.yab.slack.rtm.SlackRTMHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author matt.rasband
 */
@Service
@Slf4j
public class SlackService {
    private final SlackProperties slackProperties;
    private final SlackTeamRepository teamRepository;
    private final SlackClient slackClient;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher eventPublisher;

    private final Map<String, SlackRTMHandler> slackTeamHandlers = new HashMap<>();

    private final List<SlackActionHandler> handlers = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    public SlackService(SlackProperties slackProperties,
                        SlackTeamRepository teamRepository,
                        SlackClient slackClient,
                        ObjectMapper objectMapper,
                        ApplicationEventPublisher eventPublisher) {
        this.slackProperties = slackProperties;
        this.teamRepository = teamRepository;
        this.slackClient = slackClient;
        this.objectMapper = objectMapper;
        this.eventPublisher = eventPublisher;
    }

    @PostConstruct
    void connectBots() {
        StreamSupport.stream(teamRepository.findAll().spliterator(), false)
                .filter(a -> a.getBot() != null)
//                .map(a -> a.getBot().getBotAccessToken())
                .forEach(t -> {
                    slackTeamHandlers.put(t.getTeamId(),
                            new SlackRTMHandler(t.getBot().getBotAccessToken(), slackClient, eventPublisher, objectMapper));
                });
    }

    /**
     * Handle the action callback, note that the first one found in the list will be used.
     */
    public Message handleActionCallback(ActionResponse response) {
        return handlers.stream()
                .filter(h -> h.getCallbackId().equals(response.getCallbackId()))
                .findFirst()
                .map(slackActionHandler -> slackActionHandler.handle(response))
                .orElseThrow(() -> new RuntimeException(String.format("No handler for type %s available.", response.getCallbackId())));
    }

    public void addActionHandler(SlackActionHandler newHandler) {
        this.handlers.add(newHandler);
    }

    /**
     * Check that the verification token is valid, throw a validation exception if it is note.
     *
     * @param verificationToken Token to check
     */
    public void validateVerificationToken(String verificationToken) {
        if (!verificationToken.equals(slackProperties.getApp().getVerificationToken())) {
            throw new ValidationException("Invalid slack token");
        }
    }

    /**
     * Only sends via the API
     */
    public ChatPostMessageResult sendMessage(String teamId, Message message, boolean asBot) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Message>> violations = validator.validate(message);
        if (violations.size() > 0) {
            String errors = violations.stream()
                    .map(m -> m.getPropertyPath() + " " + m.getMessage())
                    .collect(Collectors.joining(", "));
            throw new ValidationException(String.format("Invalid object: %s", errors));
        }

        try {
            String token;
            if (asBot) {
                token = teamRepository.findOne(teamId).getBot().getBotAccessToken();
            } else {
                token = teamRepository.findOne(teamId).getAccessToken();
            }

            return slackClient.postMessage(token, message.getText(), message.getType(), message.getChannel(),
                    objectMapper.writeValueAsString(message.getAttachments())).execute().body();
        } catch (IOException e) {
            log.error("Error sending payload!", e);
        }

        return null;
    }
}
