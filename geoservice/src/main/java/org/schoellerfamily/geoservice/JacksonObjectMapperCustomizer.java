package org.schoellerfamily.geoservice;

import io.micronaut.context.event.BeanInitializingEvent;
import io.micronaut.context.event.BeanInitializedEventListener;
import jakarta.inject.Singleton;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.cfg.EnumFeature;

/**
 * Customizes the Jackson ObjectMapper for the application. Specifically, it configures
 * serialization and deserialization features for enums and enables pretty printing of JSON.
 * This makes the behavior match what was previously configured in the default ObjectMapper.
 */
@Singleton
public class JacksonObjectMapperCustomizer implements BeanInitializedEventListener<ObjectMapper> {

    /**
     * Customize the Jackson ObjectMapper after it has been initialized.
     *
     * @param event the bean initializing event containing the ObjectMapper
     * @return the customized ObjectMapper
     */
    @Override
    public ObjectMapper onInitialized(final BeanInitializingEvent<ObjectMapper> event) {
        final ObjectMapper objectMapper = event.getBean();
        return objectMapper.rebuild()
            .findAndAddModules()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(EnumFeature.WRITE_ENUMS_USING_TO_STRING, true)
            .configure(EnumFeature.READ_ENUMS_USING_TO_STRING, true)
            .build();
    }
}
