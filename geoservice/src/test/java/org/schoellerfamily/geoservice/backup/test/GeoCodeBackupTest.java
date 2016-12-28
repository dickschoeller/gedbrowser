package org.schoellerfamily.geoservice.backup.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.keys.KeyManager;
import org.schoellerfamily.geoservice.persistence.GeoCodeDao;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.stub.GeoCodeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private transient GeoCodeDao gcd;

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
        /** */
        @Value("${geoservice.keyfile:/var/lib/gedbrowser/google-geocoding-key}")
        private transient String keyfile;

        /**
         * @return the persistence manager
         */
        // We turn off checkstyle because bean methods must not be final
        // CHECKSTYLE:OFF
        @Bean
        public GeoCodeDao persistenceManager() {
            // CHECKSTYLE:ON
            return new GeoCodeCache();
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
            final KeyManager km = new KeyManager();
            final String key = km.readKeyFile(keyfile);
            return new GeoCoder(key);
        }
    }

    /**
     * @throws IOException if backup file can't be written or read
     */
    @Test
    public void testBackupRestoreBasic() throws IOException {
        gcd.clear();
        gcd.find("3341 Chaucer Lane, Bethlehem, PA");
        backupManager.backup(new File("/var/lib/gedbrowser/test.json"));
        gcd.clear();
        backupManager.recover(new File("/var/lib/gedbrowser/test.json"));
        Assert.assertTrue("Should contain expected entry",
                gcd.allKeys().contains("3341 Chaucer Lane, Bethlehem, PA"));
    }


    /**
     * @throws IOException if backup file can't be written or read
     */
    @Test
    public void testBackupRestore() throws IOException {
        gcd.clear();
        final GeoCodeItem gci = gcd.find("3341 Chaucer Lane, Bethlehem, PA");
        backupManager.backup(new File("/var/lib/gedbrowser/test.json"));
        gcd.clear();
        backupManager.recover(new File("/var/lib/gedbrowser/test.json"));
        Assert.assertEquals(gci, gcd.get("3341 Chaucer Lane, Bethlehem, PA"));
    }
}
