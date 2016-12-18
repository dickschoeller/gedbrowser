package org.schoellerfamily.gedbrowser.geoservice.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.geocodecache.GeoCodeCache;

import org.schoellerfamily.gedbrowser.geocodecache.GeoCodeCacheEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
@RequestMapping("/geocode")
public class GeoCodeCacheEntryController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

//    private final AtomicLong counter = new AtomicLong();
    /**
     * The geocode cache that underlies this service.
     */
    @Autowired
    private transient GeoCodeCache gcc;

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * @param name the old name of the place
     * @return a search result
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    final GeoCodeCacheEntry find(
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
