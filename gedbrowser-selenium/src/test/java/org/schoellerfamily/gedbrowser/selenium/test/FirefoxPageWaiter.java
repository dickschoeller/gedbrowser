package org.schoellerfamily.gedbrowser.selenium.test;

import static org.junit.Assert.fail;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Dick Schoeller
 */
public class FirefoxPageWaiter implements PageWaiter {
    /** */
    private final WebDriver driver;

    /**
     * Constructor.
     *
     * @param driver the associated web driver
     */
    public FirefoxPageWaiter(final WebDriver driver) {
        this.driver = driver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void waitForPageLoaded() {
        final long timeout = 30;
        final ExpectedCondition<Boolean> expectation =
                new ExpectedCondition<Boolean>() {
            /**
             * @see com.google.common.base.Function#apply(java.lang.Object)
             */
            @Override
            public Boolean apply(final WebDriver d) {
                return ((JavascriptExecutor) d).executeScript(
                        "return document.readyState").equals("complete");
            }
        };
        final Wait<WebDriver> wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(expectation);
        } catch (Throwable error) {
            fail("Timeout waiting for Page Load Request to complete.");
        }
    }

}
