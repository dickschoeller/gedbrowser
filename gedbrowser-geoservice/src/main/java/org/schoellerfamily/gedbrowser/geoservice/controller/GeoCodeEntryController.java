package org.schoellerfamily.gedbrowser.geoservice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.geocode.dao.GeoCodeDao;
import org.schoellerfamily.gedbrowser.geocode.dao.GeoCodeItem;
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
    private transient GeoCodeDao gcc;

    /**
     * @param name the historical name of the place
     * @return a search result
     */
    @RequestMapping("/geocode")
    public final GeoCodeItem find(
            @RequestParam(value = "name", required = true) final String name) {
        logger.debug("Find location: " + name);
        String findName;
        try {
            findName = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            findName = name;
        }
        return gcc.find(findName);
    }
}
