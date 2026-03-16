package org.schoellerfamily.gedbrowser.reader.test;

import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dick Schoeller
 */
@Configuration
public class TestConfiguration {
    /**
     * Creates and configures the ged line to ged object transformer bean.
     *
     * @return the configured ged line to ged object transformer bean
     */
    @Bean
    public GedLineToGedObjectTransformer g2g() {
        return new GedLineToGedObjectTransformer();
    }

    /**
     * Creates and configures the test data reader bean.
     *
     * @param g2g the g2g
     * @return the configured test data reader bean
     */
    @Bean
    public TestDataReader reader(final GedLineToGedObjectTransformer g2g) {
        return new TestDataReader(g2g);
    }
}
