package org.schoellerfamily.gedbrowser.api.service.storage.test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.schoellerfamily.gedbrowser.api.GedbrowserPropertiesService;
import org.schoellerfamily.gedbrowser.api.service.storage.FileSystemStorageService;
import org.schoellerfamily.gedbrowser.api.service.storage.StorageException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Test class for FileSystemStorageService path validation.
 *
 * @author Dick Schoeller
 */
class FileSystemStorageServiceTest {

    /** */
    @TempDir
    private Path tempDir;

    /** */
    private FileSystemStorageService storageService;

    /** */
    private GedbrowserPropertiesService propertiesService;

    /** */
    @BeforeEach
    void setUp() {
        propertiesService = mock(GedbrowserPropertiesService.class);
        when(propertiesService.gedbrowserHome()).thenReturn(tempDir.toString());
        storageService = new FileSystemStorageService(propertiesService);
    }

    /** */
    @Test
    void testValidFile() throws IOException {
        final MultipartFile file = createMockFile("test.ged", "content");
        storageService.store(file);
        // Verify file was created
        final Path filePath = tempDir.resolve("test.ged");
        assertTrue(Files.exists(filePath), "File should exist after storing");
    }

    /** */
    @Test
    void testPathTraversalWithDoubleDots() throws IOException {
        final MultipartFile file = createMockFile("../../../etc/passwd", "content");
        assertThrows(StorageException.class, () -> storageService.store(file));
    }

    /** */
    @Test
    void testPathTraversalInSubdirectory() throws IOException {
        final MultipartFile file = createMockFile("subdir/../../../etc/passwd", "content");
        assertThrows(StorageException.class, () -> storageService.store(file));
    }

    /** */
    @Test
    void testAbsolutePath() throws IOException {
        final MultipartFile file = createMockFile("/etc/passwd", "content");
        assertThrows(StorageException.class, () -> storageService.store(file));
    }

    /** */
    @Test
    void testEmptyFilename() {
        final MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("");
        assertThrows(StorageException.class, () -> storageService.store(file));
    }

    /** */
    @Test
    void testNullFilename() {
        final MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn(null);
        assertThrows(StorageException.class, () -> storageService.store(file));
    }

    /** */
    @Test
    void testEmptyFile() throws IOException {
        final MultipartFile file = createMockFile("test.ged", "");
        assertThrows(StorageException.class, () -> storageService.store(file));
    }

    /**
     * Helper method to create a mock MultipartFile.
     *
     * @param filename the filename
     * @param content the file content
     * @return a mock MultipartFile
     * @throws IOException if an error occurs
     */
    private MultipartFile createMockFile(final String filename, final String content)
        throws IOException {
        final MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn(filename);
        when(file.isEmpty()).thenReturn(StringUtils.isEmpty(content));
        when(file.getInputStream())
            .thenReturn(new ByteArrayInputStream(
                content == null ? new byte[0] : content.getBytes()));
        return file;
    }
}
