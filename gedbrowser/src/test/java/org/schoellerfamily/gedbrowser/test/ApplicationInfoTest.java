package org.schoellerfamily.gedbrowser.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.controller.ApplicationInfoImpl;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
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
            return new ApplicationInfoImpl();
        }
    }

    /** */
    @Test
    public void testApplicationInfoURL() {
        assertEquals("https://github.com/dickschoeller/gedbrowser",
                appInfo.getApplicationURL(), "Application URL mismatch");
    }

    /** */
    @Test
    public void testApplicationInfoMaintainerEmail() {
        assertEquals("schoeller@comcast.net", appInfo.getMaintainerEmail(),
                "Maintainer email mismatch");
    }

    /** */
    @Test
    public void testApplicationInfoMaintainerName() {
        assertEquals("Richard Schoeller", appInfo.getMaintainerName(),
                "Maintainer name mismatch");
    }

    /** */
    @Test
    public void testApplicationInfoName() {
        assertEquals("gedbrowser", appInfo.getApplicationName(),
                "Application name mismatch");
    }

    /** */
    @Test
    public void testApplicationInfoVersion() {
        assertEquals("1.3.0-RC3-SNAPSHOT", appInfo.getVersion(),
                "Version mismatch");
    }

    /** */
    @Test
    public void testApplicationInfoHomeURL() {
        assertEquals("http://www.schoellerfamily.org/", appInfo.getHomeURL(),
                "Home URL mismatch");
    }
}