package org.schoellerfamily.geoservice.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.builder.GeocodeResultBuilder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Handles requests for geo code entry.
 *
 * @author Richard Schoeller
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class GeoCodeEntryController {
    /**
     * The geocode cache that underlies this service.
     */
    private final GeoCode gcc;

    /**
     * Finds a value.
     *
     * @param name the name to use
     * @param modernName the modern name to use
     * @return the resulting geo service item
     */
    @GetMapping("/geocode")
    public final GeoServiceItem find(
            @RequestParam(value = "name", required = true)
                final String name,
            @RequestParam(value = "modernName", required = false)
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
