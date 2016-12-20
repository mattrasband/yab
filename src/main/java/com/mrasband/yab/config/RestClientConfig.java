package com.mrasband.yab.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrasband.yab.slack.SlackProperties;
import com.mrasband.yab.slack.api.SlackClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Configure the default rest client(s)
 *
 * @author matt.rasband
 */
@Configuration
public class RestClientConfig {
    /**
     * Auto implements the Slack Client endpoints.
     *
     * @param props Slack properties
     * @param mapper Object mapper, configured
     *
     * @return Implemented SlackClient
     */
    @Bean
    SlackClient slackClient(final SlackProperties props,
                            final ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(props.getSlackApiUrl())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build()
                .create(SlackClient.class);
    }
}
