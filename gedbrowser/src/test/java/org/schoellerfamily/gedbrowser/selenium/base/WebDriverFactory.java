package org.schoellerfamily.gedbrowser.selenium.base;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public class WebDriverFactory {

    /** */
    @Autowired
    private Environment env;

    /** */
    @Value("${selenium.browser.name:chrome}")
    private String browserName;

    /** */
    @Value("${selenium.browser.version:}")
    private String browserVersion;

    /** */
    @Value("${selenium.platform:}")
    private String platform;

    /**
     * @param testName pass the test name in for building the saucelabs info
     * @return the webdriver
     * @throws MalformedURLException if there is a bogus URL
     */
    public RemoteWebDriver webDriver(final String testName) throws MalformedURLException {
        final RemoteWebDriver remoteWebDriver = new RemoteWebDriver(getRemoteUrl(),
            getCapabilities(testName));
        // RemoteWebDriver does not expose setLogLevel; logging preferences
        // are applied via capabilities above.
        return remoteWebDriver;
    }

    /**
     * @return the selenium server URL
     * @throws MalformedURLException if there is a bad URL
     */
    private URL getRemoteUrl() throws MalformedURLException {
        if (useSauceLabs()) {
            log.info("Using Sauce Labs remote URL");
            // Use official Sauce Labs endpoint. Authentication is normally
            // handled via capability (sauce:options) or HTTP basic auth.
            return URI.create("https://ondemand.saucelabs.com/wd/hub").toURL();
        } else {
            log.info("Using local Selenium Grid URL");
            return URI.create("http://localhost:4445/wd/hub").toURL();
        }
    }

    /**
     * @param testName pass the test name in for building the saucelabs info
     * @return the capabilities structure
     */
    private MutableCapabilities getCapabilities(final String testName) {
        log.info("Capabilities name: {}, version: {}, platform: {}", browserName, browserVersion,
            platform);
        final MutableCapabilities capabilities = new MutableCapabilities();

        // Standard W3C capability keys
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName);
        if (browserVersion != null && !browserVersion.isEmpty()) {
            capabilities.setCapability("browserVersion", browserVersion);
        }
        if (platform != null && !platform.isEmpty()) {
            // W3C uses "platformName" (string) instead of the old Platform enum
            capabilities.setCapability("platformName", platform);
        }

        if ("firefox".equalsIgnoreCase(browserName)) {
            // Keep legacy behaviour if needed by remote grid
            capabilities.setCapability("moz:firefoxOptions", new MutableCapabilities());
        }

        if (useSauceLabs()) {
            // Provide a minimal sauce:options block so Sauce receives username
            // and access key if the environment requires it. Sauce Bindings
            // will often enrich/override these when used.
            final MutableCapabilities sauceOptions = new MutableCapabilities();
            final String username = env.getProperty("SAUCE_USERNAME");
            final String accessKey = env.getProperty("SAUCE_ACCESS_KEY");
            if (username != null) {
                sauceOptions.setCapability("username", username);
            }
            if (accessKey != null) {
                sauceOptions.setCapability("accessKey", accessKey);
            }
            final String travisBuildNumber = env.getProperty("TRAVIS_BUILD_NUMBER");
            if (travisBuildNumber != null) {
                sauceOptions.setCapability("build", travisBuildNumber);
                sauceOptions.setCapability("name",
                    travisBuildNumber + "-" + browserName + "-" + platform + "-" + testName);
            }
            capabilities.setCapability("sauce:options", sauceOptions);
        }

        final LoggingPreferences prefs = new LoggingPreferences();
        prefs.enable(LogType.CLIENT, Level.OFF);
        // Use the W3C-compatible key for Chrome logging prefs.
        // CapabilityType.LOGGING_PREFS
        // may not be present in all Selenium 4 distributions, so set the string key.
        capabilities.setCapability("goog:loggingPrefs", prefs);
        return capabilities;
    }

    /**
     * @return true if SAUCE_USERNAME is defined in the environment
     */
    private boolean useSauceLabs() {
        return env.getProperty("SAUCE_USERNAME") != null;
    }
}
