package org.schoellerfamily.gedbrowser.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.controller.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
        // We turn off checkstyle because bean methods must not be final
        // CHECKSTYLE:OFF
        @Bean
        public ApplicationInfo appInfo() {
            // CHECKSTYLE:ON
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
                "gedbrowser", appInfo.getName());
    }

    /** */
    @Test
    public void testApplicationInfoVersion() {
        assertEquals("Version mismatch",
                "1.1.0-SNAPSHOT", appInfo.getVersion());
    }
}