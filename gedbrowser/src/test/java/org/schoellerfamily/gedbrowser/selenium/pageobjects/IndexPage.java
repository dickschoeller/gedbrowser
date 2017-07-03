package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;

/**
 * @author Dick Schoeller
 */
public final class IndexPage extends PageBase {
    /** */
    private final String letter;

    /**
     * PageObject pattern for a page representing a person.
     * @param driver this is the basic web driver
     * @param waiter handles driver specific waits
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     */
    public IndexPage(final WebDriver driver,
            final PageWaiter waiter, final PageBase previous,
            final String baseUrl) {
        this(driver, waiter, previous, baseUrl, "A");
    }

    /**
     * PageObject pattern for the index page for a particular letter.
     * @param driver this is the basic web driver
     * @param waiter handles driver specific waits
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     * @param letter the letter whose index is shown
     */
    public IndexPage(final WebDriver driver,
            final PageWaiter waiter, final PageBase previous,
            final String baseUrl, final String letter) {
        super(driver, waiter, previous, baseUrl,
                "persons?db=schoeller&amp;letter=" + letter);
        this.letter = letter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIndexLetter() {
        return letter;
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
        return new PersonPage(getDriver(), getPageWaiter(), this, getBaseUrl(),
                id);
    }

    /**
     * Return the link to the person requested.
     *
     * @param id the ID string of the person link to click
     * @return the web element for the a tag inside the mother element
     */
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
        final int multiplier = 4;
//        sleep(multiplier);
        waitForPageLoaded(multiplier);
        return new IndexPage(getDriver(), getPageWaiter(), this, getBaseUrl(),
                newLetter);
    }

    /**
     * @param newLetter the letter link we want to manipulate
     * @return the element for that letter
     */
    protected WebElement getLetter(final String newLetter) {
        return getWebElement(By.id("letter-" + newLetter));
    }
}
