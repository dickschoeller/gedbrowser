package org.schoellerfamily.geoservice.backup.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import lombok.RequiredArgsConstructor;

/**
 * Tests for backup manager for cached geocode lookups.
 *
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
    classes = { GeoCodeBackupTest.ContextConfiguration.class, GeoCodeBackup.class })
final class GeoCodeBackupTest {
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
    @RequiredArgsConstructor
    public static class ContextConfiguration {
        /** */
        private GeoCoder geoCoder;

        /**
         * @return the persistence manager
         */
        @Bean
        public GeoCode persistenceManager() {
            return new GeoCodeStub(geoCoder());
        }

        /**
         * @return the geocoder
         */
        @Bean
        public GeoCoder geoCoder() {
            if (geoCoder == null) {
                geoCoder = new StubGeoCoder(new GeoCodeTestFixture().expectedNotFound());
            }
            return geoCoder;
        }
    }

    /**
     * @throws IOException if backup file can't be written or read
     */
    @Test
    void testBackupRestoreBasic() throws IOException {
        gcd.clear();
        gcd.find("3341 Chaucer Lane, Bethlehem, PA");
        backupManager.backup(new File("test.json"));
        gcd.clear();
        final File test = new File("test.json");
        backupManager.recover(test);
        assertTrue(gcd.allKeys().contains("3341 Chaucer Lane, Bethlehem, PA"),
            "Should contain expected entry");
        if (!test.delete()) {
            throw new IOException("Couldn't delete file test.json");
        }
    }

    /**
     * @throws IOException if backup file can't be written or read
     */
    @Test
    void testBackupRestore() throws IOException {
        gcd.clear();
        final GeoCodeItem gci = gcd.find("3341 Chaucer Lane, Bethlehem, PA");
        backupManager.backup(new File("test.json"));
        gcd.clear();
        final File test = new File("test.json");
        backupManager.recover(test);
        assertEquals(gci, gcd.get("3341 Chaucer Lane, Bethlehem, PA"),
            "Should have a good item");
        if (!test.delete()) {
            throw new IOException("Couldn't delete file test.json");
        }
    }
}
