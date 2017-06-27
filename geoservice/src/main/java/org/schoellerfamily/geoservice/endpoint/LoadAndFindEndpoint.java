package org.schoellerfamily.geoservice.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class LoadAndFindEndpoint extends BaseGeoCodeEndpoint {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
    public final String getId() {
        return "loadAndFind";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void geoCodeAction() {
        logger.info("Invoke load and find from: " + loadFile);
        loader.loadAndFind(loadFile);
    }
}
