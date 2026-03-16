package org.schoellerfamily.geoservice.keys;

/**
 * Stub implementation of managing goecoding keys.
 *
 * @author Dick Schoeller
 */
public final class KeyManagerStub implements KeyManager {
    /**
     * Returns the geocoding key.
     *
     * @return the geocoding key
     */
    @Override
    public String getGeocodingKey() {
        return "XYZZY";
    }

    /**
     * Returns the maps key.
     *
     * @return the maps key
     */
    @Override
    public String getMapsKey() {
        return "PLUGH";
    }

}
