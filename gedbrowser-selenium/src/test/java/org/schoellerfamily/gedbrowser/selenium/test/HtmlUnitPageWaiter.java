package org.schoellerfamily.gedbrowser.selenium.test;

import org.openqa.selenium.WebDriver;

/**
 * @author Dick Schoeller
 */
public class HtmlUnitPageWaiter implements PageWaiter {
    /** */
    private final WebDriver driver;

    /**
     * Constructor.
     *
     * @param driver the associated web driver.
     */
    public HtmlUnitPageWaiter(final WebDriver driver) {
        this.driver = driver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void waitForPageLoaded() {
        // Intentionally empty.
    }

    /**
     * @return the driver
     */
    public final WebDriver getDriver() {
        return driver;
    }
}
