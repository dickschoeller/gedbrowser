package org.schoellerfamily.geoservice.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class ClearEndpoint implements Endpoint<List<String>> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private GeoCode gcc;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "clear";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> invoke() {
        logger.info("Invoke clear");
        final List<String> messages = new ArrayList<>();
        gcc.clear();
        messages.add("Cleared cache");
        messages.add(gcc.size() + " locations in the cache");
        messages.add(
                gcc.size() - gcc.countNotFound()
                + " geocoded locations in cache");
        return messages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSensitive() {
        return true;
    }
}
