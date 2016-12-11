package org.schoellerfamily.gedbrowser.selenium.test;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;

/**
 * Base class for various page object classes.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.LawOfDemeter")
public class PageBase {

    // FIXME externalize base URL
    /**
     * Base URL string.
     */
    public static final String BASE_URL =
            "http://minitokyo.schoellerfamily.org:8082/gedbrowser/";

    /** Associated Selenium driver. */
    private final WebDriver driver;

    /** Page URL. */
    private final String url;

    /** Handles driver specific waits. */
    private final PageWaiter waiter;

    /**
     * Constructor.
     *
     * @param driver the web driver that we are using
     * @param url the URL of the page
     * @param waiter handles driver specific waits
     */
    public PageBase(final WebDriver driver, final String url,
            final PageWaiter waiter) {
        this.driver = driver;
        this.url = url;
        this.waiter = waiter;
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
        driver.get(url);
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
    public final String getURL() {
      return url;
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
        waiter.waitForPageLoaded();
    }

    /**
     * @return the URL the driver thinks we are at
     */
    public final String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
