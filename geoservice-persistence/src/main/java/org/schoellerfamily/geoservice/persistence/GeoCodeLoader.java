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
        logger.debug("Loading the cache from input stream");
        String line;
        try (
            InputStreamReader isr =
                    new InputStreamReader(istream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ) {
            while ((line = br.readLine()) != null) {
                final String[] splitLine = line.split("[|]", 4);
                if (splitLine.length > 2) {
                    gcc.find(splitLine[0], splitLine[1]);
                } else {
                    gcc.find(splitLine[0]);
                }
            }
        } catch (IOException e) {
            logger.error("Problem reading places stream", e);
        }
    }

}
