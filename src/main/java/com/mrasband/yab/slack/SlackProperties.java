package com.mrasband.yab.slack;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author matt.rasband
 */
@Component
@ConfigurationProperties(prefix = "slack")
@Data
public class SlackProperties {
    private String slackApiUrl = "https://slack.com/api/";
    private String token;

    private App app = new App();

    @Data
    @SuppressWarnings("WeakerAccess")
    public static class App {
        private String clientId;
        private String clientSecret;
        private String verificationToken;
        private List<String> scopes = new ArrayList<>();
    }
}
