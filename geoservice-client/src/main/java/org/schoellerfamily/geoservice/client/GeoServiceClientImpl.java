package org.schoellerfamily.geoservice.client;

import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Component
@Slf4j
public final class GeoServiceClientImpl implements GeoServiceClient {

    /** */
    @Autowired
    private transient RestTemplate restTemplate;

    /** */
    @Value("${geoservice.host:localhost}")
    private transient String host;

    /** */
    @Value("${geoservice.port:8080}")
    private transient int port;

    /** */
    @Value("${geoservice.protocol:http}")
    private transient String protocol;

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoServiceItem get(final String placeName) {
        log.debug("Get: " + placeName);
        final String url = protocol + "://" + host + ":" + port
                + "/geocode?name=" + placeName;
        try {
            return restTemplate.getForObject(url, GeoServiceItem.class);
        } catch (RestClientException rce) {
            if (log.isDebugEnabled()) {
                log.debug(
                    "Unable to get geocode from geoservice at " + url, rce);
            } else {
                log.error("Unable to get geocode from geoservice at " + url);
                log.error("host: " + host);
                log.error("port: " + port);
                log.error("protocol: " + protocol);
            }
            return new GeoServiceItem(placeName, placeName, null);
        }
    }
}
