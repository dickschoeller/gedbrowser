package org.schoellerfamily.gedbrowser.selenium.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
public final class SourcePage extends PageBase {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private static final boolean PRINT_NAVIGATION = "true"
            .equals(System.getProperty("printNavigation", "false"));

    /** Database ID associated with this page. */
    private final String id;

    /** Previous page, will always be a person page. */
    private final PersonPage previous;
    /** */
    private final String baseUrl;

    /**
     * Relationship between ID and validation data.
     */
    private static final Map<String, String> TITLE_MAP = new HashMap<>();
    static {
        TITLE_MAP.put("S33651",
                "Source: S33651 - Births, Marriages & Deaths Register");
    };

    /**
     * PageObject pattern for a page representing a person.
     * @param driver this is the basic web driver
     * @param id this is the ID of the person page being tested
     * @param previous where we came from. Can be null.
     * @param waiter handles driver specific waits
     * @param baseUrl the base URL from which all others derive
     */
    public SourcePage(final WebDriver driver, final String id,
            final PersonPage previous, final PageWaiter waiter,
            final String baseUrl) {
        super(driver, url(baseUrl, id), waiter);
        this.id = id;
        this.previous = previous;
        this.baseUrl = baseUrl;
        final WebElement source = driver.findElements(By.linkText(id))
                .get(0);
        source.click();
    }

    /**
     * Build the URL string for this page.
     *
     * @param baseUrl the base from which all URLs are derived
     * @param id the ID of the source on the page
     * @return the built url string
     */
    private static String url(final String baseUrl, final String id) {
        return baseUrl + "source?db=gl120368&id=" + id;
    }

    /**
     * Go back to the previous person.
     *
     * @return the associated page object.
     */
    public PersonPage back() {
        if (previous != null) {
            navigateBack();
        }
        return previous;
    }

    /**
     * Check that it is the right title.
     *
     * @return true if the title matches the expected value
     */
    public boolean titleCheck() {
        println("Page title is: " + getTitle());
        return getTitle().equals(TITLE_MAP.get(id));
    }

    /**
     * Build the URL string for this page.
     *
     * @param iD the ID of the person on the page
     * @return the built url string
     */
    public String url(final String iD) {
        return baseUrl() + "person?db=schoeller&id=" + iD;
    }

    /**
     * @return the base URL
     */
    private String baseUrl() {
        return baseUrl;
    }

    /**
     * Print the provide string.
     *
     * @param string the string to print
     */
    private void println(final String string) {
        if (PRINT_NAVIGATION) {
            logger.info(string);
        }
    }
}
