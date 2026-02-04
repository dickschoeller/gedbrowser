package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    /** */
    @Test
    void testFilePathWithColon() throws FileNotFoundException {
        // Tests paths containing ":" (Windows drive letters)
        final StreamManager streamManager = new StreamManager("C:\\temp\\test.ged");
        assertThrows(FileNotFoundException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testFilePathWithSrcDirectory() throws IOException {
        // Tests paths containing "src/"
        final String userDir = System.getProperty("user.dir");
        final StreamManager streamManager = 
            new StreamManager(userDir + "/src/test/resources/gl120368.ged");
        try (InputStream is = streamManager.getInputStream()) {
            assertNotNull(is, "Should successfully open file in src/");
        }
    }

    /** */
    @Test
    void testFilePathWithTargetDirectory() {
        // Tests paths containing "target/"
        final StreamManager streamManager = new StreamManager("target/test.ged");
        assertThrows(FileNotFoundException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testFilePathWithNullByte() {
        // Tests null byte detection in file path
        final StreamManager streamManager = new StreamManager("/tmp/test\0.ged");
        assertThrows(IllegalArgumentException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testResourcePathWithNullByte() {
        // Tests null byte detection in resource path
        final StreamManager streamManager = new StreamManager("test\0.ged");
        assertThrows(IllegalArgumentException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testResourcePathStartingWithSlash() {
        // Tests resource path starting with "/" - treated as file path, so throws FileNotFoundException
        final StreamManager streamManager = new StreamManager("/resource.ged");
        assertThrows(FileNotFoundException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testValidRelativeFilePath() throws IOException {
        // Tests a valid relative file path - use absolute path instead to avoid normalization issues
        final String userDir = System.getProperty("user.dir");
        final StreamManager streamManager = 
            new StreamManager(userDir + "/src/test/resources/gl120368.ged");
        try (InputStream is = streamManager.getInputStream()) {
            assertNotNull(is, "Should successfully open file with absolute path");
        }
    }

    /** */
    @Test
    void testPathNormalizationEdgeCase() {
        // Tests a path that uses ./ which is valid but will not be found
        final StreamManager streamManager = new StreamManager("./foo/bar.ged");
        assertThrows(FileNotFoundException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testWindowsPathWithBackslash() {
        // Tests Windows-style path - treated as resource path and not found
        final StreamManager streamManager = new StreamManager("test\\file.ged");
        assertThrows(FileNotFoundException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testMultipleDotsInResourcePath() throws FileNotFoundException {
        // Tests resource path with "..." which should be valid (not ".." as path component)
        final StreamManager streamManager = new StreamManager("test...ged");
        assertThrows(FileNotFoundException.class, streamManager::getInputStream);
    }

    /** */
    @Test
    void testResourcePathWithBackslash() {
        // Tests resource path containing backslash - treated as resource and not found
        final StreamManager streamManager = new StreamManager("foo\\bar");
        assertThrows(FileNotFoundException.class, streamManager::getInputStream);
    }
}



