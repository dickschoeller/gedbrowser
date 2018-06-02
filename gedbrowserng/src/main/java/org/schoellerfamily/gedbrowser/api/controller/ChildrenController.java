package org.schoellerfamily.gedbrowser.api.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.crud.ChildCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@Controller
public class ChildrenController {
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

    /**
     * @return the CRUD object for manipulating spouses
     */
    private ChildCrud childCrud() {
        return new ChildCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose child we are adding
     * @param person the data for the child
     * @return the input person as modified
     */
    @PostMapping(value = "/v1/dbs/{db}/persons/{id}/children")
    @ResponseBody
    public ApiPerson createChild(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        logger.info("Entering ceateChild");
        return childCrud().createChild(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose child we are adding
     * @param person the data for the child
     * @return the input person as modified
     */
    @PutMapping(value = "/v1/dbs/{db}/persons/{id}/children")
    @ResponseBody
    public ApiPerson linkChild(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        logger.info("Entering ceateChild");
        return childCrud().linkChild(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family whose child we are adding
     * @param person the data for the child
     * @return the person as created
     */
    @PostMapping(value = "/v1/dbs/{db}/families/{id}/children")
    @ResponseBody
    public ApiPerson createChildInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        logger.info("Entering ceateChildInFamily");
        return childCrud().createChildInFamily(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family whose child we are adding
     * @param person the data for the child
     * @return the person as created
     */
    @PutMapping(value = "/v1/dbs/{db}/families/{id}/children")
    @ResponseBody
    public ApiPerson linkChildInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        logger.info("Entering linkChildInFamily");
        return childCrud().linkChildInFamily(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family whose child we are adding
     * @param child the id of the child
     * @return the person as created
     */
    @DeleteMapping(value = "/v1/dbs/{db}/families/{id}/children/{child}")
    @ResponseBody
    public ApiPerson unlinkChildInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String child) {
        logger.info("Entering linkChildInFamily");
        return childCrud().unlinkChild(db, id, child);
    }
}
