package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.reader.StreamManager;

/**
 * Test class for StreamManager path validation.
 *
 * @author Dick Schoeller
 */
public class StreamManagerTest {

    /** */
    @Test
    void testNullFilename() throws FileNotFoundException {
        final StreamManager streamManager = new StreamManager(null);
        final InputStream is = streamManager.getInputStream();
        assertNull(is, "Null filename should return null input stream");
    }

    /** */
    @Test
    void testPathTraversalWithDoubleDots() {
        final StreamManager streamManager = new StreamManager("../../../etc/passwd");
        assertThrows(IllegalArgumentException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testPathTraversalInResourcePath() {
        final StreamManager streamManager = new StreamManager("foo/../bar");
        assertThrows(IllegalArgumentException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testValidResourcePathNotFound() throws FileNotFoundException {
        final StreamManager streamManager = new StreamManager("charset");
        assertThrows(FileNotFoundException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testFilePathWithTraversal() {
        final StreamManager streamManager = new StreamManager("./../../etc/passwd");
        assertThrows(IllegalArgumentException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testEmptyFilename() {
        final StreamManager streamManager = new StreamManager("");
        assertThrows(IllegalArgumentException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testAbsolutePathWithTraversal() {
        final StreamManager streamManager = new StreamManager("/../../../etc/passwd");
        assertThrows(IllegalArgumentException.class, streamManager::getInputStream);
    }
}



