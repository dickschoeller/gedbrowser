package org.schoellerfamily.gedbrowser.selenium.base;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Creates web driver instances.
 *
 * @author Richard Schoeller
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebDriverFactory {

    /** */
    private final Environment env;

    /** */
    @Value("${selenium.browser.name:chrome}")
    private final String browserName;

    /** */
    @Value("${selenium.browser.version:}")
    private final String browserVersion;

    /** */
    @Value("${selenium.platform:}")
    private final String platform;

    /**
     * Creates and returns a new remote web driver.
     *
     * @param testName the test name to use
     * @return the resulting remote web driver
     */
    public RemoteWebDriver webDriver(final String testName) throws MalformedURLException {
        return new RemoteWebDriver(getRemoteUrl(), getCapabilities(testName));
    }

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

    private MutableCapabilities getCapabilities(final String testName) {
        log.info("Capabilities name: {}, version: {}, platform: {}", browserName, browserVersion,
            platform);
        final MutableCapabilities capabilities = new MutableCapabilities();

        // Standard W3C capability keys
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName);
        if (StringUtils.isNotEmpty(browserVersion)) {
            capabilities.setCapability("browserVersion", browserVersion);
        }
        if (StringUtils.isNotEmpty(platform)) {
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

        return capabilities;
    }

    private boolean useSauceLabs() {
        return env.getProperty("SAUCE_USERNAME") != null;
    }
}
