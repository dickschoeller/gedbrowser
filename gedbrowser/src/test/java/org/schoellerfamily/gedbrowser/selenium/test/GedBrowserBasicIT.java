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
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PageFactory;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PersonPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.SourcePage;
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
@SuppressWarnings("PMD.ExcessiveImports")
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

    /** */
    private PageFactory factory;

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
        final DefaultExpectations expectationsUtil = new DefaultExpectations();
        factory = new PageFactory(driver, waiter,
                expectationsUtil.create());
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public void testChildLinkNavigation() {
        println("child link navigation test");
        assertTrue("Navigation failed",
                childNavigationExercise());
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
                fathersNavigationExercise());
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
                mothersNavigationExercise());
        println();
    }

    /**
     * @return always returns true
     */
    private boolean childNavigationExercise() {
        try {
            PersonPage currentPerson =
                    factory.createPersonPage(null, baseUrl(), "I22");
            currentPerson.open();
            println("    Person is: I22");
            check("Person ID mismatch", "I22", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            println("    Navigating to child: I193");
            final int thomasChildIndex = 5;
            currentPerson = currentPerson.navigateChild(1, thomasChildIndex);
            check("Person ID mismatch", "I193", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            println("    Navigating to child: I10");
            currentPerson = currentPerson.navigateChild(1, 1);
            check("Person ID mismatch", "I10", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            println("    Navigating to child: I9");
            final int maryAmassFamilyIndex = 3;
            final int edwinChildIndex = 2;
            currentPerson = currentPerson.navigateChild(maryAmassFamilyIndex,
                    edwinChildIndex);
            check("Person ID mismatch", "I9", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());
        } finally {
            // Close the browser
            factory.getDriver().quit();
        }
        return true;
    }

    /**
     * @return always returns true
     */
    private boolean fathersNavigationExercise() {
        try {
            PersonPage currentPerson =
                    factory.createPersonPage(null, baseUrl(), "I9");
            currentPerson.open();
            println("    Person is: I9");
            check("Person ID mismatch", "I9", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            final SourcePage currentSource = factory
                    .createSourcePage(currentPerson, baseUrl(), "S33651");
            check("Title mismatch", currentSource.titleCheck());

            currentPerson = (PersonPage) currentSource.back();
            check("Person ID mismatch", "I9", currentPerson.getId());

            println("    Navigating to father: I10");
            currentPerson = currentPerson.navigateFather();
            check("Person ID mismatch", "I10", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            println("    Navigating to father: I193");
            currentPerson = currentPerson.navigateFather();
            check("Person ID mismatch", "I193", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            println("    Navigating to father: I22");
            currentPerson = currentPerson.navigateFather();
            check("Person ID mismatch", "I22", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());
        } finally {
            // Close the browser
            factory.getDriver().quit();
        }
        return true;
    }

    /**
     * @return always returns true
     */
    private boolean mothersNavigationExercise() {
        try {
            PersonPage currentPerson =
                    factory.createPersonPage(null, baseUrl(), "I15");
            currentPerson.open();
            println("    Person is: I15");
            check("Person ID mismatch", "I15", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            println("    Navigating to mother: I539");
            currentPerson = currentPerson.navigateMother();
            check("Person ID mismatch", "I539", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            println("    Navigating to mother: I237");
            currentPerson = currentPerson.navigateMother();
            check("Person ID mismatch", "I237", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            println("    Navigating to mother: I616");
            currentPerson = currentPerson.navigateMother();
            check("Person ID mismatch", "I616", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());
        } finally {
            // Close the browser
            factory.getDriver().quit();
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
     * Use this for mid-test checks.
     *
     * @param message message to display on failure
     * @param actual actual value
     */
    private void check(final String message,
            final boolean actual) {
        assertTrue(message, actual);
    }

    /**
     * Tear down after test.
     */
    @After
    public void tearDown() {
        driver.quit();
    }
}
