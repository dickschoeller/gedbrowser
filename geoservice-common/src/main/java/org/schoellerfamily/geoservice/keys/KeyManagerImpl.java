package org.schoellerfamily.geoservice.keys;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.schoellerfamily.geoservice.exception.GeoCodeRuntimeException;

/**
 * Implementation of managing geocoding keys.
 *
 * @author Dick Schoeller
 */
public final class KeyManagerImpl implements KeyManager {
    /** */
    private final String fileName;

    /** */
    private String geocodingKey;

    /** */
    private String mapKey;

    /**
     * Creates a new KeyManagerImpl.
     *
     * @param fileName the file name to use
     */
    public KeyManagerImpl(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the geocoding key.
     *
     * @return the geocoding key
     */
    @Override
    public String getGeocodingKey() {
        if (geocodingKey != null) {
            return geocodingKey;
        }
        try (FileInputStream fileStream = new FileInputStream(fileName);
                InputStreamReader iStreamReader =
                        new InputStreamReader(fileStream, "UTF-8");
                BufferedReader br = new BufferedReader(iStreamReader);) {
            final StringBuilder sb = new StringBuilder();
            final String line = br.readLine();
            if (line != null) {
                sb.append(line.replace("\n", ""));
            }
            geocodingKey = sb.toString();
            return geocodingKey;
        } catch (IOException e) {
            throw new GeoCodeRuntimeException("Couldn't open key file: %s".formatted(fileName), e);
        }
    }

    /**
     * Returns the maps key.
     *
     * @return the maps key
     */
    @Override
    public String getMapsKey() {
        if (mapKey != null) {
            return mapKey;
        }
        try (FileInputStream fileStream = new FileInputStream(fileName);
                InputStreamReader iStreamReader =
                        new InputStreamReader(fileStream, "UTF-8");
                BufferedReader br = new BufferedReader(iStreamReader);) {
            final StringBuilder sb = new StringBuilder();
            // Ignore first line, that's the geocoding key
            final String firstLine = br.readLine();
            if (firstLine != null) {
                final String line = br.readLine();
                if (line != null) {
                    sb.append(line.replace("\n", ""));
                }
            }
            mapKey = sb.toString();
            return mapKey;
        } catch (IOException e) {
            throw new GeoCodeRuntimeException("Couldn't read key file: %s".formatted(fileName), e);
        }
    }
}
