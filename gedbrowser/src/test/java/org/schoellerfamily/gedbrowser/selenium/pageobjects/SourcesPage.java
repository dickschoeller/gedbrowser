package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;

/**
 * @author Dick Schoeller
 */
public class SourcesPage extends PageBase {
    /**
     * PageObject pattern for the sources page.
     *
     * @param driver this is the basic web driver
     * @param waiter handles driver specific waits
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     */
    public SourcesPage(final WebDriver driver,
            final PageWaiter waiter, final PageBase previous,
            final String baseUrl) {
        super(driver, waiter, previous, baseUrl,
                "sources?db=schoeller");
    }
}
