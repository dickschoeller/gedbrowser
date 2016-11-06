package org.schoellerfamily.gedbrowser.selenium.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * @author Dick Schoeller
 */
public class GedBrowserBasicTest {
    /** Standard timeout. */
    private static final int SHORT_TIMEOUT = 3;
    /** Long timeout. */
    private static final int LONG_TIMEOUT = 5;


    // FIXME IE driving, Safari driving, Mobile driving

// FIXME firefox drivers still not working Jenkins
//    /**
//     * This test runs through the links by a partial of the text. This is done
//     * because the UI currently doesn't use IDs.
//     */
//    @Test
//    public final void testChildLinkNavigationFirefox() {
//        // Create a new instance of the Firefox driver
//        // Notice that the remainder of the code relies on the interface,
//        // not the implementation.
//        final WebDriver driver = new FirefoxDriver();
//        final PageWaiter waiter = new ChromePageWaiter(driver);
//
//        System.out.println("Firefox child test");
//        childNavigationExercise(driver, LONG_TIMEOUT, waiter);
//        System.out.println();
//    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public final void testChildLinkNavigationChrome() {
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        final WebDriver driver = new ChromeDriver();
        final PageWaiter waiter = new ChromePageWaiter(driver);

        System.out.println("Chrome child test");
        childNavigationExercise(driver, LONG_TIMEOUT, waiter);
        System.out.println();
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public final void testChildLinkNavigationHtmlUnit() {
        // Create a new instance of the HtmlUnit driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        final WebDriver driver = new HtmlUnitDriver();
        final PageWaiter waiter = new HtmlUnitPageWaiter(driver);

        System.out.println("HtmlUnit child test");
        childNavigationExercise(driver, SHORT_TIMEOUT, waiter);
        System.out.println();
    }

// FIXME firefox drivers still not working Jenkins
//    /**
//     * This test runs through the links by a partial of the text. This is done
//     * because the UI currently doesn't use IDs.
//     */
//    @Test
//    public final void testFatherLinkNavigationFirefox() {
//        // Create a new instance of the Chrome driver
//        // Notice that the remainder of the code relies on the interface,
//        // not the implementation.
//        final WebDriver driver = new FirefoxDriver();
//        final PageWaiter waiter = new ChromePageWaiter(driver);
//
//        System.out.println("Firefox father test");
//        fathersNavigationExercise(driver, LONG_TIMEOUT, waiter);
//        System.out.println();
//    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public final void testFatherLinkNavigationChrome() {
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        final WebDriver driver = new ChromeDriver();
        final PageWaiter waiter = new ChromePageWaiter(driver);

        System.out.println("Chrome father test");
        fathersNavigationExercise(driver, LONG_TIMEOUT, waiter);
        System.out.println();
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public final void testFatherLinkNavigationHtmlUnit() {
        // Create a new instance of the HtmlUnit driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        final WebDriver driver = new HtmlUnitDriver();
        final PageWaiter waiter = new HtmlUnitPageWaiter(driver);

        System.out.println("HtmlUnit father test");
        fathersNavigationExercise(driver, SHORT_TIMEOUT, waiter);
        System.out.println();
    }

// FIXME firefox drivers still not working Jenkins
//    /**
//     * This test runs through the links by a partial of the text. This is done
//     * because the UI currently doesn't use IDs.
//     */
//    @Test
//    public final void testMotherLinkNavigationFirefox() {
//        // Create a new instance of the Firefox driver
//        // Notice that the remainder of the code relies on the interface,
//        // not the implementation.
//        final WebDriver driver = new FirefoxDriver();
//        final PageWaiter waiter = new ChromePageWaiter(driver);
//
//        System.out.println("Firefox mother test");
//        mothersNavigationExercise(driver, LONG_TIMEOUT, waiter);
//        System.out.println();
//    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public final void testMotherLinkNavigationChrome() {
        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        final WebDriver driver = new ChromeDriver();
        final PageWaiter waiter = new ChromePageWaiter(driver);

        System.out.println("Chrome mother test");
        mothersNavigationExercise(driver, LONG_TIMEOUT, waiter);
        System.out.println();
    }

    /**
     * This test runs through the links by a partial of the text. This is done
     * because the UI currently doesn't use IDs.
     */
    @Test
    public final void testMotherLinkNavigationHtmlUnit() {
        // Create a new instance of the HtmlUnit driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        final WebDriver driver = new HtmlUnitDriver();
        final PageWaiter waiter = new HtmlUnitPageWaiter(driver);

        System.out.println("HtmlUnit mother test");
        mothersNavigationExercise(driver, SHORT_TIMEOUT, waiter);
        System.out.println();
    }

    /**
     * @param driver the web driver to use for the test
     * @param wait the implicit wait value for this run
     * @param waiter handles driver specific waits
     */
    private void childNavigationExercise(final WebDriver driver,
            final long wait, final PageWaiter waiter) {
        try {
            driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);

            // Matthias
            PersonPage currentPerson = new PersonPage(driver, "I180", null,
                    waiter);
            currentPerson.open();
            assertEquals("I180", currentPerson.getId());
            assertEquals("", currentPerson.check());

            // Johann Martin
            currentPerson = currentPerson.navigateChild(1, 1);
            assertEquals("I3554", currentPerson.getId());
            assertEquals("", currentPerson.check());

            // Anna Maria
            currentPerson = currentPerson.navigateChild(1, 1);
            assertEquals("I3881", currentPerson.getId());
            assertEquals("", currentPerson.check());

            // Go to Maria Berta Faigle
            currentPerson = currentPerson.navigateChild(1, 1);
            assertEquals("I3891", currentPerson.getId());
            assertEquals("", currentPerson.check());
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    /**
     * @param driver the web driver to use for the test
     * @param wait the implicit wait value for this run
     * @param waiter handles driver specific waits
     */
    private void fathersNavigationExercise(final WebDriver driver,
            final long wait, final PageWaiter waiter) {
        try {
            driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);

            // Grandpop
            PersonPage currentPerson = new PersonPage(driver, "I11", null,
                    waiter);
            currentPerson.open();
            assertEquals("I11", currentPerson.getId());
            assertEquals("", currentPerson.check());

            // Fred
            currentPerson = currentPerson.navigateFather();
            assertEquals("I32", currentPerson.getId());
            assertEquals("", currentPerson.check());

            SourcePage currentSource =
                    new SourcePage(driver, "S21", currentPerson, waiter);
            assertTrue(currentSource.titleCheck());

            currentPerson = currentSource.back();
            assertEquals("I32", currentPerson.getId());

            // Johannes
            currentPerson = currentPerson.navigateFather();
            assertEquals("I99", currentPerson.getId());
            assertEquals("", currentPerson.check());

            // Matthias
            currentPerson = currentPerson.navigateFather();
            assertEquals("I180", currentPerson.getId());
            assertEquals("", currentPerson.check());
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    /**
     * @param driver the web driver to use for the test
     * @param wait the implicit wait value for this run
     * @param waiter handles driver specific waits
     */
    private void mothersNavigationExercise(final WebDriver driver,
            final long wait, final PageWaiter waiter) {
        try {
            driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);

            // Melissa
            PersonPage currentPerson = new PersonPage(driver, "I11", null,
                    waiter);
            currentPerson.open();
            assertEquals("I11", currentPerson.getId());
            assertEquals("", currentPerson.check());

            // Lisa
            currentPerson = currentPerson.navigateMother();
            assertEquals("I33", currentPerson.getId());
            assertEquals("", currentPerson.check());

            // Estelle
            currentPerson = currentPerson.navigateMother();
            assertEquals("I117", currentPerson.getId());
            assertEquals("", currentPerson.check());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
