package org.schoellerfamily.gedbrowser;

import org.schoellerfamily.gedbrowser.controller.ApplicationInfoImpl;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Bootstraps the application.
 *
 * @author Richard Schoeller
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = { GeoServiceClient.class, ApplicationInfoImpl.class,
    MongoConfiguration.class })
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
        SpringApplication.run(Application.class, args);
        Thread.sleep(TWENTY_SECONDS);
    }

    /**
     * Get the application name.
     *
     * @return the application name.
     */
    public final String getApplicationName() {
        return "gedbrowser";
    }
}
