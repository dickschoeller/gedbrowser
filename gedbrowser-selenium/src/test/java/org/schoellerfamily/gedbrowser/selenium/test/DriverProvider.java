package org.schoellerfamily.gedbrowser.selenium.test;

import org.openqa.selenium.WebDriver;

/**
 * @author Dick Schoeller
 */
public interface DriverProvider {
    /**
     * @return a webdriver instance
     */
    WebDriver getDriver();

    /**
     * @param driver the associated driver
     * @return a page waiter instance associated with the driver
     */
    PageWaiter getWaiter(WebDriver driver);

    /**
     * Get the name of the driver.
     *
     * @return the name
     */
    String getName();

    /**
     * Get the appropriate timeout for this driver.
     *
     * @return the timeout in seconds
     */
    int getTimeout();
}
