package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;

/**
 * @author Dick Schoeller
 */
public final class PageFactory {
    /** */
    private final WebDriver driver;

    /** */
    private final PageWaiter waiter;

    /** */
    private final Map<String, Expectations> expectationsMap;

    /**
     * Constructor.
     *
     * @param driver the webdriver for the current test
     * @param waiter the wait manager
     * @param expectationsMap the person expectations
     */
    public PageFactory(final WebDriver driver, final PageWaiter waiter,
            final Map<String, Expectations> expectationsMap) {
        this.driver = driver;
        this.waiter = waiter;
        this.expectationsMap = expectationsMap;
    }

    /**
     * @return the web driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * @return the page waiter
     */
    public PageWaiter getWaiter() {
        return waiter;
    }

    /**
     * @return the person expectations
     */
    public Map<String, Expectations> getExpectationsMap() {
        return expectationsMap;
    }

    /**
     * @param previous the previous page object
     * @param baseUrl the base URL string
     * @return the new page object
     */
    public IndexPage createIndexPage(final PageBase previous,
            final String baseUrl) {
        return new IndexPage(this, previous, baseUrl);
    }

    /**
     * @param previous the previous page object
     * @param baseUrl the base URL string
     * @param letter the letter for this page
     * @return the new page object
     */
    public IndexPage createIndexPage(final PageBase previous,
            final String baseUrl, final String letter) {
        return new IndexPage(this, previous, baseUrl, letter);
    }

    /**
     * @param previous the previous page object
     * @param baseUrl the base URL string
     * @param id the ID of the person
     * @return the new page object
     */
    public PersonPage createPersonPage(final PageBase previous,
            final String baseUrl, final String id) {
        return new PersonPage(this, previous, baseUrl, id);
    }

    /**
     * @param previous the previous page object
     * @param baseUrl the base URL string
     * @param id the ID of the source
     * @return the new page object
     */
    public SourcePage createSourcePage(final PageBase previous,
            final String baseUrl, final String id) {
        return new SourcePage(this, previous, baseUrl, id);
    }

    /**
     * @param previous the previous page object
     * @param baseUrl the base URL string
     * @return the new page object
     */
    public SourcesPage createSourcesPage(final PageBase previous,
            final String baseUrl) {
        return new SourcesPage(this, previous, baseUrl);
    }

    /**
     * @param previous the previous page object
     * @param baseUrl the base URL string
     * @param id the ID of the submittor
     * @return the new page object
     */
    public SubmittorPage createSubmittorPage(final PageBase previous,
            final String baseUrl, final String id) {
        return new SubmittorPage(this, previous, baseUrl, id);
    }

    /**
     * @param previous the previous page object
     * @param baseUrl the base URL string
     * @return the new page object
     */
    public SubmittorsPage createSubmittorsPage(final PageBase previous,
            final String baseUrl) {
        return new SubmittorsPage(this, previous, baseUrl);
    }
}
