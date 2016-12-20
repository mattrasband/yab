package com.mrasband.yab.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrasband.yab.slack.api.model.ActionResponse;
import com.mrasband.yab.slack.api.model.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Validation;
import javax.validation.ValidationException;
import java.beans.PropertyEditorSupport;
import java.io.IOException;

/**
 * @author matt.rasband
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    private final ObjectMapper objectMapper;

    @Autowired
    public GlobalControllerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {
        // We have to customize how the action response is deserialized, slack does a kind-of odd thing
        // where they serialize the JSON and send it as a form-encoded parameter. So it really ends up
        // needing to go through two different deserializtion strategies.
        binder.registerCustomEditor(ActionResponse.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    setValue(objectMapper.readValue(text, ActionResponse.class));
                } catch (IOException e) {
                    super.setAsText(text);
                }
            }
        });
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.OK)
    public Message handleValidationException(Exception e) {
        return Message.builder()
                .text("Sorry, that didn't work: " + e.getMessage())
                .build();
    }
}
