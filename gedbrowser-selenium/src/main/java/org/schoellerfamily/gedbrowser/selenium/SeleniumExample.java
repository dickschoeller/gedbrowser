package org.schoellerfamily.gedbrowser.selenium;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Dick Schoeller
 */
public final class SeleniumExample {

    /** Default timeout, 3 seconds. */
    private static final int THREE_SECONDS = 3;

    /** Base URL string. */
    private static final String BASE_URL =
        "http://minitokyo.schoellerfamily.org:8080/gedbrowser/";

    /**
     * Never instanciated. Private constructor.
     */
    private SeleniumExample() {
    }

    /**
     * Main method.
     *
     * @param args standard command line argument passing
     */
    public static void main(final String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        final WebDriver driver = new FirefoxDriver();

        // Start with Grandpop
        driver.get(BASE_URL + "person?db=schoeller&id=I11");
        driver.manage().timeouts().implicitlyWait(
            THREE_SECONDS, TimeUnit.SECONDS);

        System.out.println("Page title is: " + driver.getTitle());

        assertEquals(
            "Person: I11 - Karl Frederick Schoeller (17 FEB 1907-08 APR 1985)",
            driver.getTitle());

        // Got to Dad
        final WebElement john = driver.findElement(By.partialLinkText("(I4)"));

        john.click();

        System.out.println("Page title is: " + driver.getTitle());

        assertEquals(
            "Person: I4 - John Vincent Schoeller (23 JUN 1934-14 SEP 2005)",
            driver.getTitle());

        // Go to me
        final WebElement dick = driver.findElement(By.partialLinkText("(I2)"));

        dick.click();

        System.out.println("Page title is: " + driver.getTitle());

        assertEquals(
            "Person: I2 - Richard John Schoeller (14 DEC 1958-)",
            driver.getTitle());

        // Go to Melissa
        final WebElement melissa =
            driver.findElement(By.partialLinkText("(I1)"));

        melissa.click();

        System.out.println("Page title is: " + driver.getTitle());

        assertEquals(
            "Person: I1 - Melissa Robinson Schoeller (-)",
            driver.getTitle());

        //Close the browser
        driver.quit();
    }
}
