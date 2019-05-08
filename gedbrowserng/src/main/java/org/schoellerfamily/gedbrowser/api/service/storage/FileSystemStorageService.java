package org.schoellerfamily.gedbrowser.api.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.schoellerfamily.gedbrowser.api.PropertiesConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementation of StorageService that keeps the files in a known location
 * on the file system.
 *
 * @author Dick Schoeller
 */
@Service
public final class FileSystemStorageService implements StorageService {
    /** */
    @Autowired
    private PropertiesConfigurationService config;

    /**
     * Constructor.
     */
    @Autowired
    public FileSystemStorageService() {
        // Intentionally empty
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(final MultipartFile file) {
        final String filename = validateFile(file);
        final Path rootLocation = Paths.get(config.gedbrowserHome());
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    /**
     * Extract the filename and validate it.
     *
     * @param file the input file
     * @return the filename
     */
    private String validateFile(final MultipartFile file) {
        final String filename = StringUtils
                .cleanPath(file.getOriginalFilename());
        if (file.isEmpty()) {
            throw new StorageException(
                    "Failed to store empty file " + filename);
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file with relative path outside current"
                    + " directory "
                    + filename);
        }
        return filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        final Path rootLocation = Paths.get(config.gedbrowserHome());
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
