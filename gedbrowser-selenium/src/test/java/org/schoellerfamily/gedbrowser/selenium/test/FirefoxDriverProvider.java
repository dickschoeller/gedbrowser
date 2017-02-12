package org.schoellerfamily.gedbrowser.selenium.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Dick Schoeller
 */
public class FirefoxDriverProvider implements DriverProvider {
    /**
     * The timeout in seconds.
     */
    private static final int TIMEOUT = 15;

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDriver getDriver() {
        return new FirefoxDriver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageWaiter getWaiter(final WebDriver driver) {
        return new FirefoxPageWaiter(driver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Firefox";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTimeout() {
        return TIMEOUT;
    }
}
