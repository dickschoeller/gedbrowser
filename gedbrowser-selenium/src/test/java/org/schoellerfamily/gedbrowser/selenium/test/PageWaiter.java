package org.schoellerfamily.gedbrowser.selenium.test;

/**
 * @author Dick Schoeller
 */
public interface PageWaiter {
    /**
     * Wait for the page to load. Different implementations for different
     * drivers.
     */
    void waitForPageLoaded();
}
