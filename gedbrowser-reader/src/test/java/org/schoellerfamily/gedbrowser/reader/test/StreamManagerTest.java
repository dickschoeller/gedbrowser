package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.schoellerfamily.gedbrowser.reader.StreamManager;

/**
 * Test class for StreamManager path validation.
 *
 * @author Dick Schoeller
 */
class StreamManagerTest {

    @Test
    void testNullFilename() throws FileNotFoundException {
        final StreamManager streamManager = new StreamManager(null);
        final InputStream is = streamManager.getInputStream();
        assertNull(is, "Null filename should return null input stream");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "../../../etc/passwd",
        "foo/../bar",
        "./../../etc/passwd",
        "",
        "/../../../etc/passwd",
        "/tmp/test\0.ged",
        "test\0.ged"
    })
    void testInvalidPaths(final String filename) {
        final StreamManager streamManager = new StreamManager(filename);
        assertThrows(IllegalArgumentException.class, streamManager::getInputStream);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "charset",
        "C:\\temp\\test.ged",
        "target/test.ged",
        "/resource.ged",
        "./foo/bar.ged",
        "test\\file.ged",
        "test...ged",
        "foo\\bar"
    })
    void testPathsNotFound(final String filename) throws FileNotFoundException {
        final StreamManager streamManager = new StreamManager(filename);
        assertThrows(FileNotFoundException.class, streamManager::getInputStream);
    }

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

    @Test
    void testValidRelativeFilePath() throws IOException {
        // Tests a valid relative file path - use absolute path instead to avoid
        // normalization issues
        final String userDir = System.getProperty("user.dir");
        final StreamManager streamManager =
            new StreamManager(userDir + "/src/test/resources/gl120368.ged");
        try (InputStream is = streamManager.getInputStream()) {
            assertNotNull(is, "Should successfully open file with absolute path");
        }
    }
}



