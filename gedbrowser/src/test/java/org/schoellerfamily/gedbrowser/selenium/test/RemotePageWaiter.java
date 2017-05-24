package org.schoellerfamily.gedbrowser.selenium.test;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Dick Schoeller
 */
public class RemotePageWaiter implements PageWaiter {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final long timeout;

    /**
     * Constructor.
     *
     * @param timeout timeout to use
     */
    public RemotePageWaiter(final long timeout) {
        this.timeout = timeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void waitForPageLoaded(final WebDriver driver) {
        logger.debug("Waiting for readState");
        final ExpectedCondition<Boolean> expectation =
                new ExpectedCondition<Boolean>() {
            /**
             * @see com.google.common.base.Function#apply(java.lang.Object)
             */
            @Override
            public Boolean apply(final WebDriver d) {
                final boolean done = ((JavascriptExecutor) d).executeScript(
                        "return document.readyState").equals("complete");
                return done;
            }
        };
        final Wait<WebDriver> wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(expectation);
        } catch (Throwable error) {
            fail("Timeout waiting for Page Load Request to complete.");
        }
        logger.debug("Waiting for maintainerMail");
        final Wait<WebDriver> wait2 = new WebDriverWait(driver, timeout);
        try {
            wait2.until(ExpectedConditions
                    .presenceOfElementLocated(By.id("maintainerMail")));
        } catch (Throwable error) {
            fail("Timeout waiting for maintainerMail.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void waitForPageLoaded(final WebDriver driver, final String newUrl) {
        logger.debug("Waiting for new URL");
        final Wait<WebDriver> wait3 = new WebDriverWait(driver, timeout);
        try {
            wait3.until(ExpectedConditions.urlToBe(newUrl));
        } catch (Throwable error) {
            fail("Timeout waiting for url.");
        }
        waitForPageLoaded(driver);
    }
}
