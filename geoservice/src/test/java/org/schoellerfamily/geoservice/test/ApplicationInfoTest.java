package org.schoellerfamily.geoservice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.geoservice.controller.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public final class ApplicationInfoTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /**
     * Setup the configurations for this test class.
     *
     * @author Dick Schoeller
     */
    @Configuration
    static class ContextConfiguration {

        /**
         * @return the backup manager
         */
        @Bean
        public ApplicationInfo appInfo() {
            return new ApplicationInfo();
        }
    }

    /** */
    @Test
    public void testApplicationInfoURL() {
        assertEquals("Application URL mismatch",
                "https://github.com/dickschoeller/gedbrowser",
                appInfo.getApplicationURL());
    }

    /** */
    @Test
    public void testApplicationInfoMaintainerEmail() {
        assertEquals("Maintainer email mismatch",
                "schoeller@comcast.net", appInfo.getMaintainerEmail());
    }

    /** */
    @Test
    public void testApplicationInfoMaintainerName() {
        assertEquals("Maintainer name mismatch",
                "Richard Schoeller", appInfo.getMaintainerName());
    }

    /** */
    @Test
    public void testApplicationInfoName() {
        assertEquals("Application name mismatch",
                "gedbrowser-geoservice", appInfo.getName());
    }

    /** */
    @Test
    public void testApplicationInfoVersion() {
        assertEquals("Version mismatch",
                "1.3.0-RC3-SNAPSHOT", appInfo.getVersion());
    }
}