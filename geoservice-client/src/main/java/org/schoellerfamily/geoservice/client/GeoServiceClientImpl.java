package org.schoellerfamily.geoservice.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
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

    /** */
    @Value("${geoservice.host:localhost}")
    private transient String host;

    /** */
    @Value("${geoservice.port:8080}")
    private transient int port;

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoServiceItem get(final String placeName) {
        logger.debug("Get: " + placeName);
        final String url = "http://" + host + ":" + port + "/geocode?name="
                + placeName;
        try {
            return restTemplate.getForObject(url, GeoServiceItem.class);
        } catch (RestClientException rce) {
            logger.error(
                    "Unable to get geocode from geoservice at " + url, rce);
            return new GeoServiceItem(placeName, placeName, null);
        }
    }
}
