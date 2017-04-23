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
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

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

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the
     * supplied user name/access key. To use the authentication supplied by
     * environment variables or from an external file, use the no-arg
     * {@link SauceOnDemandAuthentication} constructor.
     */
    private final SauceOnDemandAuthentication authentication =
            new SauceOnDemandAuthentication(
                    System.getenv("SAUCE_USERNAME"),
                    System.getenv("SAUCE_ACCESS_KEY"));

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test
     * succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher =
        new SauceOnDemandTestWatcher(this, authentication);

    /** */
    @Rule
    public TestName testName = new TestName();

    /** */
    private WebDriver driver;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSessionId() {
        return ((RemoteWebDriver) driver).getSessionId().toString();
    }

    /**
     * @throws MalformedURLException if something goes awry
     */
    @Before
    public void setUp() throws MalformedURLException {
        driver = driverFactory.webDriver(testName);
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
     * @param wd the web driver to use for the test
     * @param pw handles driver specific waits
     * @return always returns true
     */
    private boolean childNavigationExercise(final WebDriver wd,
            final PageWaiter pw) {
        try {
            PersonPage currentPerson = new PersonPage(wd, "I22", null,
                    pw, baseUrl());
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
            PersonPage currentPerson = new PersonPage(wd, "I9", null,
                    pw, baseUrl());
            currentPerson.open();
            println("    Person is: I9");
            assertEquals("Person ID mismatch", "I9", currentPerson.getId());
            assertEquals("Person failed check", "", currentPerson.check());

            final SourcePage currentSource = new SourcePage(wd, "S33651",
                    currentPerson, pw, baseUrl());
            assertTrue("Title mismatch", currentSource.titleCheck());

            currentPerson = currentSource.back();
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
            PersonPage currentPerson = new PersonPage(wd, "I15", null,
                    pw, baseUrl());
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
