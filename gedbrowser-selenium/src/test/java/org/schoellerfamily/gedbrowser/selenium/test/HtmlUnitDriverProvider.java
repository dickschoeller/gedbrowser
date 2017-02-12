package org.schoellerfamily.gedbrowser.selenium.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * @author Dick Schoeller
 */
public class HtmlUnitDriverProvider implements DriverProvider {
    /**
     * The timeout in seconds.
     */
    private static final int TIMEOUT = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDriver getDriver() {
        return new HtmlUnitDriver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageWaiter getWaiter(final WebDriver driver) {
        return new HtmlUnitPageWaiter(driver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "HtmlUnit";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTimeout() {
        return TIMEOUT;
    }
}
