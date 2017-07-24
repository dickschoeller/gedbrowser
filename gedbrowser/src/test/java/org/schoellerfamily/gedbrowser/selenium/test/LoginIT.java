package org.schoellerfamily.gedbrowser.selenium.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
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
import org.schoellerfamily.gedbrowser.selenium.pageobjects.LoginPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.MenuPage;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PageFactory;
import org.schoellerfamily.gedbrowser.selenium.pageobjects.PersonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;

/**
 * Tests for the basic presentation of guest, user login, and admin login.
 *
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SeleniumConfig.class)
@SuppressWarnings("PMD.ExcessiveImports")
public class LoginIT implements SauceOnDemandSessionIdProvider {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
     * Test navigation through index from one person to another.
     */
    @Test
    public void testBasicAdminLogin() {
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
        assertTrue("Logout should be present",
                currentPerson.isTextPresent("Logout"));
        currentPerson.clickLogout();
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Test
    public void testBasicUserLogin() {
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
        assertTrue("Logout should be present",
                currentPerson.isTextPresent("Logout"));
        currentPerson.clickLogout();
    }

    /**
     * Test navigation through index from one person to another.
     */
    @Test
    public void testBasicUserLivingPerson() {
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
        assertTrue("Logout should be present",
                currentPerson.isTextPresent("Logout"));
        currentPerson.clickLogout();
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
    private void checkEquals(final String message, final String expected,
            final String actual) {
        assertEquals(message, expected, actual);
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
        assertSame(message, expected, actual);
    }

    /**
     * @param page the page being checked
     * @param name the name of the menu item
     */
    private void checkMenuPresent(final MenuPage page, final String name) {
        assertTrue("Menu " + name + " should be present",
                page.isMenuPresent(name));
    }

    /**
     * @param page the page being checked
     * @param name the name of the menu item
     */
    private void checkMenuAbsent(final MenuPage page, final String name) {
        assertFalse("Menu " + name + " should not be present",
                page.isMenuPresent(name));
    }

    /**
     * @return the base url string for connecting to the server
     */
    private String baseUrl() {
        return "http://" + this.host + ":" + this.port + "/gedbrowser/";
    }
}
