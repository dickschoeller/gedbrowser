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
    /**
     * Allow us to run gedbrowser as a standalone application with an embedded
     * application server.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(
                org.schoellerfamily.gedbrowser.Application.class,
                args).close();
    }

    /**
     * @return the application name.
     */
    public final String getApplicationName() {
        return "gedbrowser";
    }
}
