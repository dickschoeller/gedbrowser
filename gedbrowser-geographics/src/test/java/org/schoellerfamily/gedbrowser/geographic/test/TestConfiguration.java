package org.schoellerfamily.gedbrowser.geographic.test;

import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
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
}
