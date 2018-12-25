package org.schoellerfamily.gedbrowser.api.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

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
        final Path rootLocation = Paths.get(this.gedbrowserHome);
        final String filename = StringUtils
                .cleanPath(file.getOriginalFilename());
        try {
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
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        final Path rootLocation = Paths.get(this.gedbrowserHome);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
