package org.schoellerfamily.gedbrowser;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * @author Dick Schoeller
 */
public final class HelloWebXml extends SpringBootServletInitializer {
    /**
     * @see org.springframework.boot.context.web.SpringBootServletInitializer
     *    #configure(org.springframework.boot.builder.SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(
            final SpringApplicationBuilder application) {
        logger.info("Got to HelloWebXml.configure");
        application.sources(Application.class);
        return application;
    }
}
