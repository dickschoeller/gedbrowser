package org.schoellerfamily.gedbrowser.analytics.test;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.analytics.order.test.OrderAnalyzerTestHelper;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.reader.GedObjectCreator;
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
    public CalendarProvider provider() {
        return new CalendarProviderStub();
    }

    /**
     * @return the helper
     */
    @Bean
    public OrderAnalyzerTestHelper helper() {
        return new OrderAnalyzerTestHelper();
    }

    /**
     * @return the builder
     */
    @Bean
    public GedObjectBuilder builder() {
        return new GedObjectBuilder();
    }

    /**
     * @return convert for AbstractGedLine hierarchy to GedObject hierarchy
     */
    @Bean
    public GedObjectCreator g2g() {
        return new GedObjectCreator();
    }
}
