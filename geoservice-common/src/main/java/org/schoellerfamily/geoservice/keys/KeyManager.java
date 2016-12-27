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
    /**
     * Constructor.
     */
    public KeyManager() {
        // Empty constructor.
    }

    /**
     * Read the key string.
     *
     * @param fileName the name of the file to read.
     * @return the key string
     */
    public String readKeyFile(final String fileName) {
        try (FileInputStream fileStream = new FileInputStream(fileName);
                InputStreamReader iStreamReader =
                        new InputStreamReader(fileStream, "UTF-8");
                BufferedReader br = new BufferedReader(iStreamReader);) {
            final StringBuilder sb = new StringBuilder();
            final String line = br.readLine();
            if (line != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new GeoCodeRuntimeException("Couldn't open key file", e);
        }
    }

    /**
     * @return path to the standard location for the google key.
     */
    public String getGoogleGeoCodingKeyPath() {
        // TODO change to a value from the resources.
        return "/var/lib/gedbrowser/google-geocoding-key";
    }
}
