package org.schoellerfamily.gedbrowser.api.service.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * Provides services for storage.
 *
 * @author Richard Schoeller
 */
public interface StorageService {
    /**
     * Store a file.
     *
     * @param file the file to store
     */
    void store(MultipartFile file);
}
