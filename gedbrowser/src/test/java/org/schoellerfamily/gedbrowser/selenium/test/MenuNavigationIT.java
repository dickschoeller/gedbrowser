package org.schoellerfamily.gedbrowser.selenium.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;
import org.schoellerfamily.gedbrowser.selenium.base.SauceOnDemandWatcherFactory;
import org.schoellerfamily.gedbrowser.selenium.base.TestWatcherFactory;
import org.schoellerfamily.gedbrowser.selenium.base.WebDriverFactory;
import org.schoellerfamily.gedbrowser.selenium.config.SeleniumConfig;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.IndexPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PersonPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.SourcesPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.SubmittorPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.SubmittorsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SeleniumConfig.class)
public class MenuNavigationIT implements SauceOnDemandSessionIdProvider {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
    private SessionId sessionId;

    /**
     * The factory that creates the appropriate test watcher based on current
     * environment.
     */
    private final TestWatcherFactory watcherFactory =
            new SauceOnDemandWatcherFactory(this);

    /**
     * JUnit Rule which will watch test results. Depending on the environment,
     * this could be watcher that marks the Sauce Job as passed/failed when the
     * test completes.
     */
    @Rule
    public TestWatcher testWatcher = watcherFactory.createTestWatcher();

    /**
     * This rule makes the current test name available to various consumers.
     */
    @Rule
    public TestName testName = new TestName();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSessionId() {
        if (sessionId == null) {
            logger.warn("********************** "
                    + "SESSION ID IS NULL"
                    + " *********************");
            return "";
        }
        return sessionId.toString();
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Test
    public void testIndexLinkNavigation() {
        final PersonPage currentPerson = new PersonPage(driver, waiter, null,
                baseUrl(), "I15");
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
        assertTrue("Wrong person",
                personPageBagley.getTitle().contains("James BAGLEY"));
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Test
    public void testMenuWandering() {
        final PersonPage currentPerson = new PersonPage(driver, waiter, null,
                baseUrl(), "I15");
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

        final SubmittorsPage submittorsPage = sourcesPage.clickSubmittors();
        final String submittorsUrl = submittorsPage.getCurrentUrl();
        check("Submittors URL mismatch",
                baseUrl() + "submittors?db=gl120368",
                submittorsUrl);

        final SubmittorPage submittorPage = submittorsPage.clickSubmittor("U1");
        final String submittorUrl = submittorPage.getCurrentUrl();
        assertEquals("Submittor URL mismatch",
                baseUrl() + "submittor?db=gl120368&id=U1",
                submittorUrl);
    }

    /**
     * @throws MalformedURLException if something goes awry
     */
    @Before
    public void setUp() throws MalformedURLException {
        if (driver == null) {
            driver = driverFactory.webDriver(testName);
        } else {
            logger.warn("********************** "
                    + "DRIVER ALREADY SET UP"
                    + " *********************");
        }
        if (sessionId == null) {
            sessionId = driver.getSessionId();
        }
        if (sessionId == null) {
            logger.warn("********************** "
                    + "SESSION ID IS NULL IN SETUP"
                    + " *********************");
        }
    }

    /**
     * Tear down after test.
     */
    @After
    public void tearDown() {
        driver.quit();
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
        assertEquals(message, expected, actual);
    }

    /**
     * @return the base url string for connecting to the server
     */
    private String baseUrl() {
        return "http://" + this.host + ":" + this.port + "/gedbrowser/";
    }
}
