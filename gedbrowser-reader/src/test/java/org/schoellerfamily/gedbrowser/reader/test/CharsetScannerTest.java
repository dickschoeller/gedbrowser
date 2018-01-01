package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.CharsetScanner;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class CharsetScannerTest {
    /** */
    @Autowired
    private transient TestDataReader reader;

    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /** */
    @Test
    public void testFileUTF8() {
        final CharsetScanner scanner = new CharsetScanner();
        assertEquals("Charset mismatch",
                "UTF-8", scanner.charset("mini-schoeller.ged"));
    }

    /** */
    @Test
    public void testFileANSI() {
        final CharsetScanner scanner =
                new CharsetScanner();
        assertEquals("Charset mismatch",
                "Cp1252", scanner.charset(gedbrowserHome + "/gl120368.ged"));
    }

    /** */
    @Test
    public void testFileASCII() {
        final CharsetScanner scanner =
                new CharsetScanner();
        assertEquals("Charset mismatch",
                "ASCII", scanner.charset("simple.ged"));
    }

    /** */
    @Test
    public void testFileANSEL() {
        final CharsetScanner scanner =
                new CharsetScanner();
        assertEquals("Charset mismatch",
                "ANSEL", scanner.charset("TGC551LF.ged"));
    }

    /** */
    @Test
    public void testFileANSEL2() {
        final CharsetScanner scanner =
                new CharsetScanner();
        assertEquals("Charset mismatch",
                "ANSEL", scanner.charset("ansel.ged"));
    }

    /**
     * @throws IOException if there is an error reading the file.
     */
    @Test
    public void testRootUTF8() throws IOException {
        final Root root = reader.readFileTestSource("mini-schoeller.ged");
        final CharsetScanner scanner = new CharsetScanner();
        assertEquals("Charset mismatch",
                "UTF-8", scanner.charset(root));
    }

    /**
     * @throws IOException if there is an error reading the file.
     */
    @Test
    public void testRootANSEL2() throws IOException {
        final Root root = reader.readFileTestSource("ansel.ged");
        final CharsetScanner scanner = new CharsetScanner();
        assertEquals("Charset mismatch",
                "ANSEL", scanner.charset(root));
    }
}
