package org.schoellerfamily.gedbrowser.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * Reads the top of a GEDCOM file, looking for the CHAR tag to determine how
 * to read the file.
 *
 * @author Dick Schoeller
 */
public class CharsetScanner {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Holds the mapping between GEDCOM known charsets and Java known charsets.
     */
    private static final Map<String, String> CHARSET_MAP = new HashMap<>();
    static {
        CHARSET_MAP.put("ansel", "ANSEL");
        CHARSET_MAP.put("ansi", "Cp1252");
        CHARSET_MAP.put("cp1252", "Cp1252");
        CHARSET_MAP.put("unicode", "UTF-16");
        CHARSET_MAP.put("utf-8", "UTF-8");
        CHARSET_MAP.put("utf8", "UTF-8");
        CHARSET_MAP.put("ascii", "ASCII");
    }

    /**
     * @param filename the name of the file to scan
     * @return the Java charset name
     */
    public String charset(final String filename) {
        try (InputStream fis = new StreamManager(filename).getInputStream();
                Reader reader = new InputStreamReader(fis, "ASCII");
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("1 CHAR")) {
                    final int space = line.lastIndexOf(' ') + 1;
                    return gedcomCharsetToJava(line.substring(space));
                }
            }
        } catch (IOException e) {
            logger.warn("Could not read file: " + filename);
        }
        return "UTF-8";
    }

    /**
     * @param root the root of the dataset that we are working with
     * @return the Java charset name
     */
    public String charset(final Root root) {
        final GedObject gob = root.getAttributes().get(0);
        if ("Header".equals(gob.getString())) {
            return gedcomCharsetToJava(findCharsetInHeader(gob));
        }
        return "UTF-8";
    }

    /**
     * Find the GEDCOM charset in the attributes of the header.
     *
     * @param gob the header ged object
     * @return the GEDCOM charset
     */
    private String findCharsetInHeader(final GedObject gob) {
        for (final GedObject hgob : gob.getAttributes()) {
            if ("Character Set".equals(hgob.getString())) {
                return ((Attribute) hgob).getTail();
            }
        }
        return "UTF-8";
    }

    /**
     * @param charset the GEDCOM charset name
     * @return the Java charset name
     */
    public String gedcomCharsetToJava(final String charset) {
        final String javaCharset = CHARSET_MAP
                .get(charset.toLowerCase(Locale.ENGLISH));
        if (javaCharset == null) {
            return "UTF-8";
        }
        return javaCharset;
    }
}
