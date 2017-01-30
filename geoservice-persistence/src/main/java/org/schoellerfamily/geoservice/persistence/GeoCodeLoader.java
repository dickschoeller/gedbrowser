package org.schoellerfamily.geoservice.persistence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides the means to pre-load the cache with some expected places. This is
 * the primary means by which we provide different modern place names for
 * places in the cache.
 *
 * @author Dick Schoeller
 */
public class GeoCodeLoader {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private GeoCode gcc;

    /**
     * Read places from a data file. The file is | separated. It may contain
     * just a historical place name or both historical and modern places names.
     *
     * @param filename the name of the file to load
     */
    public final void load(final String filename) {
        logger.debug("Loading the cache from places file: " + filename);
        try (
            InputStream fis = new FileInputStream(filename);
        ) {
            load(fis);
        } catch (IOException e) {
            logger.error("Problem reading places file", e);
        }
    }

    /**
     * Read places from an input stream. The format is | separated. It may
     * contain just a historical place name or both historical and modern
     * places names.
     *
     * @param istream the input stream
     */
    public final void load(final InputStream istream) {
        load(istream, (name, modernName) -> {
            final GeoCodeItem gci = gcc.get(name);
            if (gci == null || !gci.getModernPlaceName().equals(modernName)) {
                final GeoCodeItem item = new GeoCodeItem(name, modernName);
                gcc.add(item);
                return item;
            }
            return gci;
        });
    }

    /**
     * Read places from a data file. The file is | separated. It may contain
     * just a historical place name or both historical and modern places names.
     *
     * @param filename the name of the file to load
     */
    public final void loadAndFind(final String filename) {
        logger.debug("Loading the cache from places file: " + filename);
        try (
            InputStream fis = new FileInputStream(filename);
        ) {
            loadAndFind(fis);
        } catch (IOException e) {
            logger.error("Problem reading places file", e);
        }
    }

    /**
     * Read places from an input stream. The format is | separated. It may
     * contain just a historical place name or both historical and modern
     * places names.
     *
     * @param istream the input stream
     */
    public final void loadAndFind(final InputStream istream) {
        load(istream, (s1, s2) -> gcc.find(s1, s2));
    }

    /**
     * @author Dick Schoeller
     */
    private interface Loader {
        /**
         * Do whatever the load load operation requires.
         *
         * @param placeName the place name
         * @param modernPlaceName the modern place name for geocoding
         * @return a geocodeitem
         */
        GeoCodeItem load(String placeName, String modernPlaceName);
    }

    /**
     * Read places from an input stream. The format is | separated. It may
     * contain just a historical place name or both historical and modern
     * places names.
     *
     * @param istream the input stream
     * @param loader what to do with each line
     */
    private void load(final InputStream istream, final Loader loader) {
        logger.debug("Loading the cache from input stream");
        String line;
        try (
            InputStreamReader isr =
                    new InputStreamReader(istream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ) {
            while ((line = br.readLine()) != null) {
                final String[] splitLine = line.split("[|]", 4);
                if (splitLine.length >= 2 && !splitLine[1].isEmpty()) {
                    loader.load(splitLine[0], splitLine[1]);
                } else if (splitLine[0] != null && !splitLine[0].isEmpty()) {
                    loader.load(splitLine[0], splitLine[0]);
                }
            }
        } catch (IOException e) {
            logger.error("Problem reading places stream", e);
        }
    }
}
