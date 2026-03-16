package org.schoellerfamily.gedbrowser.selenium.base;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public final class RemotePageWaiter implements PageWaiter {

    /** */
    private final long timeout;

    /**
     * Creates a new RemotePageWaiter.
     *
     * @param timeout the timeout
     */
    public RemotePageWaiter(final long timeout) {
        this.timeout = timeout;
    }

    /**
     * Executes wait for page loaded.
     *
     * @param driver the driver
     */
    @Override
    public void waitForPageLoaded(final WebDriver driver) {
        waitForPageLoaded(driver, 1);
    }

    /**
     * Executes wait for page loaded.
     *
     * @param driver the driver
     * @param multiplier the multiplier
     */
    @Override
    public void waitForPageLoaded(final WebDriver driver, final long multiplier) {
        log.debug("Waiting for readyState");
        final ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            /**
             * Returns the boolean.
             *
             * @param d the d
             * @return the resulting boolean
             */
            @Override
            public Boolean apply(final WebDriver d) {
                return "complete".equals(
                    ((JavascriptExecutor) d).executeScript("return document.readyState"));
            }
        };
        final Wait<WebDriver> wait = new WebDriverWait(driver,
            Duration.ofSeconds(timeout * multiplier));
        try {
            wait.until(expectation);
        } catch (Throwable error) {
            fail("Timeout waiting for Page Load Request to complete.");
        }
        log.debug("Waiting for maintainerMail");
        final Wait<WebDriver> wait2 = new WebDriverWait(driver,
            Duration.ofSeconds(timeout * multiplier));
        try {
            wait2.until(ExpectedConditions.presenceOfElementLocated(By.id("maintainerMail")));
        } catch (Throwable error) {
            fail("Timeout waiting for maintainerMail.");
        }
    }

    /**
     * Executes wait for page loaded.
     *
     * @param driver the driver
     * @param newUrl the new url to use
     */
    @Override
    public void waitForPageLoaded(final WebDriver driver, final String newUrl) {
        waitForPageLoaded(driver, newUrl, 1);
    }

    /**
     * Executes wait for page loaded.
     *
     * @param driver the driver
     * @param newUrl the new url to use
     * @param multiplier the multiplier
     */
    @Override
    public void waitForPageLoaded(final WebDriver driver, final String newUrl,
        final int multiplier) {
        log.debug("Waiting for new URL");
        final Wait<WebDriver> wait3 = new WebDriverWait(driver,
            Duration.ofSeconds(timeout * multiplier));
        try {
            wait3.until(ExpectedConditions.urlToBe(newUrl));
        } catch (Throwable error) {
            fail("Timeout waiting for url.");
        }
        waitForPageLoaded(driver);
    }
}
