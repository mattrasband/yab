package com.mrasband.yab.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mrasband.yab.config.deserializers.EventDeserializer;
import com.mrasband.yab.slack.rtm.event.AbstractEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author matt.rasband
 */
@Configuration
public class JsonConfig {
    /**
     * Adds custom module to handle deserializing a fairly complex polymorphic type: Slack Events. We
     * would normally just use the jackson provided resolvers, but they don't easily support multiple
     * properties in this resolution.
     */
    @Bean
    Module eventDeserializerModule() {
        return new SimpleModule("EventsModule")
                .addDeserializer(AbstractEvent.class, new EventDeserializer());
    }

}
