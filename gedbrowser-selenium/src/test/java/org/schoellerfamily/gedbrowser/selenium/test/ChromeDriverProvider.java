package org.schoellerfamily.gedbrowser.selenium.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Dick Schoeller
 */
public final class ChromeDriverProvider implements DriverProvider {
    /**
     * The timeout in seconds.
     */
    private static final int TIMEOUT = 5;

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDriver getDriver() {
        return new ChromeDriver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageWaiter getWaiter(final WebDriver driver) {
        return new ChromePageWaiter(driver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Chrome";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTimeout() {
        return TIMEOUT;
    }
}
