package org.schoellerfamily.geoservice;

import io.micronaut.runtime.Micronaut;

/**
 * Bootstraps the application.
 *
 * @author Richard Schoeller
 */
public final class Application {

    /**
     * Private constructor.
     */
    private Application() {
    }

    /**
     * Starts the application.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        Micronaut.run(Application.class, args);
    }
}
