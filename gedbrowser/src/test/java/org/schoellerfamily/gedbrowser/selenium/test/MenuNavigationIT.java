package org.schoellerfamily.gedbrowser.selenium.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;
import org.schoellerfamily.gedbrowser.selenium.base.WebDriverFactory;
import org.schoellerfamily.gedbrowser.selenium.config.SeleniumConfig;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.IndexPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PageFactory;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PersonPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.SourcesPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.SubmitterPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.SubmittersPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.saucelabs.saucebindings.junit5.SauceBindingsExtension;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SeleniumConfig.class)
@SuppressWarnings("PMD.ExcessiveImports")
@Disabled("Selenium tests currently failing in setup phase")
@Slf4j
public class MenuNavigationIT {

    /** */
    @Value("${server.host:localhost}")
    private String host;

    /** */
    @Value("${server.port:8080}")
    private String port;

    /** */
    @Autowired
    private WebDriverFactory driverFactory;

    /** */
    @Autowired
    private PageWaiter waiter;

    /** */
    private RemoteWebDriver driver;

    /** */
    private boolean driverManagedByExtension = false;

    /** */
    private SessionId sessionId;

    /** */
    private PageFactory factory;

    /** */
    @RegisterExtension
    @SuppressWarnings("checkstyle:visibilitymodifier")
    public final SauceBindingsExtension sauceExtension = new SauceBindingsExtension();

    /**
     * @param testInfo information about the currently running test
     * @throws MalformedURLException if something goes awry
     */
    @BeforeEach
    public void setUp(final TestInfo testInfo) throws MalformedURLException {
        final String methodName = testInfo.getTestMethod()
                .map(m -> m.getName()).orElse("unknown");
        if (driver == null) {
            // Try to use SauceBindingsExtension-managed driver first
            if (sauceExtension != null && sauceExtension.getDriver() != null) {
                driver = (RemoteWebDriver) sauceExtension.getDriver();
                driverManagedByExtension = true;
            } else {
                // Fallback to local factory for developers running tests locally
                driver = driverFactory.webDriver(methodName);
                driverManagedByExtension = false;
            }
        } else {
            log.warn("********************** "
                    + "DRIVER ALREADY SET UP"
                    + " *********************");
        }
        if (sessionId == null && driver != null) {
            sessionId = driver.getSessionId();
        }
        if (sessionId == null) {
            log.warn("********************** "
                    + "SESSION ID IS NULL IN SETUP"
                    + " *********************");
        }
        final DefaultExpectations expectationsUtil = new DefaultExpectations();
        factory = new PageFactory(driver, waiter,
                expectationsUtil.create());
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Test
    void testIndexLinkNavigation() {
        final PersonPage currentPerson =
                factory.createPersonPage(null, baseUrl(), "I15");
        currentPerson.open();
        final IndexPage indexPageM = currentPerson.clickIndex();
        final String currentUrlM = indexPageM.getCurrentUrl();
        check("URL mismatch",
                baseUrl() + "surnames?db=gl120368&letter=M#Moore",
                currentUrlM);
        final IndexPage indexPageB = indexPageM.clickLetter("B");
        final String currentUrlB = indexPageB.getCurrentUrl();
        check("URL mismatch",
                baseUrl() + "surnames?db=gl120368&letter=B",
                currentUrlB);
        final PersonPage personPageBagley = indexPageB.clickPerson("I2561");
        assertTrue(personPageBagley.getTitle().contains("James BAGLEY"), "Wrong person");
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Test
    void testMenuWandering() {
        final PersonPage currentPerson =
                factory.createPersonPage(null, baseUrl(), "I15");
        currentPerson.open();
        final IndexPage indexPageM = currentPerson.clickIndex();
        final String currentUrlM = indexPageM.getCurrentUrl();
        check("Index M URL mismatch",
                baseUrl() + "surnames?db=gl120368&letter=M#Moore",
                currentUrlM);

        final IndexPage indexPageB = indexPageM.clickLetter("B");
        final String currentUrlB = indexPageB.getCurrentUrl();
        check("Index B URL mismatch",
                baseUrl() + "surnames?db=gl120368&letter=B",
                currentUrlB);

        final SourcesPage sourcesPage = indexPageB.clickSources();
        final String sourcesUrl = sourcesPage.getCurrentUrl();
        check("Sources URL mismatch",
                baseUrl() + "sources?db=gl120368",
                sourcesUrl);

        final SubmittersPage submittersPage = sourcesPage.clickSubmitters();
        final String submittersUrl = submittersPage.getCurrentUrl();
        check("Submitters URL mismatch",
                baseUrl() + "submitters?db=gl120368",
                submittersUrl);

        final SubmitterPage submitterPage = submittersPage.clickSubmitter("U1");
        final String submitterUrl = submitterPage.getCurrentUrl();
        assertEquals(baseUrl() + "submitter?db=gl120368&id=U1", submitterUrl,
            "Submitter URL mismatch");
    }

    /**
     * Tear down after test.
     */
    @AfterEach
    public void tearDown() {
        // Quit only if we created the driver locally.
        if (!driverManagedByExtension && driver != null) {
            driver.quit();
        }
    }

    /**
     * Use this for mid-test checks.
     *
     * @param message message to display on failure
     * @param expected expected value
     * @param actual actual value
     */
    private void check(final String message, final String expected,
            final String actual) {
        assertEquals(expected, actual, message);
    }

    /**
     * @return the base url string for connecting to the server
     */
    private String baseUrl() {
        return "http://" + this.host + ":" + this.port + "/gedbrowser/";
    }
}
