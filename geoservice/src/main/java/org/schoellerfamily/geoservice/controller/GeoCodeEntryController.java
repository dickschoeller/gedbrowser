package org.schoellerfamily.geoservice.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.builder.GeocodeResultBuilder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.exceptions.HttpStatusException;

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

    private boolean hasBlankName(final GeoServiceItem item) {
        return item == null || StringUtils.isBlank(item.getPlaceName());
    }

    private GeoServiceItem normalize(final GeoServiceItem item) {
        if (StringUtils.isBlank(item.getModernPlaceName())) {
            return new GeoServiceItem(item.getPlaceName(), item.getPlaceName(), item.getResult());
        }
        return item;
    }

    private String decode(final String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
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
        if (StringUtils.isBlank(name)) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Invalid encoded value");
        }
        final String findName;
        try {
            findName = decode(name);
        } catch (IllegalArgumentException ex) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Invalid encoded value");
        }
        if (StringUtils.isBlank(findName)) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Invalid encoded value");
        }
        final String decodedModernName;
        try {
            decodedModernName = StringUtils.isBlank(modernName) ? "" : decode(modernName);
        } catch (IllegalArgumentException ex) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Invalid encoded value");
        }
        if (StringUtils.isBlank(decodedModernName)) {
            log.debug("Find location: \"{}\"", findName);
            final GeoCodeItem find = gcc.find(findName);
            final GeocodeResultBuilder builder = new GeocodeResultBuilder();
            return builder.toGeoServiceItem(find);
        }
        log.debug("Find location: \"{}\", \"{}\"", findName, decodedModernName);
        final GeoCodeItem find = gcc.find(findName, decodedModernName);
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        return builder.toGeoServiceItem(find);
    }

    /**
     * Creates a new value.
     *
     * @param item the item to create
     * @return the response containing the created item
     */
    @Post("/geocode")
    public HttpResponse<GeoServiceItem> create(@Body final GeoServiceItem item) {
        if (hasBlankName(item)) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        final GeoServiceItem normalized = normalize(item);
        if (gcc.get(normalized.getPlaceName()) != null) {
            return HttpResponse.status(HttpStatus.CONFLICT);
        }
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        final GeoCodeItem saved;
        try {
            saved = gcc.add(builder.toGeoCodeItem(normalized));
        } catch (IllegalArgumentException ex) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        return HttpResponse.created(builder.toGeoServiceItem(saved));
    }

    /**
     * Updates an existing value.
     *
     * @param item the item to update
     * @return the response containing the updated item
     */
    @Put("/geocode")
    public HttpResponse<GeoServiceItem> update(@Body final GeoServiceItem item) {
        if (hasBlankName(item)) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        final GeoServiceItem normalized = normalize(item);
        if (gcc.get(normalized.getPlaceName()) == null) {
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        final GeoCodeItem saved;
        try {
            saved = gcc.add(builder.toGeoCodeItem(normalized));
        } catch (IllegalArgumentException ex) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        return HttpResponse.ok(builder.toGeoServiceItem(saved));
    }

    /**
     * Deletes an existing value.
     *
     * @param name the place name to delete
     * @return the response containing the deleted item
     */
    @Delete("/geocode")
    public HttpResponse<GeoServiceItem> delete(@QueryValue("name") final String name) {
        if (StringUtils.isBlank(name)) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        try {
            final String deleteName = decode(name);
            if (StringUtils.isBlank(deleteName)) {
                return HttpResponse.status(HttpStatus.BAD_REQUEST);
            }
            final GeoCodeItem existing = gcc.get(deleteName);
            if (existing == null) {
                return HttpResponse.status(HttpStatus.NOT_FOUND);
            }
            final GeoCodeItem deleted = gcc.delete(existing);
            final GeocodeResultBuilder builder = new GeocodeResultBuilder();
            return HttpResponse.ok(builder.toGeoServiceItem(deleted));
        } catch (IllegalArgumentException ex) {
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
    }
}
