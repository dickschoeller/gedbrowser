package org.schoellerfamily.gedbrowser.selenium.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

/**
 * @author Dick Schoeller
 */
public class WebDriverFactory {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
    public RemoteWebDriver webDriver(final TestName testName)
            throws MalformedURLException {
        final RemoteWebDriver remoteWebDriver = new RemoteWebDriver(
                getRemoteUrl(), getCapabilities(testName));
        remoteWebDriver.setLogLevel(Level.OFF);
        return remoteWebDriver;
    }


    /**
     * @return the selenium server URL
     * @throws MalformedURLException if there is a bad URL
     */
    private URL getRemoteUrl() throws MalformedURLException {
        if (useSauceLabs()) {
            logger.info("Getting Saunce Labs URL");
            final String urlString = String.format(
                    "http://%s:%s@localhost:4445/wd/hub",
                    env.getProperty("SAUCE_USERNAME"),
                    env.getProperty("SAUCE_ACCESS_KEY"));
            return new URL(urlString);
        } else {
            logger.info("Get localhost URL");
            return new URL("http://localhost:4445/wd/hub");
        }
    }

    /**
     * @param testName pass the test name in for building the saucelabs info
     * @return the capabilities structure
     */
    private DesiredCapabilities getCapabilities(final TestName testName) {
        logger.info("Capabilities name: " + browserName + ", version: "
                + browserVersion + ", platform: " + platform);
        final DesiredCapabilities capabilities = new DesiredCapabilities(
                browserName, browserVersion, Platform.fromString(platform));
        if ("firefox".equals(browserName)) {
            capabilities.setCapability("marionette", true);
        }
        if (useSauceLabs()) {
            capabilities.setCapability(
                    "tunnel-identifier", env.getProperty("TRAVIS_JOB_NUMBER"));
            capabilities.setCapability(
                    "seleniumVersion", env.getProperty("selenium.version"));
            final String travisBuildNumber =
                    env.getProperty("TRAVIS_BUILD_NUMBER");
            capabilities.setCapability("build", travisBuildNumber);
            capabilities.setCapability("name",
                    travisBuildNumber + "-" + browserName + "-" + platform + "-"
                            + testName.getMethodName());
        }
        final LoggingPreferences prefs = new LoggingPreferences();
        prefs.enable(LogType.CLIENT, Level.OFF);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, prefs);
        return capabilities;
    }

    /**
     * @return true if SAUCE_USERNAME is defined in the environment
     */
    private boolean useSauceLabs() {
        return env.getProperty("SAUCE_USERNAME") != null;
    }
}
