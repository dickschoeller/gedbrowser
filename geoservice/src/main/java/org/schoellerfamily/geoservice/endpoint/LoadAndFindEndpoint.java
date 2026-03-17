package org.schoellerfamily.geoservice.endpoint;

import java.util.List;

import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;



/**
 * Exposes operations for the load and find endpoint.
 *
 * @author Richard Schoeller
 */
@Component
@Endpoint(id = "loadAndFind")
@Slf4j
public class LoadAndFindEndpoint extends BaseGeoCodeEndpoint {
    /** */
    private final GeoCodeLoader loader;
    /** */
    private final String loadFile;

    /**
     * Creates a new LoadAndFindEndpoint.
     *
     * @param gcc the gcc
     * @param loader the loader
     */
    public LoadAndFindEndpoint(final GeoCode gcc, final GeoCodeLoader loader,
        @Value("${geoservice.loadfile:/var/lib/gedbrowser/geoservice-loadfile.txt}")
        final String loadFile) {
        super(gcc);
        this.loader = loader;
        this.loadFile = loadFile;
    }

    /**
     * Returns the id.
     *
     * @return the id
     */
    public final String getId() {
        return "loadAndFind";
    }

    /**
     * Returns the list.
     *
     * @return the resulting list
     */
    @ReadOperation
    public List<String> invokeEndpoint() {
        return super.invoke();
    }

    /**
     * Executes geo code action.
     */
    @Override
    public void geoCodeAction() {
        log.info("Invoke load and find from: {}", loadFile);
        loader.loadAndFind(loadFile);
    }
}
