package org.schoellerfamily.gedbrowser.selenium.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.schoellerfamily.gedbrowser.selenium.base.PageWaiter;
import org.schoellerfamily.gedbrowser.selenium.base.WebDriverFactory;
import org.schoellerfamily.gedbrowser.selenium.config.SeleniumConfig;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.LoginPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.MenuPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PageFactory;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PersonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.saucelabs.saucebindings.junit5.SauceBindingsExtension;

/**
 * Tests for the basic presentation of guest, user login, and admin login.
 *
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SeleniumConfig.class)
@SuppressWarnings("PMD.ExcessiveImports")
@Slf4j
public class LoginIT {

    /** */
    @Value("${server.host:localhost}")
    private String host;

    /** */
    @Value("${server.port:8080}")
    private String port;

    /** */
    @Value("${test.adminUsername:schoeller@comcast.net}")
    private String adminUsername;

    /** */
    @Value("${test.adminPassword:HAHANOWAY}")
    private String adminPassword;

    /** */
    @Value("${test.username:guest}")
    private String username;

    /** */
    @Value("${test.password:guest}")
    private String password;

    /** */
    @Autowired
    private PageWaiter waiter;

    /** */
    @Autowired
    private WebDriverFactory driverFactory;

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
     * Return the current session id as a string. Kept as a plain method so
     * external utilities can still call it if needed.
     *
     * @return the session id string
     */
    public String getSessionId() {
        if (sessionId == null) {
            log.warn("********************** "
                    + "SESSION ID IS NULL"
                    + " *********************");
            return "";
        }
        return sessionId.toString();
    }

    /**
     * @throws MalformedURLException if something goes awry
     */
    @BeforeEach
    public void setUp() throws MalformedURLException {
        if (driver == null) {
            if (sauceExtension != null && sauceExtension.getDriver() != null) {
                driver = (RemoteWebDriver) sauceExtension.getDriver();
                driverManagedByExtension = true;
            } else {
                driver = driverFactory.webDriver("LoginIT");
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
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Disabled("Selenium tests currently failing in setup phase")
    @Test
    void testBasicAdminLogin() {
        final PersonPage currentPerson =
                factory.createPersonPage(null, baseUrl(), "I15");
        currentPerson.open();
        final String currentUrl = currentPerson.getCurrentUrl();
        checkMenuAbsent(currentPerson, "living");
        checkMenuAbsent(currentPerson, "places");
        final LoginPage loginPage = currentPerson.clickLogin();
        final PersonPage newPerson =
                (PersonPage) loginPage.login(adminUsername, adminPassword);
        checkSame("should give the same object", currentPerson, newPerson);
        final String newUrl = newPerson.getCurrentUrl();
        checkEquals("Should be the same URL", currentUrl, newUrl);
        checkMenuPresent(newPerson, "living");
        checkMenuPresent(newPerson, "places");
        assertTrue(currentPerson.isTextPresent("Logout"), "Logout should be present");
        currentPerson.clickLogout();
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Disabled("Selenium tests currently failing in setup phase")
    @Test
    void testBasicUserLogin() {
        final PersonPage currentPerson =
                factory.createPersonPage(null, baseUrl(), "I15");
        currentPerson.open();
        final String currentUrl = currentPerson.getCurrentUrl();
        checkMenuAbsent(currentPerson, "living");
        checkMenuAbsent(currentPerson, "places");
        final LoginPage loginPage = currentPerson.clickLogin();
        final PersonPage newPerson =
                (PersonPage) loginPage.login(username, password);
        checkSame("should give the same object", currentPerson, newPerson);
        final String newUrl = newPerson.getCurrentUrl();
        checkEquals("Should be the same URL", currentUrl, newUrl);
        // Only for admin
        checkMenuAbsent(newPerson, "living");
        checkMenuAbsent(newPerson, "places");
        assertTrue(currentPerson.isTextPresent("Logout"), "Logout should be present");
        currentPerson.clickLogout();
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Disabled("Selenium tests currently failing in setup phase")
    @Test
    void testBasicUserLivingPerson() {
        final PersonPage currentPerson =
                factory.createPersonPage(null, baseUrl(), "I1");
        currentPerson.open();
        checkEquals("Title mismatch",
                "Living - I1 - gl120368", currentPerson.getTitle());
        final String currentUrl = currentPerson.getCurrentUrl();
        checkMenuAbsent(currentPerson, "living");
        checkMenuAbsent(currentPerson, "places");
        final LoginPage loginPage = currentPerson.clickLogin();
        final PersonPage newPerson =
                (PersonPage) loginPage.login(username, password);
        checkSame("should give the same object", currentPerson, newPerson);
        final String newUrl = newPerson.getCurrentUrl();
        checkEquals("Should be the same URL", currentUrl, newUrl);
        // Only for admin
        checkMenuAbsent(newPerson, "living");
        checkMenuAbsent(newPerson, "places");
        checkEquals("Title mismatch",
                "Living Williams (1950-) - I1 - gl120368",
                newPerson.getTitle());
        assertTrue(currentPerson.isTextPresent("Logout"), "Logout should be present");
        currentPerson.clickLogout();
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

    /**
     * Use this for mid-test checks.
     *
     * @param message message to display on failure
     * @param expected expected value
     * @param actual actual value
     */
    private void checkEquals(final String message, final String expected,
            final String actual) {
        assertEquals(expected, actual, message);
    }

    /**
     * Use this for mid-test checks.
     *
     * @param message message to display on failure
     * @param expected expected value
     * @param actual actual value
     */
    private void checkSame(final String message, final Object expected,
            final Object actual) {
        assertSame(expected, actual, message);
    }

    /**
     * @param page the page being checked
     * @param name the name of the menu item
     */
    private void checkMenuPresent(final MenuPage page, final String name) {
        assertTrue(page.isMenuPresent(name), "Menu " + name + " should be present");
    }

    /**
     * @param page the page being checked
     * @param name the name of the menu item
     */
    private void checkMenuAbsent(final MenuPage page, final String name) {
        assertFalse(page.isMenuPresent(name), "Menu " + name + " should not be present");
    }

    /**
     * @return the base url string for connecting to the server
     */
    private String baseUrl() {
        return "http://" + this.host + ":" + this.port + "/gedbrowser/";
    }
}
