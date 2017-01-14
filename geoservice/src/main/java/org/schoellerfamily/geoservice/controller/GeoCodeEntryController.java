package org.schoellerfamily.geoservice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.schoellerfamily.geoservice.model.builder.GeocodeResultBuilder;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for RESTful web service to request geocoding for a historical
 * place name. The response will contain an equivalent modern place name, which
 * may or may not be the same as the historical place name. If found, the
 * response will also contain a geocoding result from Google's geocoding APIs.
 *
 * @author Dick Schoeller
 */
@RestController
public class GeoCodeEntryController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * The geocode cache that underlies this service.
     */
    @Autowired
    private transient GeoCode gcc;

    /**
     * @param name the historical name of the place
     * @param modernName the modern searchable name of the place
     * @return a search result
     */
    @RequestMapping("/geocode")
    public final GeoServiceItem find(
            @RequestParam(value = "name", required = true)
                final String name,
            @RequestParam(value = "modernName", required = false)
                final String modernName) {
        if (modernName == null || modernName.isEmpty()) {
            logger.debug("Find location: \"" + name + "\"");
        } else {
            logger.debug(
                    "Find location: \"" + name + "\", \"" + modernName + "\"");
        }
        String findName;
        try {
            findName = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            findName = name;
        }
        String findModernName;
        if (modernName == null || modernName.isEmpty()) {
            findModernName = findName;
        } else {
            try {
                findModernName = URLDecoder.decode(modernName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                findModernName = modernName;
            }
        }

        final GeoCodeItem find = gcc.find(findName, findModernName);
        final GeocodeResultBuilder builder = new GeocodeResultBuilder();
        return builder.toGeoServiceItem(find);
    }
}
