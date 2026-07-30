package org.schoellerfamily.geoservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.JacksonObjectMapperCustomizer;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.event.BeanInitializingEvent;
import io.micronaut.inject.BeanDefinition;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.cfg.EnumFeature;

/**
 * Contains tests for {@link JacksonObjectMapperCustomizer}.
 */
public final class JacksonObjectMapperCustomizerTest {
    /**
     * Verifies that customizer logic runs and configures enum and pretty-print behavior.
     *
     * @throws Exception when JSON serialization/deserialization fails
     */
    @Test
    void shouldCustomizeObjectMapperOnInitialization() throws Exception {
        try (ApplicationContext context = ApplicationContext.run()) {
            final BeanDefinition<ObjectMapper> beanDefinition =
                    context.getBeanDefinition(ObjectMapper.class);
            final ObjectMapper initialObjectMapper = new ObjectMapper();
            final BeanInitializingEvent<ObjectMapper> event =
                    new BeanInitializingEvent<>(context, beanDefinition, initialObjectMapper);

            final ObjectMapper customizedObjectMapper =
                    new JacksonObjectMapperCustomizer().onInitialized(event);

            assertTrue(customizedObjectMapper.isEnabled(SerializationFeature.INDENT_OUTPUT),
                    "Expected indented output to be enabled");
            assertTrue(customizedObjectMapper.isEnabled(EnumFeature.WRITE_ENUMS_USING_TO_STRING),
                    "Expected enum serialization using toString to be enabled");
            assertTrue(customizedObjectMapper.isEnabled(EnumFeature.READ_ENUMS_USING_TO_STRING),
                    "Expected enum deserialization using toString to be enabled");

            final EnumHolder holder = new EnumHolder();
            holder.sample = Sample.FIRST;
            final String output = customizedObjectMapper.writeValueAsString(holder);

            assertTrue(output.contains("\"as-string\""), "Expected enum to serialize using toString");

            final EnumHolder restored = customizedObjectMapper.readValue("{\"sample\":\"as-string\"}",
                    EnumHolder.class);
            assertEquals(Sample.FIRST, restored.sample, "Expected enum to deserialize using toString");
        }
    }

    /** Test enum with a custom string value. */
    private enum Sample {
        FIRST;

        @Override
        public String toString() {
            return "as-string";
        }
    }

    /** Simple holder for enum JSON round-trip checks. */
    private static final class EnumHolder {
        public Sample sample;
    }
}
