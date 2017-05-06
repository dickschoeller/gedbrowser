package org.schoellerfamily.gedbrowser.renderer.test;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.reader.GedObjectCreator;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dick Schoeller
 */
@Configuration
public class TestConfiguration {
    /**
     * @return provides the "today" for use in comparisons
     */
    @Bean
    public CalendarProvider provider() {
        return new CalendarProviderStub();
    }

    /**
     * @return the data reader
     */
    @Bean
    TestDataReader reader() {
        return new TestDataReader();
    }

    /**
     * @return the persistence manager
     */
    @Bean
    public GeoServiceClient geoServiceClient() {
        return new GeoServiceClientStub();
    }

    /**
     * @return the calendar provider
     */
    @Bean
    public ApplicationInfo applicationInfo() {
        return new ApplicationInfoStub();
    }

    /**
     * @return converter from AbstractGedLine to GedObject
     */
    @Bean
    public GedObjectCreator gedObjectCreator() {
        return new GedObjectCreator();
    }
}
