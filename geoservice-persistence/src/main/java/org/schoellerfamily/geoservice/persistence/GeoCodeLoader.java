package org.schoellerfamily.geoservice.persistence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Provides behavior related to geo code loader.
 *
 * @author Richard Schoeller
 */
@Slf4j
@RequiredArgsConstructor
public class GeoCodeLoader {

    /** */
    private final GeoCode gcc;

    /**
     * Read places from a data file. The file is | separated. It may contain
     * just a historical place name or both historical and modern places names.
     *
     * @param filename the name of the file to load
     */
    public final void load(final String filename) {
        log.debug("Loading the cache from places file: {}", filename);
        try (
            InputStream fis = new FileInputStream(filename);
        ) {
            load(fis);
        } catch (IOException e) {
            log.error("Problem reading places file", e);
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
        log.debug("Loading the cache from places file: {}", filename);
        try (
            InputStream fis = new FileInputStream(filename);
        ) {
            loadAndFind(fis);
        } catch (IOException e) {
            log.error("Problem reading places file", e);
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
     * @author Richard Schoeller
     */
    private interface Loader {
        GeoCodeItem load(String placeName, String modernPlaceName);
    }

    private void load(final InputStream istream, final Loader loader) {
        log.debug("Loading the cache from input stream");
        String line;
        try (
            InputStreamReader isr =
                    new InputStreamReader(istream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ) {
            while ((line = br.readLine()) != null) {
                final String[] splitLine = line.split("[|]", 4);
                if (splitLine.length >= 2 && StringUtils.isNotEmpty(splitLine[1])) {
                    loader.load(splitLine[0], splitLine[1]);
                } else if (StringUtils.isNotEmpty(splitLine[0])) {
                    loader.load(splitLine[0], splitLine[0]);
                }
            }
        } catch (IOException e) {
            log.error("Problem reading places stream", e);
        }
    }
}
