package org.schoellerfamily.gedbrowser.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents charset scanner.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class CharsetScanner {
    /** */
    private static final String ANSEL = "ANSEL";
    /** */
    private static final String ASCII = "ASCII";
    /** */
    private static final String CP1252 = "Cp1252";
    /** */
    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    /** */
    private static final String UTF_16 = StandardCharsets.UTF_16.name();
    /** */
    private static final Map<String, String> CHARSET_MAP = Map.of(
        "ansel", ANSEL,
        "ansi", CP1252,
        "cp1252", CP1252,
        "unicode", UTF_16,
        "utf-8", UTF_8,
        "utf8", UTF_8,
        "ascii", ASCII);

    /**
     * Executes charset.
     *
     * @param filename the filename to use
     * @return the resulting string
     */
    public String charset(final String filename) {
        try (InputStream fis = new StreamManager(filename).getInputStream()) {
                if (fis == null) {
                log.warn("Could not open stream for: {}", filename);
                return UTF_8;
            }
            try (Reader reader = new InputStreamReader(fis, StandardCharsets.US_ASCII);
                    BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (isCharset(line)) {
                        return extractCharsetFromLine(line);
                    }
                }
            }
        } catch (IOException e) {
            log.warn("Could not read file: {}", filename);
        }
        return UTF_8;
    }

    private boolean isCharset(final String line) {
        return line.startsWith("1 CHAR");
    }

    private String extractCharsetFromLine(final String line) {
        final int space = line.lastIndexOf(' ') + 1;
        return gedcomCharsetToJava(line.substring(space));
    }

    /**
     * Executes charset.
     *
     * @param root the root
     * @return the resulting string
     */
    public String charset(final Root root) {
        final GedObject gob = root.getAttributes().get(0);
        if ("Header".equals(gob.getString())) {
            return gedcomCharsetToJava(findCharsetInHeader(gob));
        }
        return UTF_8;
    }

    private String findCharsetInHeader(final GedObject gob) {
        for (final GedObject hgob : gob.getAttributes()) {
            if ("Character Set".equals(hgob.getString())) {
                return ((Attribute) hgob).getTail();
            }
        }
        return UTF_8;
    }

    /**
     * Executes gedcom charset to java.
     *
     * @param charset the charset to process
     * @return the resulting string
     */
    public String gedcomCharsetToJava(final String charset) {
        final String javaCharset = CHARSET_MAP
                .get(charset.toLowerCase(Locale.ENGLISH));
        if (javaCharset == null) {
            return UTF_8;
        }
        return javaCharset;
    }
}
