package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.WebDriver;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;

/**
 * @author Dick Schoeller
 */
public class SubmittorPage extends PageBase {
    /** */
    private final String id;

    /**
     * @param driver the web driver
     * @param pageWaiter handles page waiting
     * @param previous the previous page object
     * @param baseUrl the base URL for this test
     * @param id the ID of the current submittor
     */
    public SubmittorPage(final WebDriver driver, final PageWaiter pageWaiter,
            final PageBase previous, final String baseUrl, final String id) {
        super(driver, pageWaiter, previous, baseUrl,
                "submittor?db=gl120368&id=" + id);
        this.id = id;
    }

    /**
     * @return the submittor ID for this page.
     */
    public String getId() {
        return id;
    }
}
