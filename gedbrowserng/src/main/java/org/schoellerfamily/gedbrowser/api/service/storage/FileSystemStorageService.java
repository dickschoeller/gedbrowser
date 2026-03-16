package org.schoellerfamily.gedbrowser.api.service.storage;

import static org.springframework.util.StringUtils.cleanPath;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.gedbrowser.api.GedbrowserPropertiesService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of StorageService that keeps the files in a known location on
 * the file system.
 *
 * @author Dick Schoeller
 */
@Service
@RequiredArgsConstructor
public class FileSystemStorageService implements StorageService {
    /** */
    private final GedbrowserPropertiesService gedbrowserProperties;

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(final MultipartFile file) {
        final String filename = validateFile(file);
        final Path rootLocation = Paths.get(gedbrowserProperties.gedbrowserHome()).normalize();
        final Path resolvedPath = rootLocation.resolve(filename).normalize();
        // Ensure the resolved path is still under the root directory
        if (!resolvedPath.startsWith(rootLocation)) {
            throw new StorageException(
                "Cannot store file outside root directory: " + filename);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file %s".formatted(filename), e);
        }
    }

    private String validateFile(final MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            throw new StorageException("Failed to store file with empty name");
        }
        final String filename = cleanPath(originalFilename);
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file %s".formatted(filename));
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new StorageException(
                "Cannot store file with relative path outside current directory %s"
                    .formatted(filename));
        }
        return filename;
    }
}
