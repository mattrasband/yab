package com.mrasband.yab.config.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mrasband.yab.slack.rtm.event.AbstractEvent;
import com.mrasband.yab.slack.rtm.event.GenericEvent;
import com.mrasband.yab.slack.rtm.event.UserTyping;
import com.mrasband.yab.slack.rtm.event.messages.BaseMessage;
import com.mrasband.yab.slack.rtm.event.messages.MessageDeleted;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author matt.rasband
 */
@RunWith(JUnit4.class)
public class EventDeserializerTest {
    private ObjectMapper objectMapper;

    @Before
    public void createObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule("EventsModule")
                .addDeserializer(AbstractEvent.class, new EventDeserializer()));
    }

    @Test
    public void providesGenericEventForNonResolvedEvent() throws IOException {
        String json = "{\"type\": \"foobar_event\"}";
        AbstractEvent e = objectMapper.readValue(json, AbstractEvent.class);
        assertThat(e).isInstanceOf(GenericEvent.class);
    }

    @Test
    public void resolvesToFoundEvent() throws IOException {
        String json = "{\"type\": \"user_typing\"}";
        AbstractEvent e = objectMapper.readValue(json, AbstractEvent.class);
        assertThat(e).isInstanceOf(UserTyping.class);
    }

    @Test
    public void findsMessageType() throws IOException {
        String json = "{\"type\": \"message\", \"subtype\": \"foobar\", \"text\": \"foobar2\"}";
        AbstractEvent e = objectMapper.readValue(json, AbstractEvent.class);
        assertThat(e).isInstanceOf(BaseMessage.class);
        BaseMessage msg = (BaseMessage) e;
        assertThat(msg.getText()).isEqualToIgnoringCase("foobar2");
    }

    @Test
    public void findsMessageSubType() throws IOException {
        String json = "{\"type\": \"message\", \"subtype\": \"message_deleted\"}";
        AbstractEvent e = objectMapper.readValue(json, AbstractEvent.class);
        assertThat(e).isInstanceOf(MessageDeleted.class);
    }
}
