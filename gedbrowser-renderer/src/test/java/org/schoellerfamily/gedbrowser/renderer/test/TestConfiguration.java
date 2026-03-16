package org.schoellerfamily.gedbrowser.renderer.test;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
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
     * Creates and configures the calendar provider bean.
     *
     * @return the configured calendar provider bean
     */
    @Bean
    public CalendarProvider provider() {
        return new CalendarProviderStub();
    }

    @Bean
    TestDataReader reader(final GedLineToGedObjectTransformer g2g) {
        return new TestDataReader(g2g);
    }

    /**
     * Creates and configures the geo service client bean.
     *
     * @return the configured geo service client bean
     */
    @Bean
    public GeoServiceClient geoServiceClient() {
        return new GeoServiceClientStub();
    }

    /**
     * Creates and configures the application info bean.
     *
     * @return the configured application info bean
     */
    @Bean
    public ApplicationInfo applicationInfo() {
        return new ApplicationInfoStub();
    }

    /**
     * Creates and configures the ged line to ged object transformer bean.
     *
     * @return the configured ged line to ged object transformer bean
     */
    @Bean
    public GedLineToGedObjectTransformer gedObjectCreator() {
        return new GedLineToGedObjectTransformer();
    }
}
