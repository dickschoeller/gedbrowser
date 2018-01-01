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
     * @return convert for AbstractGedLine hierarchy to GedObject hierarchy
     */
    @Bean
    public GedLineToGedObjectTransformer g2g() {
        return new GedLineToGedObjectTransformer();
    }

    /**
     * @return convert for AbstractGedLine hierarchy to GedObject hierarchy
     */
    @Bean
    public TestDataReader reader() {
        return new TestDataReader();
    }
}
