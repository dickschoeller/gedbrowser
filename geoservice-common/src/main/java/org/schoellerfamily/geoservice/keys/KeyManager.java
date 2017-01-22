package org.schoellerfamily.geoservice.keys;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.schoellerfamily.geoservice.exception.GeoCodeRuntimeException;

/**
 * @author Dick Schoeller
 */
public final class KeyManager {
    /** */
    private final String fileName;

    /**
     * Constructor.
     *
     * @param fileName the name of the file to read.
     */
    public KeyManager(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Read the Google Geocoding key string.
     *
     * @return the key string
     */
    public String getGeocodingKey() {
        try (FileInputStream fileStream = new FileInputStream(fileName);
                InputStreamReader iStreamReader =
                        new InputStreamReader(fileStream, "UTF-8");
                BufferedReader br = new BufferedReader(iStreamReader);) {
            final StringBuilder sb = new StringBuilder();
            final String line = br.readLine();
            if (line != null) {
                sb.append(line.replace("\n", ""));
            }
            return sb.toString();
        } catch (IOException e) {
            throw new GeoCodeRuntimeException(
                    "Couldn't open key file: " + fileName, e);
        }
    }

    /**
     * Read the Google Maps key string.
     *
     * @return the key string
     */
    public String getMapsKey() {
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
            return sb.toString();
        } catch (IOException e) {
            throw new GeoCodeRuntimeException(
                    "Couldn't read key file: " + fileName, e);
        }
    }
}
