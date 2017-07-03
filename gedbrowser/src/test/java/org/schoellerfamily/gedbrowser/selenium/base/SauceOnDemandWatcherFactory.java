package org.schoellerfamily.gedbrowser.selenium.base;

import org.junit.rules.TestWatcher;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

/**
 * @author Dick Schoeller
 */
public final class SauceOnDemandWatcherFactory implements TestWatcherFactory {
    /**
     * The session ID provider. As a rule, this is the current instance of the
     * test class.
     */
    private final SauceOnDemandSessionIdProvider provider;

    /**
     * @param provider the provider for the session ID.
     */
    public SauceOnDemandWatcherFactory(
            final SauceOnDemandSessionIdProvider provider) {
        this.provider = provider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestWatcher createTestWatcher() {
        if (useSauceLabs()) {
            return new SauceOnDemandTestWatcher(provider, authentication());
        }
        // Null watcher as a fallback
        return new TestWatcher() {
        };
    }

    /**
     * @return true if SAUCE_USERNAME is defined in the environment
     */
    private boolean useSauceLabs() {
        return System.getenv("SAUCE_USERNAME") != null;
    }

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the
     * supplied user name/access key. To use the authentication supplied by
     * environment variables or from an external file, use the no-arg
     * {@link SauceOnDemandAuthentication} constructor.
     *
     * @return the created authentication
     */
    private SauceOnDemandAuthentication authentication() {
        return new SauceOnDemandAuthentication(System.getenv("SAUCE_USERNAME"),
                System.getenv("SAUCE_ACCESS_KEY"));
    }
}
