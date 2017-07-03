package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;

/**
 * Base class for various page object classes.
 *
 * @author Dick Schoeller
 */
public class PageBase {
    /** Twenty seconds for passing to sleep for some known longish waits. */
    private static final int MEDIUM_SLEEP = 20000;

    /** Associated Selenium driver. */
    private final WebDriver driver;

    /** The base page URL. */
    private final String baseUrl;

    /** The rest of the page URL. */
    private final String location;

    /** Handles driver specific waits. */
    private final PageWaiter waiter;

    /** */
    private final PageBase previous;

    /**
     * Constructor.
     * @param driver the web driver that we are using
     * @param waiter handles driver specific waits
     * @param previous the page we are coming here from
     * @param baseUrl the URL of the page
     * @param location the location part of the URL
     */
    public PageBase(final WebDriver driver, final PageWaiter waiter,
            final PageBase previous, final String baseUrl,
            final String location) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.waiter = waiter;
        this.previous = previous;
        this.location = location;
    }

    /**
     * @return the title string for this page.
     */
    public final String getTitle() {
        return driver.getTitle();
    }

    /**
     * Open the page.
     */
    public final void open() {
        driver.get(baseUrl + location);
    }

    /**
     * Like hitting the back button.
     */
    public final void navigateBack() {
        final Navigation navigate = driver.navigate();
        navigate.back();
        waitForPageLoaded();
    }

    /**
     * @param by the lookup approach
     * @return the web element
     */
    public final WebElement getWebElement(final By by) {
        return driver.findElement(by);
    }

    /**
     * @param cssSelector CSS selector string to find Element
     * @return the web element
     */
    public final WebElement getWebElement(final String cssSelector) {
        return getWebElement(By.cssSelector(cssSelector));
    }

    /**
     * Send text keys to the Element that finds by cssSelector.
     * It shortens "driver.findElement(By.cssSelector()).sendKeys()".
     *
     * @param cssSelector CSS selector string to find Element
     * @param text the text to send to the Element
     */
    protected final void sendText(final String cssSelector, final String text) {
        getWebElement(By.cssSelector(cssSelector)).sendKeys(text);
    }

    /**
     * Is the text present in page.
     *
     * @param text the text to search for
     * @return true if the text is found
     */
    public final boolean isTextPresent(final String text) {
        return driver.getPageSource().contains(text);
    }

    /**
     * Is the Element in page.
     *
     * @param by the lookup approach
     * @return true if the element is found
     */
    public final boolean isElementPresent(final By by) {
        try {
            getWebElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Is the Element present in the DOM (by css selector).
     *
     * @param cssSelector element locater
     * @return true if the element is found
     */
    public final boolean isElementPresent(final String cssSelector) {
        return isElementPresent(By.cssSelector(cssSelector));
    }

    /**
     * Checks if the Element is in the DOM and displayed.
     *
     * @param by selector to find the element
     * @return true if the Element exists and is displayed
     */
    public final boolean isElementPresentAndDisplayed(final By by) {
        try {
            return getWebElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns the page URL.
     *
     * @return the URL string for this page
     */
    public final String getBaseUrl() {
      return baseUrl;
    }

    /**
     * Returns the location part of the URL.
     *
     * @return the URL string for this page
     */
    public final String getLocation() {
      return location;
    }

    /**
     * Returns the associated web driver for this page.
     *
     * @return the web driver
     */
    public final WebDriver getDriver() {
        return driver;
    }

    /**
     * @return this pages waiter
     */
    public final PageWaiter getPageWaiter() {
        return waiter;
    }

    /**
     * Wait for page load on real browser. Doesn't work for HTML driver.
     */
    public final void waitForPageLoaded() {
        waiter.waitForPageLoaded(driver);
    }


    /**
     * Wait for page load on real browser. Doesn't work for HTML driver.
     *
     * @param newUrl the target URL of the load
     */
    public final void waitForPageLoaded(final String newUrl) {
        waiter.waitForPageLoaded(driver, newUrl);
    }

    /**
     * @return the URL the driver thinks we are at
     */
    public final String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Go back to the previous person.
     *
     * @return the associated page object.
     */
    public final PageBase back() {
        if (previous != null) {
            navigateBack();
        }
        return previous;
    }

    /**
     * @return the page for the newly reached page
     */
    public IndexPage clickIndex() {
        final WebElement element = getMenu("index");
        element.click();
        sleep();
        waitForPageLoaded();
        return new IndexPage(getDriver(), getPageWaiter(), this, getBaseUrl(),
                getIndexLetter());
    }

    /**
     * Sleep for a bit to allow slow stuff to happen.
     */
    protected void sleep() {
        try {
            Thread.sleep(MEDIUM_SLEEP);
        } catch (InterruptedException e) {
            // Do nothing
        }
    }

    /**
     * @return the letter we expect to go to when we click index in the menu
     */
    protected String getIndexLetter() {
        return "A";
    }

    /**
     * @return the page for the newly reached page
     */
    public SourcesPage clickSources() {
        final WebElement element = getMenu("sources");
        element.click();
        waitForPageLoaded();
        return new SourcesPage(getDriver(), getPageWaiter(), this,
                getBaseUrl());
    }

    /**
     * @return the page for the newly reached page
     */
    public SubmittorsPage clickSubmittors() {
        final WebElement element = getMenu("submittors");
        element.click();
        waitForPageLoaded();
        return new SubmittorsPage(getDriver(), getPageWaiter(), this,
                getBaseUrl());
    }

    /**
     * @param name the name of the menu item
     * @return the element for that menu item
     */
    protected WebElement getMenu(final String name) {
        return getWebElement(By.id(name + "-menu"));
    }
}
