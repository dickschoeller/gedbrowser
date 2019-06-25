package org.schoellerfamily.gedbrowser.api.service.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface describing the methods of a service for storing files.
 *
 * @author Dick Schoeller
 */
public interface StorageService {
    /**
     * Store a file.
     *
     * @param file the file to store
     */
    void store(MultipartFile file);
}
