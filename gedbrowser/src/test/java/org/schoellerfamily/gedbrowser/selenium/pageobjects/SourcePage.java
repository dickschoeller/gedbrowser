package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
public final class SourcePage extends PageBase implements MenuPageFacade {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private static final boolean PRINT_NAVIGATION = "true"
            .equals(System.getProperty("printNavigation", "false"));

    /** Database ID associated with this page. */
    private final String id;

    /** */
    private final MenuPage menuPage = new MenuPageImpl(this);

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
     *
     * @param factory the factory for creating more page objects
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     * @param id this is the ID of the person page being tested
     */
    public SourcePage(final PageFactory factory, final PageBase previous,
            final String baseUrl, final String id) {
        super(factory, previous, baseUrl, location(id));
        this.id = id;
        final WebElement source = getDriver().findElements(By.linkText(id))
                .get(0);

        final String newUrl = source.getAttribute("href");
        source.click();
        waitForPageLoaded(newUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuPage getMenuPage() {
        return menuPage;
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
