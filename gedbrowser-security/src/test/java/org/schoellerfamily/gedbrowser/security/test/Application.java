package org.schoellerfamily.gedbrowser.security.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Dick Schoeller
 */
@SpringBootApplication
public class Application {
    /**
     * Starts the application.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Returns the string.
     *
     * @return the resulting string
     */
    public String name() {
        return "Test application";
    }
}
