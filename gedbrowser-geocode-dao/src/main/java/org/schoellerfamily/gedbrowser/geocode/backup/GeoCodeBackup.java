package org.schoellerfamily.gedbrowser.geocode.backup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.geocode.dao.GeoCodeDao;
import org.schoellerfamily.gedbrowser.geocode.dao.GeoCodeItem;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
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
        // Empty constructor.
    }

    /**
     * @throws JsonGenerationException if can't generate JSON
     * @throws JsonMappingException if can't figure out what stuff is for JSON
     * @throws IOException if file IO problems
     */
    public void backup()
            throws JsonGenerationException, JsonMappingException, IOException {
        final List<GeoCodeItem> list = new ArrayList<>(gcd.size());
        for (final String key : gcd.allKeys()) {
            final GeoCodeItem gci = gcd.find(key);
            list.add(gci);
        }
        mapper.writeValue(new File("file.json"), list);
    }

    /**
     * @throws JsonParseException if can't parse JSON
     * @throws JsonMappingException if can't figure out what stuff is for JSON
     * @throws IOException if file IO problems
     */
    public void recover()
            throws JsonParseException, JsonMappingException, IOException {
        final List<GeoCodeItem> list = mapper.readValue(new File("file.json"),
                new TypeReference<List<GeoCodeItem>>() {
                });
        for (final GeoCodeItem item : list) {
            gcd.add(item);
        }
    }
}
