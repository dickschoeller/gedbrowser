package org.schoellerfamily.geoservice.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "load")
public class LoadEndpoint extends BaseGeoCodeEndpoint {
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
    public final String getId() {
        return "load";
    }

    @ReadOperation
    public java.util.List<String> invokeEndpoint() {
        return super.invoke();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void geoCodeAction() {
        logger.info("Invoke load from: " + loadFile);
        loader.load(loadFile);
    }
}
