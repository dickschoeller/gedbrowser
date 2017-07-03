package org.schoellerfamily.gedbrowser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Dick Schoeller
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {
    /** Sleep during shutdown to let everything finish. */
    private static final int TWENTY_SECONDS = 20000;

    /**
     * Allow us to run gedbrowser as a standalone application with an embedded
     * application server.
     *
     * @param args command line arguments
     * @throws InterruptedException if thread sleep fails at end
     */
    public static void main(final String[] args) throws InterruptedException {
        SpringApplication.run(
                org.schoellerfamily.gedbrowser.Application.class,
                args);
        Thread.sleep(TWENTY_SECONDS);
    }

    /**
     * @return the application name.
     */
    public final String getApplicationName() {
        return "gedbrowser";
    }
}
