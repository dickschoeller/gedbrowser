package org.schoellerfamily.gedbrowser.selenium.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author Dick Schoeller
 */
@Configuration
public class SeleniumConfig {
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

    /** */
    @Value("${selenium.timeout:30}")
    private long timeout;

    /**
     * @return the webdriver
     * @throws MalformedURLException if there is a bogus URL
     */
    @Bean
    public WebDriver webDriver() throws MalformedURLException {
        final RemoteWebDriver remoteWebDriver = new RemoteWebDriver(
                getRemoteUrl(), getDesiredCapabilities());
        remoteWebDriver.setLogLevel(Level.OFF);
        return remoteWebDriver;
    }

    /**
     * @return the page waiter
     */
    @Bean
    public PageWaiter pageWaiter() {
        logger.info("Getting page waiter");
        return new RemotePageWaiter(timeout);
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
     * @return the capabilities structure
     */
    private DesiredCapabilities getDesiredCapabilities() {
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
            capabilities.setCapability(
                    "build", env.getProperty("TRAVIS_BUILD_NUMBER"));
            capabilities.setCapability("name",
                    env.getProperty("TRAVIS_BUILD_NUMBER") + "-" + browserName
                            + "-" + platform);
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
