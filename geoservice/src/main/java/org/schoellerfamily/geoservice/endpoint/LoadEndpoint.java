package org.schoellerfamily.geoservice.endpoint;

import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.schoellerfamily.geoservice.persistence.GeoCodeLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "load")
@Slf4j
public class LoadEndpoint extends BaseGeoCodeEndpoint {
    /** */
    private final GeoCodeLoader loader;

    /** */
    private final String loadFile;

    public LoadEndpoint(final GeoCode gcc, final GeoCodeLoader loader,
        @Value("${geoservice.loadfile:/var/lib/gedbrowser/geoservice-loadfile.txt}")
        final String loadFile) {
        super(gcc);
        this.loader = loader;
        this.loadFile = loadFile;
    }

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
        log.info("Invoke load from: {}", loadFile);
        loader.load(loadFile);
    }
}
