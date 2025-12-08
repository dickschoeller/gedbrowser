package org.schoellerfamily.gedbrowser.selenium.config;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;
import org.schoellerfamily.gedbrowser.selenium.base.RemotePageWaiter;
import org.schoellerfamily.gedbrowser.selenium.base.WebDriverFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dick Schoeller
 */
@Configuration
@Slf4j
public class SeleniumConfig {

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
        log.info("Getting page waiter with timeout: " + timeout);
        return new RemotePageWaiter(timeout);
    }
}
