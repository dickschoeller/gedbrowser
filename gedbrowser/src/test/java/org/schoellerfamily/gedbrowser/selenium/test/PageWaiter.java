package org.schoellerfamily.gedbrowser.selenium.test;

import org.openqa.selenium.WebDriver;

/**
 * @author Dick Schoeller
 */
public interface PageWaiter {
    /**
     * Wait for the page to load. Different implementations for different
     * drivers.
     *
     * @param driver the driver
     */
    void waitForPageLoaded(WebDriver driver);
}
