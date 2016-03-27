package org.schoellerfamily.gedbrowser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * @author Dick Schoeller
 */
public final class HelloWebXml extends SpringBootServletInitializer {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @see org.springframework.boot.context.web.SpringBootServletInitializer
     *    #configure(org.springframework.boot.builder.SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(
            final SpringApplicationBuilder application) {
        logger.debug("Entering configure");
        application.sources(Application.class);
        logger.debug("Exiting configure");
        return application;
    }
}
