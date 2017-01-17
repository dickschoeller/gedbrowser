package org.schoellerfamily.geoservice.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dick Schoeller
 */
@Component
public class GeoServiceClientImpl implements GeoServiceClient {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoServiceItem get(final String placeName) {
        logger.info("Get: " + placeName);
        final GeoServiceItem item = restTemplate.getForObject(
                "http://localhost:8088/geocode?name=" + placeName,
                GeoServiceItem.class);
        return item;
    }
}
