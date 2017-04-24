package org.schoellerfamily.gedbrowser.selenium.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dick Schoeller
 */
@Configuration
public class SeleniumConfig {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Value("${selenium.timeout:30}")
    private long timeout;

    /**
     * @return the factory
     */
    @Bean
    public WebDriverFactory webDriverFactory() {
        return new WebDriverFactory();
    }

    /**
     * @return the page waiter
     */
    @Bean
    public PageWaiter pageWaiter() {
        logger.info("Getting page waiter");
        return new RemotePageWaiter(timeout);
    }
}
