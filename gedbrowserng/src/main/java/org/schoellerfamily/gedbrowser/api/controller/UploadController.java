package org.schoellerfamily.gedbrowser.api.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.crud.HeadCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.service.storage.StorageService;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@Controller
public class UploadController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());
    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /** */
    @Autowired
    private transient StorageService storageService;

    /**
     * @return the CRUD object for manipulating the DB header
     */
    private HeadCrud headCrud() {
        return new HeadCrud(loader, toDocConverter, repositoryManager);
    }

    @RequestMapping(value = "/v1/upload", method = RequestMethod.POST, consumes= "multipart/form-data")
    @ResponseBody
    public final ApiHead upload(@RequestParam("file") MultipartFile file)
            throws JsonProcessingException {
        if (file == null) {
            logger.info("in file upload: file is null");
            return null;
        }
        logger.info("in file upload: " + file.getOriginalFilename());
        storageService.store(file);
        final String name = file.getOriginalFilename().replaceAll("\\.ged", "");
        return headCrud().readHead(name);
    }
}
