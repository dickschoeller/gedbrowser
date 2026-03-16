package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
public final class IndexPage extends PageBase implements MenuPageFacade {
    /** */
    private final String letter;

    /** */
    private final MenuPage menuPage = new MenuPageImpl(this);

    /**
     * PageObject pattern for a page representing a person.
     *
     * @param factory the factory for creating more page objects
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     */
    public IndexPage(final PageFactory factory,
            final PageBase previous, final String baseUrl) {
        this(factory, previous, baseUrl, "A");
    }

    /**
     * PageObject pattern for the index page for a particular letter.
     *
     * @param factory the factory for creating more page objects
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     * @param letter the letter whose index is shown
     */
    public IndexPage(final PageFactory factory,
            final PageBase previous, final String baseUrl,
            final String letter) {
        super(factory, previous, baseUrl,
                "persons?db=schoeller&amp;letter=" + letter);
        this.letter = letter;
    }

    /**
     * Gets the index letter.
     *
     * @return the index letter
     */
    @Override
    protected String getIndexLetter() {
        return letter;
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
     * Find the link to the person ID in the page and click on it to navigate.
     *
     * @param id the ID of the person you want to navigate to
     * @return the person page
     */
    public PersonPage clickPerson(final String id) {
        final WebElement element = getPerson(id);
        element.click();
        waitForPageLoaded();
        return getFactory().createPersonPage(this, getBaseUrl(), id);
    }

    private WebElement getPerson(final String id) {
        return getWebElement(
                By.id(id)).findElement(By.tagName("a"));
    }

    /**
     * Click the link to the letter of the index specified.
     *
     * @param newLetter identifies which letter surnames will be shown
     * @return the index page
     */
    public IndexPage clickLetter(final String newLetter) {
        final WebElement element = getLetter(newLetter);
        element.click();
        final long multiplier = 4L;
        sleep(multiplier);
        waitForPageLoaded(multiplier);
        return getFactory().createIndexPage(this, getBaseUrl(), newLetter);
    }

    /**
     * Returns the letter.
     *
     * @param newLetter the new letter
     * @return the letter
     */
    protected WebElement getLetter(final String newLetter) {
        return getWebElement(By.id("letter-" + newLetter));
    }
}
