package org.schoellerfamily.geoservice.endpoint;

import java.util.List;

import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import lombok.extern.slf4j.Slf4j;



/**
 * Exposes operations for the load endpoint.
 *
 * @author Richard Schoeller
 */
@Controller("/actuator")
@Slf4j
public class LoadEndpoint extends BaseGeoCodeEndpoint {
    /** */
    private final GeoCodeLoader loader;

    /** */
    private final String loadFile;

    /**
     * Creates a new LoadEndpoint.
     *
     * @param gcc the gcc
     * @param loader the loader
     * @param loadFile the load file
     */
    public LoadEndpoint(final GeoCode gcc, final GeoCodeLoader loader,
        @Value("${geoservice.loadfile:/var/lib/gedbrowser/geoservice-loadfile.txt}")
        final String loadFile) {
        super(gcc);
        this.loader = loader;
        this.loadFile = loadFile;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public final String getId() {
        return "load";
    }

    /**
     * Returns the list.
     *
     * @return the resulting list
     */
    @Get("/load")
    public List<String> invokeEndpoint() {
        return super.invoke();
    }

    /**
     * Executes geo code action.
     */
    @Override
    public final void geoCodeAction() {
        log.info("Invoke load from: {}", loadFile);
        loader.load(loadFile);
    }
}
