package org.schoellerfamily.geoservice.backup.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.geocoder.StubGeoCoder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeStub;
import org.schoellerfamily.geoservice.persistence.fixture.GeoCodeTestFixture;

/**
 * Contains tests for geo code backup.
 *
 * @author Richard Schoeller
 */
final class GeoCodeBackupTest {
    /** */
    private transient GeoCode gcd;

    /** */
    private transient GeoCodeBackup backupManager;

    @BeforeEach
    void setUp() {
        final GeoCoder geoCoder = new StubGeoCoder(new GeoCodeTestFixture().expectedNotFound());
        gcd = new GeoCodeStub(geoCoder);
        backupManager = new GeoCodeBackup(gcd);
    }

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
