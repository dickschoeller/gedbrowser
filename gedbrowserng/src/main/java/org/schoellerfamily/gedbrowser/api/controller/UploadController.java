package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.crud.HeadCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.service.storage.StorageService;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@Controller
@RequiredArgsConstructor
@Slf4j
public class UploadController {
    /** */
    private final GedDocumentFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    /** */
    private final StorageService storageService;

    /**
     * Read the head object associated with the name. Public only for testing.
     *
     * @param name the name
     * @return the head
     */
    public ApiHead readOne(final String name) {
        return new HeadCrud(loader, toDocConverter, repositoryManager)
                .readOne(name, "");
    }

    /**
     * Controller for uploading new GEDCOM files.
     *
     * @param file the file being sent
     * @return the head object for that file
     */
    @RequestMapping(value = "/v1/upload",
            method = RequestMethod.POST,
            consumes = "multipart/form-data")
    @ResponseBody
    public final ApiHead upload(
            @RequestParam("file") final MultipartFile file) {
        log.info("in file upload: {}", file.getOriginalFilename());
        storageService.store(file);
        final String name =
                file.getOriginalFilename().replaceAll("\\.ged", "");
        return readOne(name);
    }
}
