package org.schoellerfamily.gedbrowser.selenium.base;

import org.junit.rules.TestWatcher;

/**
 * @author Dick Schoeller
 */
public interface TestWatcherFactory {
    /**
     * @return a test watcher to report on test status.
     */
    TestWatcher createTestWatcher();
}
