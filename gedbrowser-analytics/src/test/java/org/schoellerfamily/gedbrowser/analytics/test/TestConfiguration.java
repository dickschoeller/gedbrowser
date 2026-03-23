package org.schoellerfamily.gedbrowser.analytics.test;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.analytics.order.test.OrderAnalyzerTestWrapper;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilderImpl;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * Configures components related to test.
 *
 * @author Richard Schoeller
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

    /**
     * Creates and configures the order analyzer test wrapper bean.
     *
     * @return the configured order analyzer test wrapper bean
     */
    @Bean
    public OrderAnalyzerTestWrapper wrapper() {
        return new OrderAnalyzerTestWrapper();
    }

    /**
     * Creates and configures the ged object builder bean.
     *
     * @return the configured ged object builder bean
     */
    @Bean
    public GedObjectBuilder builder() {
        return new GedObjectBuilderImpl();
    }

    /**
     * Creates and configures the ged line to ged object transformer bean.
     *
     * @return the configured ged line to ged object transformer bean
     */
    @Bean
    public GedLineToGedObjectTransformer g2g() {
        return new GedLineToGedObjectTransformer();
    }
}
