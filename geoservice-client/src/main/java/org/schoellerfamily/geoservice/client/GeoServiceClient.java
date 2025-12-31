package org.schoellerfamily.geoservice.client;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implements making read requests to geoservice.
 *
 * @author Dick Schoeller
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class GeoServiceClient {

    /** */
    private final RestClient restClient;

    /** */
    @Value("${geoservice.host:localhost}")
    private final String host;

    /** */
    @Value("${geoservice.port:8080}")
    private final int port;

    /** */
    @Value("${geoservice.protocol:http}")
    private final String protocol;

    /**
     * Get an item that associates a place name with a canonical place name and coordinates.
     *
     * @param placeName the place name
     * @return the item
     */
    public GeoServiceItem get(final String placeName) {
        log.debug("Get: {}", placeName);
        final String url = protocol + "://" + host + ":" + port
                + "/geocode?name=" + URLEncoder.encode(placeName, StandardCharsets.UTF_8);
        try {
            return restClient.get()
                .uri(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GeoServiceItem.class)
                .getBody();
        } catch (RestClientException rce) {
            if (log.isDebugEnabled()) {
                log.debug("Unable to get geocode from geoservice at {}", url, rce);
            } else {
                log.error("Unable to get geocode from geoservice at {}", url);
                log.error("host: {}", host);
                log.error("port: {}", port);
                log.error("protocol: {}", protocol);
            }
            return new GeoServiceItem(placeName, placeName, null);
        }
    }
}
