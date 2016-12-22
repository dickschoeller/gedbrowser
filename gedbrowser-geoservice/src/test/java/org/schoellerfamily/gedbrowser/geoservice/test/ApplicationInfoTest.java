package org.schoellerfamily.gedbrowser.geoservice.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.geoservice.controller.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import org.junit.Assert;

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
        Assert.assertEquals("Maintainer URL mismatch",
                "https://github.com/dickschoeller/gedbrowser",
                appInfo.getApplicationURL());
    }

    /** */
    @Test
    public void testApplicationInfoMaintainerEmail() {
        Assert.assertEquals("Maintainer email mismatch",
                "schoeller@comcast.net", appInfo.getMaintainerEmail());
    }

    /** */
    @Test
    public void testApplicationInfoMaintainerName() {
        Assert.assertEquals("Maintainer name mismatch",
                "Richard Schoeller", appInfo.getMaintainerName());
    }

    /** */
    @Test
    public void testApplicationInfoName() {
        Assert.assertEquals("Application name mismatch",
                "gedbrowser-geoservice", appInfo.getName());
    }

    /** */
    @Test
    public void testApplicationInfoVersion() {
        Assert.assertEquals("Version mismatch",
                "1.1.0-SNAPSHOT", appInfo.getVersion());
        appInfo.getVersion();
    }
}
