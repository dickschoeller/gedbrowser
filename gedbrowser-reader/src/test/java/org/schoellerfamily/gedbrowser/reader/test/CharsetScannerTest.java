package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.CharsetScanner;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class CharsetScannerTest {
    /** */
    @Autowired
    private transient TestDataReader reader;

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private transient String gedbrowserHome;

    /** */
    @Test
    public void testFileUTF8() {
        final CharsetScanner scanner = new CharsetScanner();
        assertEquals("UTF-8", scanner.charset("mini-schoeller.ged"),
                "Charset mismatch");
    }

    /** */
    @Test
    public void testFileANSI() {
        final CharsetScanner scanner = new CharsetScanner();
        assertEquals("Cp1252", scanner.charset(gedbrowserHome + "/gl120368.ged"),
                "Charset mismatch");
    }

    /** */
    @Test
    public void testFileASCII() {
        final CharsetScanner scanner =
                new CharsetScanner();
        assertEquals("ASCII", scanner.charset("simple.ged"),
                "Charset mismatch");
    }

    /** */
    @Test
    public void testFileANSEL() {
        final CharsetScanner scanner =
                new CharsetScanner();
        assertEquals("ANSEL", scanner.charset("TGC551LF.ged"),
                "Charset mismatch");
    }

    /** */
    @Test
    public void testFileANSEL2() {
        final CharsetScanner scanner =
                new CharsetScanner();
        assertEquals("ANSEL", scanner.charset("ansel.ged"),
                "Charset mismatch");
    }

    /**
     * @throws IOException if there is an error reading the file.
     */
    @Test
    public void testRootUTF8() throws IOException {
        final Root root = reader.readFileTestSource("mini-schoeller.ged");
        final CharsetScanner scanner = new CharsetScanner();
        assertEquals("UTF-8", scanner.charset(root),
                "Charset mismatch");
    }

    /**
     * @throws IOException if there is an error reading the file.
     */
    @Test
    public void testRootANSEL2() throws IOException {
        final Root root = reader.readFileTestSource("ansel.ged");
        final CharsetScanner scanner = new CharsetScanner();
        assertEquals("ANSEL", scanner.charset(root),
                "Charset mismatch");
    }
}
