package org.schoellerfamily.geoservice.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.builder.GeocodeResultBuilder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

import lombok.extern.slf4j.Slf4j;



/**
 * Handles requests for geo code entry.
 *
 * @author Richard Schoeller
 */
@Controller
@Slf4j
public class GeoCodeEntryController {
    /**
     * The geocode cache that underlies this service.
     */
    private final GeoCode gcc;

    /**
     * Create controller.
     *
     * @param gcc geocode service
     */
    public GeoCodeEntryController(final GeoCode gcc) {
        this.gcc = gcc;
    }

    /**
     * Finds a value.
     *
     * @param name the name to use
     * @param modernName the modern name to use
     * @return the resulting geo service item
     */
    @Get("/geocode")
    public final GeoServiceItem find(
            @QueryValue("name")
                final String name,
            @QueryValue(value = "modernName", defaultValue = "")
                final String modernName) {
        if (StringUtils.isEmpty(modernName)) {
            log.debug("Find location: \"{}\"", name);
        } else {
            log.debug("Find location: \"{}\", \"{}\"", name, modernName);
        }
        final String findName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        if (StringUtils.isEmpty(modernName)) {
            final GeoCodeItem find = gcc.find(findName);
            final GeocodeResultBuilder builder = new GeocodeResultBuilder();
            return builder.toGeoServiceItem(find);
        }
        final String findModernName = URLDecoder.decode(modernName, StandardCharsets.UTF_8);
        final GeoCodeItem find = gcc.find(findName, findModernName);
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        return builder.toGeoServiceItem(find);

    }
}
