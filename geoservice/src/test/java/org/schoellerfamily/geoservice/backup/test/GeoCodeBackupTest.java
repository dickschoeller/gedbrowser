package org.schoellerfamily.geoservice.backup.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.geocoder.StubGeoCoder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeStub;
import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeTestFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Tests for backup manager for cached geocode lookups.
 *
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public final class GeoCodeBackupTest {
    /** */
    @Autowired
    private transient GeoCode gcd;

    /** */
    @Autowired
    private transient GeoCodeBackup backupManager;

    /**
     * Setup the configurations for this test class.
     *
     * @author Dick Schoeller
     */
    @Configuration
    static class ContextConfiguration {
        /**
         * @return the persistence manager
         */
        // We turn off checkstyle because bean methods must not be final
        // CHECKSTYLE:OFF
        @Bean
        public GeoCode persistenceManager() {
            // CHECKSTYLE:ON
            return new GeoCodeStub();
        }

        /**
         * @return the backup manager
         */
        // We turn off checkstyle because bean methods must not be final
        // CHECKSTYLE:OFF
        @Bean
        public GeoCodeBackup backupManager() {
            // CHECKSTYLE:ON
            return new GeoCodeBackup();
        }

        /**
         * @return the geocoder
         */
        // We turn off checkstyle because bean methods must not be final
        // CHECKSTYLE:OFF
        @Bean
        public GeoCoder geoCoder() {
            final GeoCodeTestFixture fixture = new GeoCodeTestFixture();
            return new StubGeoCoder(fixture.expectedNotFound());
        }
    }

    /**
     * @throws IOException if backup file can't be written or read
     */
    @Test
    public void testBackupRestoreBasic() throws IOException {
        gcd.clear();
        gcd.find("3341 Chaucer Lane, Bethlehem, PA");
        backupManager.backup(new File("test.json"));
        gcd.clear();
        final File test = new File("test.json");
        backupManager.recover(test);
        assertTrue("Should contain expected entry",
                gcd.allKeys().contains("3341 Chaucer Lane, Bethlehem, PA"));
        if (!test.delete()) {
            throw new IOException("Couldn't delete file test.json");
        }
    }

    /**
     * @throws IOException if backup file can't be written or read
     */
    @Test
    public void testBackupRestore() throws IOException {
        gcd.clear();
        final GeoCodeItem gci = gcd.find("3341 Chaucer Lane, Bethlehem, PA");
        backupManager.backup(new File("test.json"));
        gcd.clear();
        final File test = new File("test.json");
        backupManager.recover(test);
        assertEquals("Should have a good item",
                gci, gcd.get("3341 Chaucer Lane, Bethlehem, PA"));
        if (!test.delete()) {
            throw new IOException("Couldn't delete file test.json");
        }
    }
}
