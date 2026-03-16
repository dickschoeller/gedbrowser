package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.crud.ChildCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
@RequiredArgsConstructor
@Slf4j
public final class ChildrenController {

    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    private ChildCrud childCrud() {
        return new ChildCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * Creates the child.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    @PostMapping(value = "/v1/dbs/{db}/persons/{id}/children")
    public ApiPerson createChild(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        log.info("Entering ceateChild");
        return childCrud().createChild(db, id, person);
    }

    /**
     * Executes link child.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    @PutMapping(value = "/v1/dbs/{db}/persons/{id}/children")
    public ApiPerson linkChild(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        log.info("Entering ceateChild");
        return childCrud().linkChild(db, id, person);
    }

    /**
     * Creates the child in family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    @PostMapping(value = "/v1/dbs/{db}/families/{id}/children")
    public ApiPerson createChildInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        log.info("Entering ceateChildInFamily");
        return childCrud().createChildInFamily(db, id, person);
    }

    /**
     * Executes link child in family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    @PutMapping(value = "/v1/dbs/{db}/families/{id}/children")
    public ApiPerson linkChildInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        log.info("Entering linkChildInFamily");
        return childCrud().linkChildInFamily(db, id, person);
    }

    /**
     * Executes unlink child.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param child the child
     * @return the resulting api person
     */
    @DeleteMapping(value = "/v1/dbs/{db}/families/{id}/children/{child}")
    public ApiPerson unlinkChild(@PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String child) {
        log.info("Entering linkChildInFamily");
        return childCrud().unlinkChild(db, id, child);
    }
}
