package org.schoellerfamily.gedbrowser.security.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Dick Schoeller
 */
@SpringBootApplication
public class Application {
    /**
     * @param args the main arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * @return name
     */
    public String name() {
        return "Test application";
    }
}
