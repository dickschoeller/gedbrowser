package org.schoellerfamily.geoservice.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class LoadEndpoint implements Endpoint<List<String>> {

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private GeoCode gcc;

    /** */
    @Autowired
    private GeoCodeLoader loader;

    /** */
    @Value("${geoservice.loadfile:"
            + "/var/lib/gedbrowser/geoservice-loadfile.txt}")
    private transient String loadFile;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "load";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> invoke() {
        logger.info("Invoke load from: " + loadFile);
        final List<String> messages = new ArrayList<>();
        loader.load(loadFile);
        messages.add("Load complete");
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
