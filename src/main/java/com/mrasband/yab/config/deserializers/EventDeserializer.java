package com.mrasband.yab.config.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.mrasband.yab.slack.rtm.event.AbstractEvent;
import com.mrasband.yab.slack.rtm.event.GenericEvent;
import com.mrasband.yab.slack.rtm.event.messages.BaseMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Custom deserializer for events, it tries to do some polymorphic event
 * type discovery across more than just a singular type field. We care about
 * "type" and in some cases "subtype".
 */
@Slf4j
public class EventDeserializer extends StdDeserializer<AbstractEvent> {
    private static final String EVT_PKG = AbstractEvent.class.getPackage().getName();
    private static final String MSG_PKG = BaseMessage.class.getPackage().getName();

    public EventDeserializer() {
        super(AbstractEvent.class);
    }

    @Override
    public AbstractEvent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Class<? extends AbstractEvent> clazz = null;

        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode tree = mapper.readTree(p);

        Iterator<Map.Entry<String, JsonNode>> elemIter = tree.fields();
        while (elemIter.hasNext()) {
            Map.Entry<String, JsonNode> element = elemIter.next();
            String name = element.getKey();
            if (name.equals("subtype")) {
                String eventSubType = convertTypeNameToClassName(element.getValue().asText());
                try {
                    //noinspection unchecked
                    clazz = (Class<AbstractEvent>) TypeFactory.defaultInstance().findClass(MSG_PKG + "." + eventSubType);
                } catch (ClassNotFoundException e) {
                    log.error("Subtype not found {}, using base message", eventSubType);
                    clazz = BaseMessage.class;
                }

                break;
            } else if (name.equals("type") && !element.getValue().asText().equals("message")) {
                String eventType = convertTypeNameToClassName(element.getValue().asText());
                try {
                    //noinspection unchecked
                    clazz = (Class<AbstractEvent>) TypeFactory.defaultInstance().findClass(EVT_PKG + "." + eventType);
                } catch (ClassNotFoundException e) {
                    log.error("Class not found for event type {}, using base event", eventType);
                    clazz = GenericEvent.class;
                }

                break;
            } else if (name.equals("type") && element.getValue().asText().equals("message")) {
                // At least get the default message type, if we didn't already find the subtype
                if (clazz == null) {
                    clazz = BaseMessage.class;
                }
            }
        }

        if (clazz == null) {
            clazz = GenericEvent.class;
            log.error("Unable to find specialized event type, using generic.");
        }

        return mapper.treeToValue(tree, clazz);
    }

    /**
     * The convention we are using for events is converting the name to TitleCase
     * from snake_case, removing the underscores.
     */
    private String convertTypeNameToClassName(String typeName) {
        return Arrays.stream(typeName.split("_"))
                .map(w -> Character.toTitleCase(w.charAt(0)) + w.substring(1).toLowerCase())
                .collect(Collectors.joining());
    }
}
