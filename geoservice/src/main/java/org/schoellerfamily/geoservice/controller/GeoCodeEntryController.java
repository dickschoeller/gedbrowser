package org.schoellerfamily.geoservice.controller;

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
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

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

    /**
     * Finds a value.
     *
     * @param name the name to use
     * @param modernName the modern name to use
     * @return the resulting geo service item
     */
    @Get("/geocode")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public final GeoServiceItem find(
            @QueryValue("name")
                final String name,
            @QueryValue(value = "modernName", defaultValue = "")
                final String modernName) {
        if (StringUtils.isBlank(name)) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Invalid query value");
        }
        if (StringUtils.isBlank(modernName)) {
            log.debug("Find location: \"{}\"", name);
            final GeoCodeItem find = gcc.find(name);
            final GeocodeResultBuilder builder = new GeocodeResultBuilder();
            return builder.toGeoServiceItem(find);
        }
        log.debug("Find location: \"{}\", \"{}\"", name, modernName);
        final GeoCodeItem find = gcc.find(name, modernName);
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
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<GeoServiceItem> create(@Body final GeoServiceItem item) {
        log.info("GeoService create attempt: placeName={} modernPlaceName={}",
            item == null ? null : item.getPlaceName(),
            item == null ? null : item.getModernPlaceName());
        if (hasBlankName(item)) {
            log.warn("GeoService create rejected: missing placeName");
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        final GeoServiceItem normalized = normalize(item);
        if (gcc.get(normalized.getPlaceName()) != null) {
            log.info("GeoService create conflict: placeName={}", normalized.getPlaceName());
            return HttpResponse.status(HttpStatus.CONFLICT);
        }
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        final GeoCodeItem saved;
        try {
            saved = gcc.add(builder.toGeoCodeItem(normalized));
        } catch (IllegalArgumentException ex) {
            log.warn("GeoService create rejected: invalid payload placeName={} modernPlaceName={}",
                normalized.getPlaceName(), normalized.getModernPlaceName(), ex);
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        log.info("GeoService create success: placeName={} modernPlaceName={}",
            saved.getPlaceName(), saved.getModernPlaceName());
        return HttpResponse.created(builder.toGeoServiceItem(saved));
    }

    /**
     * Updates an existing value.
     *
     * @param item the item to update
     * @return the response containing the updated item
     */
    @Put("/geocode")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<GeoServiceItem> update(@Body final GeoServiceItem item) {
        log.info("GeoService update attempt: placeName={} modernPlaceName={}",
            item == null ? null : item.getPlaceName(),
            item == null ? null : item.getModernPlaceName());
        if (hasBlankName(item)) {
            log.warn("GeoService update rejected: missing placeName");
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        final GeoServiceItem normalized = normalize(item);
        if (gcc.get(normalized.getPlaceName()) == null) {
            log.info("GeoService update not found: placeName={}", normalized.getPlaceName());
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        final GeoCodeItem saved;
        try {
            saved = gcc.update(builder.toGeoCodeItem(normalized));
        } catch (IllegalArgumentException ex) {
            log.warn("GeoService update rejected: invalid payload placeName={} modernPlaceName={}",
                normalized.getPlaceName(), normalized.getModernPlaceName(), ex);
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        log.info("GeoService update success: placeName={} modernPlaceName={}",
            saved.getPlaceName(), saved.getModernPlaceName());
        return HttpResponse.ok(builder.toGeoServiceItem(saved));
    }

    /**
     * Deletes an existing value.
     *
     * @param name the place name to delete
     * @return the response containing the deleted item
     */
    @Delete("/geocode")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<GeoServiceItem> delete(@QueryValue("name") final String name) {
        log.info("GeoService delete attempt: placeName={}", name);
        if (StringUtils.isBlank(name)) {
            log.warn("GeoService delete rejected: missing placeName");
            return HttpResponse.status(HttpStatus.BAD_REQUEST);
        }
        final GeoCodeItem existing = gcc.get(name);
        if (existing == null) {
            log.info("GeoService delete not found: placeName={}", name);
            return HttpResponse.status(HttpStatus.NOT_FOUND);
        }
        final GeoCodeItem deleted = gcc.delete(existing);
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        log.info("GeoService delete success: placeName={}", name);
        return HttpResponse.ok(builder.toGeoServiceItem(deleted));
    }
}
