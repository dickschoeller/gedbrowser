package org.schoellerfamily.gedbrowser.selenium.test;

import static org.junit.Assert.fail;

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
    /**
     * {@inheritDoc}
     */
    @Override
    public void waitForPageLoaded(final WebDriver driver) {
        final long timeout = 30;
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
        final Wait<WebDriver> wait2 = new WebDriverWait(driver, timeout);
        try {
            wait2.until(ExpectedConditions
                    .presenceOfElementLocated(By.id("maintainerMail")));
        } catch (Throwable error) {
            fail("Timeout waiting for maintainerMail.");
        }
    }

}