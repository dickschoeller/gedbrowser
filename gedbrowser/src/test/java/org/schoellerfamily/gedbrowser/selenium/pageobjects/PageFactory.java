package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;

/**
 * Creates page instances.
 *
 * @author Richard Schoeller
 */
public final class PageFactory {
    /** */
    private final WebDriver driver;

    /** */
    private final PageWaiter waiter;

    /** */
    private final Map<String, Expectations> expectationsMap;

    /**
     * Creates a new PageFactory.
     *
     * @param driver the driver
     * @param waiter the waiter
     * @param expectationsMap the expectations map to process
     */
    public PageFactory(final WebDriver driver, final PageWaiter waiter,
            final Map<String, Expectations> expectationsMap) {
        this.driver = driver;
        this.waiter = waiter;
        this.expectationsMap = expectationsMap;
    }

    /**
     * Gets the driver.
     *
     * @return the driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Gets the waiter.
     *
     * @return the waiter
     */
    public PageWaiter getWaiter() {
        return waiter;
    }

    /**
     * Gets the expectations map.
     *
     * @return the expectations map
     */
    public Map<String, Expectations> getExpectationsMap() {
        return expectationsMap;
    }

    /**
     * Creates the index page.
     *
     * @param previous the previous
     * @param baseUrl the base url to use
     * @return the resulting index page
     */
    public IndexPage createIndexPage(final PageBase previous,
            final String baseUrl) {
        return new IndexPage(this, previous, baseUrl);
    }

    /**
     * Creates the index page.
     *
     * @param previous the previous
     * @param baseUrl the base url to use
     * @param letter the letter
     * @return the resulting index page
     */
    public IndexPage createIndexPage(final PageBase previous,
            final String baseUrl, final String letter) {
        return new IndexPage(this, previous, baseUrl, letter);
    }

    /**
     * Creates the person page.
     *
     * @param previous the previous
     * @param baseUrl the base url to use
     * @param id the unique identifier for the target
     * @return the resulting person page
     */
    public PersonPage createPersonPage(final PageBase previous,
            final String baseUrl, final String id) {
        return new PersonPage(this, previous, baseUrl, id);
    }

    /**
     * Creates the source page.
     *
     * @param previous the previous
     * @param baseUrl the base url to use
     * @param id the unique identifier for the target
     * @return the resulting source page
     */
    public SourcePage createSourcePage(final PageBase previous,
            final String baseUrl, final String id) {
        return new SourcePage(this, previous, baseUrl, id);
    }

    /**
     * Creates the sources page.
     *
     * @param previous the previous
     * @param baseUrl the base url to use
     * @return the resulting sources page
     */
    public SourcesPage createSourcesPage(final PageBase previous,
            final String baseUrl) {
        return new SourcesPage(this, previous, baseUrl);
    }

    /**
     * Creates the submitter page.
     *
     * @param previous the previous
     * @param baseUrl the base url to use
     * @param id the unique identifier for the target
     * @return the resulting submitter page
     */
    public SubmitterPage createSubmitterPage(final PageBase previous,
            final String baseUrl, final String id) {
        return new SubmitterPage(this, previous, baseUrl, id);
    }

    /**
     * Creates the submitters page.
     *
     * @param previous the previous
     * @param baseUrl the base url to use
     * @return the resulting submitters page
     */
    public SubmittersPage createSubmittersPage(final PageBase previous,
            final String baseUrl) {
        return new SubmittersPage(this, previous, baseUrl);
    }

    /**
     * Creates the login page.
     *
     * @param previous the previous
     * @param baseUrl the base url to use
     * @return the resulting login page
     */
    public LoginPage createLoginPage(final PageBase previous,
            final String baseUrl) {
        return new LoginPage(this, previous, baseUrl);
    }
}
