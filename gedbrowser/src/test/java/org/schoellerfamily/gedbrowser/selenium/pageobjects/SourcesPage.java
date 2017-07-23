package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
public class SourcesPage extends PageBase {
    /**
     * PageObject pattern for the sources page.
     *
     * @param factory the factory for creating more page objects
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     */
    public SourcesPage(final PageFactory factory,
            final PageBase previous, final String baseUrl) {
        super(factory, previous, baseUrl, "sources?db=schoeller");
    }

    /**
     * @param id the ID of the source to click
     * @return the page object for that
     */
    public SourcePage clickSource(final String id) {
        final WebElement element = getWebElement(By.id(id));
        final WebElement link = element.findElement(By.tagName("a"));
        link.click();
        waitForPageLoaded();
        return getFactory().createSourcePage(this, getBaseUrl(), id);
    }
}
