package org.schoellerfamily.geoservice.keys;

/**
 * @author Dick Schoeller
 */
public class KeyManagerStub implements KeyManager {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getGeocodingKey() {
        return "XYZZY";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMapsKey() {
        return "PLUGH";
    }

}
