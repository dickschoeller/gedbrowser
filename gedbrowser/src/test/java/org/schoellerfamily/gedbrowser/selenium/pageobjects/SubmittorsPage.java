package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;

/**
 * @author Dick Schoeller
 */
public class SubmittorsPage extends PageBase {
    /**
     * PageObject pattern for the sources page.
     *
     * @param driver this is the basic web driver
     * @param waiter handles driver specific waits
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     */
    public SubmittorsPage(final WebDriver driver,
            final PageWaiter waiter, final PageBase previous,
            final String baseUrl) {
        super(driver, waiter, previous, baseUrl,
                "submittors?db=gl120368");
    }

    /**
     * @param id the ID of the submittor to click
     * @return the page object for that
     */
    public SubmittorPage clickSubmittor(final String id) {
        final WebElement element = getWebElement(By.id(id));
        final WebElement link = element.findElement(By.tagName("a"));
        link.click();
        return new SubmittorPage(getDriver(), getPageWaiter(), this,
                getBaseUrl(), id);
    }
}
