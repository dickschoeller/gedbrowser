package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;

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

    /**
     * Relationship between ID and validation data.
     */
    private static final Map<String, String> TITLE_MAP = new HashMap<>();
    static {
        TITLE_MAP.put("S33651",
                "Births, Marriages & Deaths Register - S33651 - gl120368");
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
        super(driver, waiter, previous, baseUrl, location(id));
        this.id = id;
        final WebElement source = driver.findElements(By.linkText(id))
                .get(0);

        final String newUrl = source.getAttribute("href");
        source.click();
        waitForPageLoaded(newUrl);
    }

    /**
     * Build the URL string for this page.
     *
     * @param id the ID of the source on the page
     * @return the built url string
     */
    private static String location(final String id) {
        return "source?db=gl120368&id=" + id;
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
