package org.schoellerfamily.geoservice.keys;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.schoellerfamily.geoservice.exception.GeoCodeRuntimeException;

/**
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
     * Constructor.
     *
     * @param fileName the name of the file to read.
     */
    public KeyManagerImpl(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * {@inheritDoc}
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
            throw new GeoCodeRuntimeException(
                    "Couldn't open key file: " + fileName, e);
        }
    }

    /**
     * {@inheritDoc}
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
            br.readLine();
            final String line = br.readLine();
            if (line != null) {
                sb.append(line.replace("\n", ""));
            }
            mapKey = sb.toString();
            return mapKey;
        } catch (IOException e) {
            throw new GeoCodeRuntimeException(
                    "Couldn't read key file: " + fileName, e);
        }
    }
}
