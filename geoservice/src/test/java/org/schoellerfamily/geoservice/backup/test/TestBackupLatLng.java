package org.schoellerfamily.geoservice.backup.test;

import org.junit.Test;
import org.schoellerfamily.geoservice.backup.model.BackupLatLng;
import org.junit.Assert;

/**
 * @author Dick Schoeller
 */
public final class TestBackupLatLng {
    /** */
    @Test
    public void testToString() {
        final BackupLatLng bll = new BackupLatLng(1.01, 2.02);
        Assert.assertEquals("1.01000000,2.02000000", bll.toString());
    }

    /** */
    @Test
    public void testToUrlValue() {
        final BackupLatLng bll = new BackupLatLng(120.01, 170.02);
        Assert.assertEquals("120.01000000,170.02000000", bll.toUrlValue());
    }
}
