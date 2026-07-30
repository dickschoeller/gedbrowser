package org.schoellerfamily.geoservice.endpoint;

import java.util.List;

import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import lombok.extern.slf4j.Slf4j;



/**
 * Exposes operations for the load and find endpoint.
 *
 * @author Richard Schoeller
 */
@Controller("/actuator")
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
     * @param loadFile the load file
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
    @Get("/loadAndFind")
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
