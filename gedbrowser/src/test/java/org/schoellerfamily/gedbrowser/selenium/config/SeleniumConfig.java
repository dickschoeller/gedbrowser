package org.schoellerfamily.gedbrowser.selenium.config;

import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;
import org.schoellerfamily.gedbrowser.selenium.base.RemotePageWaiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Configures components related to selenium.
 *
 * @author Richard Schoeller
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class SeleniumConfig {

    /** */
    @Value("${selenium.timeout:30}")
    private final long timeout;

    /**
     * Creates and configures the page waiter bean.
     *
     * @return the configured page waiter bean
     */
    @Bean
    public PageWaiter pageWaiter() {
        log.info("Getting page waiter with timeout: {}", timeout);
        return new RemotePageWaiter(timeout);
    }
}
