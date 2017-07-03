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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;
import org.schoellerfamily.gedbrowser.selenium.base.SauceOnDemandWatcherFactory;
import org.schoellerfamily.gedbrowser.selenium.base.TestWatcherFactory;
import org.schoellerfamily.gedbrowser.selenium.base.WebDriverFactory;
import org.schoellerfamily.gedbrowser.selenium.config.SeleniumConfig;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.IndexPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PersonPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.SourcePage;
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
public final class GedBrowserBasicIT implements SauceOnDemandSessionIdProvider {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private static final boolean PRINT_NAVIGATION = "true"
            .equals(System.getProperty("printNavigation", "false"));

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
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public void testChildLinkNavigation() {
        println("child link navigation test");
        assertTrue("Navigation failed",
                childNavigationExercise(driver, waiter));
        println();
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public void testFatherLinkNavigation() {
        println("father link navigation test");
        assertTrue("Navigation failed",
                fathersNavigationExercise(driver, waiter));
        println();
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public void testMotherLinkNavigation() {
        println("mother link navigation test");
        assertTrue("Navigation failed",
                mothersNavigationExercise(driver, waiter));
        println();
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Test
    public void testIndexLinkNavigation() {
        PersonPage currentPerson = new PersonPage(driver, waiter, null,
                baseUrl(), "I15");
        currentPerson.open();
        final IndexPage indexPageM = currentPerson.clickIndex();
        final String currentUrlM = indexPageM.getCurrentUrl();
        assertEquals("URL mismatch",
                baseUrl() + "surnames?db=gl120368&letter=M#Moore",
                currentUrlM);
        final IndexPage indexPageB = indexPageM.clickLetter("B");
        final String currentUrlB = indexPageB.getCurrentUrl();
        assertEquals("URL mismatch",
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
        PersonPage currentPerson = new PersonPage(driver, waiter, null,
                baseUrl(), "I15");
        currentPerson.open();
        final IndexPage indexPageM = currentPerson.clickIndex();
        final String currentUrlM = indexPageM.getCurrentUrl();
        assertEquals("Index M URL mismatch",
                baseUrl() + "surnames?db=gl120368&letter=M#Moore",
                currentUrlM);

        final IndexPage indexPageB = indexPageM.clickLetter("B");
        final String currentUrlB = indexPageB.getCurrentUrl();
        assertEquals("Index B URL mismatch",
                baseUrl() + "surnames?db=gl120368&letter=B",
                currentUrlB);

        final SourcesPage sourcesPage = indexPageB.clickSources();
        final String sourcesUrl = sourcesPage.getCurrentUrl();
        assertEquals("Sources URL mismatch",
                baseUrl() + "sources?db=gl120368",
                sourcesUrl);

        final SubmittorsPage submittorsPage = sourcesPage.clickSubmittors();
        final String submittorsUrl = submittorsPage.getCurrentUrl();
        assertEquals("Submittors URL mismatch",
                baseUrl() + "submittors?db=gl120368",
                submittorsUrl);

        final SubmittorPage submittorPage = submittorsPage.clickSubmittor("U1");
        final String submittorUrl = submittorPage.getCurrentUrl();
        assertEquals("Submittor URL mismatch",
                baseUrl() + "submittor?db=gl120368&id=U1",
                submittorUrl);
    }

    /**
     * @param wd the web driver to use for the test
     * @param pw handles driver specific waits
     * @return always returns true
     */
    private boolean childNavigationExercise(final WebDriver wd,
            final PageWaiter pw) {
        try {
            PersonPage currentPerson = new PersonPage(wd, pw, null,
                    baseUrl(), "I22");
            currentPerson.open();
            println("    Person is: I22");
            assertEquals("Person ID mismatch", "I22", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            println("    Navigating to child: I193");
            final int thomasChildIndex = 5;
            currentPerson = currentPerson.navigateChild(1, thomasChildIndex);
            assertEquals("Person ID mismatch", "I193", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            println("    Navigating to child: I10");
            currentPerson = currentPerson.navigateChild(1, 1);
            assertEquals("Person ID mismatch", "I10", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            println("    Navigating to child: I9");
            final int maryAmassFamilyIndex = 3;
            final int edwinChildIndex = 2;
            currentPerson = currentPerson.navigateChild(maryAmassFamilyIndex,
                    edwinChildIndex);
            assertEquals("Person ID mismatch", "I9", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());
        } finally {
            // Close the browser
            wd.quit();
        }
        return true;
    }

    /**
     * @param wd the web driver to use for the test
     * @param pw handles driver specific waits
     * @return always returns true
     */
    private boolean fathersNavigationExercise(final WebDriver wd,
            final PageWaiter pw) {
        try {
            PersonPage currentPerson = new PersonPage(wd, pw, null,
                    baseUrl(), "I9");
            currentPerson.open();
            println("    Person is: I9");
            assertEquals("Person ID mismatch", "I9", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            final SourcePage currentSource = new SourcePage(wd, "S33651",
                    currentPerson, pw, baseUrl());
            assertTrue("Title mismatch", currentSource.titleCheck());

            currentPerson = (PersonPage) currentSource.back();
            assertEquals("Person ID mismatch", "I9", currentPerson.getId());

            println("    Navigating to father: I10");
            currentPerson = currentPerson.navigateFather();
            assertEquals("Person ID mismatch", "I10", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            println("    Navigating to father: I193");
            currentPerson = currentPerson.navigateFather();
            assertEquals("Person ID mismatch", "I193", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            println("    Navigating to father: I22");
            currentPerson = currentPerson.navigateFather();
            assertEquals("Person ID mismatch", "I22", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());
        } finally {
            // Close the browser
            wd.quit();
        }
        return true;
    }

    /**
     * @param wd the web driver to use for the test
     * @param pw handles driver specific waits
     * @return always returns true
     */
    private boolean mothersNavigationExercise(final WebDriver wd,
            final PageWaiter pw) {
        try {
            PersonPage currentPerson = new PersonPage(wd, pw, null,
                    baseUrl(), "I15");
            currentPerson.open();
            println("    Person is: I15");
            assertEquals("Person ID mismatch", "I15", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            println("    Navigating to mother: I539");
            currentPerson = currentPerson.navigateMother();
            assertEquals("Person ID mismatch", "I539", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            println("    Navigating to mother: I237");
            currentPerson = currentPerson.navigateMother();
            assertEquals("Person ID mismatch", "I237", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            println("    Navigating to mother: I616");
            currentPerson = currentPerson.navigateMother();
            assertEquals("Person ID mismatch", "I616", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());
        } finally {
            // Close the browser
            wd.quit();
        }
        return true;
    }

    /**
     * @return the base url string for connecting to the server
     */
    private String baseUrl() {
        return "http://" + this.host + ":" + this.port + "/gedbrowser/";
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

    /**
     * Print empty line.
     */
    private void println() {
        if (PRINT_NAVIGATION) {
            logger.info("");
        }
    }

    /**
     * Tear down after test.
     */
    @After
    public void tearDown() {
        driver.quit();
    }
}
