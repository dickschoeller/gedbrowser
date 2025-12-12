package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.crud.ChildCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
public final class ChildrenController {

    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

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
        log.info("Entering ceateChild");
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
        log.info("Entering ceateChild");
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
        log.info("Entering ceateChildInFamily");
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
        log.info("Entering linkChildInFamily");
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
    public ApiPerson unlinkChild(@PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String child) {
        log.info("Entering linkChildInFamily");
        return childCrud().unlinkChild(db, id, child);
    }
}
