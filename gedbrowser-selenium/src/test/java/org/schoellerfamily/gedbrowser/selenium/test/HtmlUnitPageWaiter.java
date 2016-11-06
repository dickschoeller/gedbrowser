package org.schoellerfamily.gedbrowser.selenium.test;

import org.openqa.selenium.WebDriver;

/**
 * @author Dick Schoeller
 */
public class HtmlUnitPageWaiter implements PageWaiter {
    /** */
    @SuppressWarnings("unused")
    private final WebDriver driver;

    /**
     * Constructor.
     *
     * @param driver the associated web driver.
     */
    public HtmlUnitPageWaiter(final WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void waitForPageLoaded() {
    }
}
