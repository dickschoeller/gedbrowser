package org.schoellerfamily.geoservice.backup;

import java.io.File;
import java.util.List;

import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.builder.GeocodeResultBuilder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;

import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException;
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
@RequiredArgsConstructor
public class GeoCodeBackup {
    /** */
    private final GeoCode gcd;
    /**
     * The object mapper used for JSON serialization and deserialization.
     */
    private final ObjectMapper mapper = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .changeDefaultVisibility(
                vc -> vc.withVisibility(PropertyAccessor.FIELD, Visibility.ANY))
            .build();
    /**
     * Executes backup.
     *
     * @param resultFile the result file to use
     * @throws JacksonException if a JSON processing error occurs
     */
    public void backup(final File resultFile) throws JacksonException {
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
     * @throws JacksonException if a JSON processing error occurs
     */
    public void recover(final File src) throws JacksonException {
        final List<GeoServiceItem> list = mapper.readValue(src,
                new TypeReference<List<GeoServiceItem>>() {
                });
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        for (final GeoServiceItem item : list) {
            gcd.add(builder.toGeoCodeItem(item));
        }
    }
}
