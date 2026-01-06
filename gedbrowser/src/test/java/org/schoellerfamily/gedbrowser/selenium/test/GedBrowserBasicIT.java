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
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PageFactory;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PersonPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.SourcePage;
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
public final class GedBrowserBasicIT {

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
     * Return the current session id as a string. Kept as a plain method so external
     * utilities can still call it if needed.
     *
     * @return the session id string
     */
    public String getSessionId() {
        if (sessionId == null) {
            log.warn("********************** " + "SESSION ID IS NULL" + " *********************");
            return "";
        }
        return sessionId.toString();
    }

    /**
     * @param testInfo information about the currently running test
     * @throws MalformedURLException if something goes awry
     */
    @BeforeEach
    public void setUp(final TestInfo testInfo) throws MalformedURLException {
        final String methodName = testInfo.getTestMethod().map(m -> m.getName()).orElse("unknown");
        if (driver == null) {
            if (sauceExtension != null && sauceExtension.getDriver() != null) {
                driver = (RemoteWebDriver) sauceExtension.getDriver();
                driverManagedByExtension = true;
            } else {
                driver = driverFactory.webDriver(methodName);
                driverManagedByExtension = false;
            }
        } else {
            log.warn(
                "********************** " + "DRIVER ALREADY SET UP" + " *********************");
        }
        if (sessionId == null && driver != null) {
            sessionId = driver.getSessionId();
        }
        if (sessionId == null) {
            log.warn("********************** " + "SESSION ID IS NULL IN SETUP"
                + " *********************");
        }
        final DefaultExpectations expectationsUtil = new DefaultExpectations();
        factory = new PageFactory(driver, waiter, expectationsUtil.create());
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    void testChildLinkNavigation() {
        println("child link navigation test");
        assertTrue(childNavigationExercise(), "Navigation failed");
        println();
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    void testFatherLinkNavigation() {
        println("father link navigation test");
        assertTrue(fathersNavigationExercise(), "Navigation failed");
        println();
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    void testMotherLinkNavigation() {
        println("mother link navigation test");
        assertTrue(mothersNavigationExercise(), "Navigation failed");
        println();
    }

    /**
     * @return always returns true
     */
    private boolean childNavigationExercise() {
        try {
            PersonPage currentPerson = factory.createPersonPage(null, baseUrl(), "I22");
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
            currentPerson = currentPerson.navigateChild(maryAmassFamilyIndex, edwinChildIndex);
            check("Person ID mismatch", "I9", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());
            assertEquals("", currentPerson.check(), "Person failed check");
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
            PersonPage currentPerson = factory.createPersonPage(null, baseUrl(), "I9");
            currentPerson.open();
            println("    Person is: I9");
            check("Person ID mismatch", "I9", currentPerson.getId());
            check("Person failed check", "", currentPerson.check());

            final SourcePage currentSource = factory.createSourcePage(currentPerson, baseUrl(),
                "S33651");
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
            assertEquals("", currentPerson.check(), "Person failed check");
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
            PersonPage currentPerson = factory.createPersonPage(null, baseUrl(), "I15");
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
            assertEquals("", currentPerson.check(), "Person failed check");
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
            log.info(string);
        }
    }

    /**
     * Print empty line.
     */
    private void println() {
        if (PRINT_NAVIGATION) {
            log.info("");
        }
    }

    /**
     * Use this for mid-test checks.
     *
     * @param message  message to display on failure
     * @param expected expected value
     * @param actual   actual value
     */
    private void check(final String message, final String expected, final String actual) {
        assertEquals(expected, actual, message);
    }

    /**
     * Use this for mid-test checks.
     *
     * @param message message to display on failure
     * @param actual  actual value
     */
    private void check(final String message, final boolean actual) {
        assertTrue(actual, message);
    }

    /**
     * Tear down after test.
     */
    @AfterEach
    public void tearDown() {
        if (!driverManagedByExtension && driver != null) {
            driver.quit();
        }
    }
}
