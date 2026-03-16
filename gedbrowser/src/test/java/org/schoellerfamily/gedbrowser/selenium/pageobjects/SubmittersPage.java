package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Represents submitters page.
 *
 * @author Richard Schoeller
 */
public final class SubmittersPage extends PageBase implements MenuPageFacade {
    /** */
    private final MenuPage menuPage = new MenuPageImpl(this);

    /**
     * PageObject pattern for the sources page.
     *
     * @param factory the factory for creating more page objects
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     */
    public SubmittersPage(final PageFactory factory,
            final PageBase previous, final String baseUrl) {
        super(factory, previous, baseUrl, "submitters?db=gl120368");
    }

    /**
     * Gets the menu page.
     *
     * @return the menu page
     */
    @Override
    public MenuPage getMenuPage() {
        return menuPage;
    }

    /**
     * Executes click submitter.
     *
     * @param id the unique identifier for the target
     * @return the resulting submitter page
     */
    public SubmitterPage clickSubmitter(final String id) {
        final WebElement element = getWebElement(By.id(id));
        final WebElement link = element.findElement(By.tagName("a"));
        link.click();
        waitForPageLoaded();
        return getFactory().createSubmitterPage(this, getBaseUrl(), id);
    }
}
