package org.schoellerfamily.gedbrowser.selenium.base;

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

    /**
     * Wait for the page to load. Different implementations for different
     * drivers.
     *
     * @param driver the driver
     * @param multiplier timeout multiplier
     */
    void waitForPageLoaded(WebDriver driver, int multiplier);

    /**
     * Wait for the page to load. Different implementations for different
     * drivers.
     *
     * @param driver the driver
     * @param newUrl the target URL
     */
    void waitForPageLoaded(WebDriver driver, String newUrl);

    /**
     * Wait for the page to load. Different implementations for different
     * drivers.
     *
     * @param driver the driver
     * @param newUrl the target URL
     * @param multiplier timeout multiplier
     */
    void waitForPageLoaded(WebDriver driver, String newUrl, int multiplier);
}
