package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
public class SubmittorsPage extends PageBase {
    /**
     * PageObject pattern for the sources page.
     *
     * @param factory the factory for creating more page objects
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     */
    public SubmittorsPage(final PageFactory factory,
            final PageBase previous, final String baseUrl) {
        super(factory, previous, baseUrl, "submittors?db=gl120368");
    }

    /**
     * @param id the ID of the submittor to click
     * @return the page object for that
     */
    public SubmittorPage clickSubmittor(final String id) {
        final WebElement element = getWebElement(By.id(id));
        final WebElement link = element.findElement(By.tagName("a"));
        link.click();
        waitForPageLoaded();
        return getFactory().createSubmittorPage(this, getBaseUrl(), id);
    }
}
