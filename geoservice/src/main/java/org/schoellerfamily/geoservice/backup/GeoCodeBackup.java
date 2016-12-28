package org.schoellerfamily.geoservice.backup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.geoservice.backup.model.BackupGeoCodeItem;
import org.schoellerfamily.geoservice.persistence.GeoCodeDao;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements backup and recover of the contents of the geocode data set.
 *
 * @author Dick Schoeller
 */
public final class GeoCodeBackup {
    /** */
    @Autowired
    private transient GeoCodeDao gcd;

    /** */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor.
     */
    public GeoCodeBackup() {
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

    /**
     * @param resultFile the file to write to
     * @throws JsonGenerationException if can't generate JSON
     * @throws JsonMappingException if can't figure out what stuff is for JSON
     * @throws IOException if file IO problems
     */
    public void backup(final File resultFile)
            throws JsonGenerationException, JsonMappingException, IOException {
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        final List<BackupGeoCodeItem> list = new ArrayList<>();
        for (final String key : gcd.allKeys()) {
            final GeoCodeItem gci = gcd.find(key);
            list.add(builder.toBackupGeoCodeItem(gci));
        }
        mapper.writeValue(resultFile, list);
    }

    /**
     * @param src the file to read from
     * @throws JsonParseException if can't parse JSON
     * @throws JsonMappingException if can't figure out what stuff is for JSON
     * @throws IOException if file IO problems
     */
    public void recover(final File src)
            throws JsonParseException, JsonMappingException, IOException {
        final List<BackupGeoCodeItem> list = mapper.readValue(src,
                new TypeReference<List<BackupGeoCodeItem>>() {
                });
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        for (final BackupGeoCodeItem item : list) {
            gcd.add(builder.toGeoCodeItem(item));
        }
    }
}
