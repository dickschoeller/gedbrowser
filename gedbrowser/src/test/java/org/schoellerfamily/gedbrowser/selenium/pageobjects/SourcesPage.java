package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Represents sources page.
 *
 * @author Richard Schoeller
 */
public final class SourcesPage extends PageBase implements MenuPageFacade {
    /** */
    private final MenuPage menuPage = new MenuPageImpl(this);

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
     * Gets the menu page.
     *
     * @return the menu page
     */
    @Override
    public MenuPage getMenuPage() {
        return menuPage;
    }

    /**
     * Executes click source.
     *
     * @param id the unique identifier for the target
     * @return the resulting source page
     */
    public SourcePage clickSource(final String id) {
        final WebElement element = getWebElement(By.id(id));
        final WebElement link = element.findElement(By.tagName("a"));
        link.click();
        waitForPageLoaded();
        return getFactory().createSourcePage(this, getBaseUrl(), id);
    }
}
