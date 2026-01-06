package org.schoellerfamily.gedbrowser.selenium.test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.schoellerfamily.gedbrowser.selenium.base.WebDriverFactory;
import org.schoellerfamily.gedbrowser.selenium.config.SeleniumConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.saucelabs.saucebindings.junit5.SauceBindingsExtension;

import lombok.extern.slf4j.Slf4j;

/**
 * Example test that demonstrates using the SauceBindings JUnit 5 extension
 * directly. This test is disabled by default and intended as a template for
 * running against Sauce Labs.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SeleniumConfig.class)
@Disabled("Example; enable when SAUCE_USERNAME/SAUCE_ACCESS_KEY are configured")
@Slf4j
public class SauceBindingsExampleIT {
    /** Factory to create WebDriver instances. */
    @Autowired
    private WebDriverFactory driverFactory;

    /** Sauce Labs extension to manage WebDriver lifecycle. */
    @RegisterExtension
    @SuppressWarnings("checkstyle:visibilitymodifier")
    public final SauceBindingsExtension sauceExtension = new SauceBindingsExtension();

    @Test
    void exampleSauceTest() throws Exception {
        // Prefer the extension-managed driver when available; otherwise create
        // a driver via the factory (factory will include sauce:options when
        // SAUCE env vars are set).
        final RemoteWebDriver driver;
        if (sauceExtension != null && sauceExtension.getDriver() != null) {
            driver = (RemoteWebDriver) sauceExtension.getDriver();
        } else {
            driver = driverFactory.webDriver("exampleSauceTest");
        }

        try {
            driver.get("https://www.example.com");
            log.info("Title: {}", driver.getTitle());
        } finally {
            // If the driver was created by the extension, the extension will
            // stop the session. If we created it, we must quit it ourselves.
            if (sauceExtension == null || sauceExtension.getDriver() == null) {
                driver.quit();
            }
        }
    }
}
