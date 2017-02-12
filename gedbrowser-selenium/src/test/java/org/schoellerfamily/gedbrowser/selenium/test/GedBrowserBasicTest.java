package org.schoellerfamily.gedbrowser.selenium.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public final class GedBrowserBasicTest {
    /** */
    private static final boolean PRINT_NAVIGATION = "true"
            .equals(System.getProperty("printNavigation", "false"));

    /** */
    private final String host =
            System.getProperty("selenium.host", "localhost");

    /** */
    private final String port =
            System.getProperty("selenium.port", "8080");

    /** */
    private final String drivername;

    /** */
    private final WebDriver driver;

    /** */
    private final PageWaiter waiter;

    /** */
    private final int timeout;

    /**
     * @param provider the driver provider to use
     */
    public GedBrowserBasicTest(final DriverProvider provider) {
        this.drivername = provider.getName();
        this.driver = provider.getDriver();
        this.waiter = provider.getWaiter(driver);
        this.timeout = provider.getTimeout();
    }

    /**
     * @return collection of parameter arrays
     */
    @Parameters
    public static Collection<Object[]> params() {
        final List<Object[]> list = new ArrayList<>();
        println("Adding HtmlUnitDriver");
        final Object[] html = {new HtmlUnitDriverProvider()};
        list.add(html);
        final String chromeDriver =
                System.getProperty("webdriver.chrome.driver");
        if (chromeDriver == null) {
            println("Enable Chrome tests with:"
                    + " -Dwebdriver.chrome.driver=/usr/bin/chromedriver"
                    + " -Dchrome.binary=/opt/google/chrome/chrome");
        } else {
            println("Adding ChromeDriver");
            final Object[] chrome = {new ChromeDriverProvider()};
            list.add(chrome);
        }
        final String geckoDriver =
                System.getProperty("webdriver.gecko.driver");
        if (geckoDriver == null) {
            println("Enable Firefox tests with:"
                    + " -Dwebdriver.gecko.driver=/usr/local/bin/geckodriver");
        } else {
            println(
                    "Adding FirefoxDriver");
            final Object[] firefox = {new FirefoxDriverProvider()};
            list.add(firefox);
        }
        return list;
        // FIXME IE driving, Safari driving, Mobile driving
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public void testChildLinkNavigation() {
        println(drivername + " child test");
        assertTrue("Navigation failed",
                childNavigationExercise(driver, timeout, waiter));
        println();
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public void testFatherLinkNavigation() {
        println(drivername + " father test");
        assertTrue("Navigation failed",
                fathersNavigationExercise(driver, timeout, waiter));
        println();
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public void testMotherLinkNavigation() {
        println(drivername + " mother test");
        assertTrue("Navigation failed",
                mothersNavigationExercise(driver, timeout, waiter));
        println();
    }

    /**
     * @param wd the web driver to use for the test
     * @param wait the implicit wait value for this run
     * @param pw handles driver specific waits
     *
     * @return always returns true
     */
    private boolean childNavigationExercise(final WebDriver wd,
            final long wait, final PageWaiter pw) {
        try {
            wd.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);

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
     * @param wait the implicit wait value for this run
     * @param pw handles driver specific waits
     *
     * @return always returns true
     */
    private boolean fathersNavigationExercise(final WebDriver wd,
            final long wait, final PageWaiter pw) {
        try {
            wd.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);

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
     * @param wait the implicit wait value for this run
     * @param pw handles driver specific waits
     *
     * @return always returns true
     */
    private boolean mothersNavigationExercise(final WebDriver wd,
            final long wait, final PageWaiter pw) {
        try {
            wd.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);

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
    private static void println(final String string) {
        if (PRINT_NAVIGATION) {
            System.out.println(string);
        }
    }

    /**
     * Print empty line.
     */
    private static void println() {
        if (PRINT_NAVIGATION) {
            System.out.println();
        }
    }
}
