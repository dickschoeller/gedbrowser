package org.schoellerfamily.geoservice.backup;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.builder.GeocodeResultBuilder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;



/**
 * Provides behavior related to geo code backup.
 *
 * @author Richard Schoeller
 */
@Component
public class GeoCodeBackup {
    /** */
    private final GeoCode gcd;

    private final ObjectMapper mapper = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .changeDefaultVisibility(
                vc -> vc.withVisibility(PropertyAccessor.FIELD, Visibility.ANY))
            .build();

    /**
     * Creates a new GeoCodeBackup.
     *
     * @param gcd the gcd
     */
    public GeoCodeBackup(final GeoCode gcd) {
        this.gcd = gcd;
    }

    /**
     * Executes backup.
     *
     * @param resultFile the result file to use
     */
    public void backup(final File resultFile) throws IOException {
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        final List<GeoServiceItem> list = gcd.allKeys().stream()
            .map(gcd::find)
            .map(builder::toGeoServiceItem)
            .toList();
        mapper.writeValue(resultFile, list);
    }

    /**
     * Executes recover.
     *
     * @param src the src
     */
    public void recover(final File src) throws IOException {
        final List<GeoServiceItem> list = mapper.readValue(src,
                new TypeReference<List<GeoServiceItem>>() {
                });
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        for (final GeoServiceItem item : list) {
            gcd.add(builder.toGeoCodeItem(item));
        }
    }
}
