package org.schoellerfamily.gedbrowser.geographic.test;

import org.schoellerfamily.gedbrowser.reader.GedObjectCreator;
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
    public GedObjectCreator g2g() {
        return new GedObjectCreator();
    }
}
