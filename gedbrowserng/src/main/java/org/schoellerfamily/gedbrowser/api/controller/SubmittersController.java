package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.crud.SubmitterCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;



/**
 * Handles requests for submitters.
 *
 * @author Richard Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
@RequiredArgsConstructor
public class SubmittersController {
    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    private ObjectCrud<ApiSubmitter> crud() {
        return new SubmitterCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * Returns the api submitter.
     *
     * @param db the db
     * @param submitter the submitter
     * @return the resulting api submitter
     */
    @PostMapping(value = "/v1/dbs/{db}/submitters")
    public ApiSubmitter create(
            @PathVariable final String db,
            @RequestBody final ApiSubmitter submitter) {
        return crud().createOne(db, submitter);
    }

    /**
     * Returns the list.
     *
     * @param db the db
     * @return the resulting list
     */
    @GetMapping(value = "/v1/dbs/{db}/submitters")
    public List<ApiSubmitter> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * Returns the api submitter.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api submitter
     */
    @GetMapping(value = "/v1/dbs/{db}/submitters/{id}")
    public ApiSubmitter read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * Returns the api submitter.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param submitter the submitter
     * @return the resulting api submitter
     */
    @PutMapping(value = "/v1/dbs/{db}/submitters/{id}")
    public ApiSubmitter update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiSubmitter submitter) {
        return crud().updateOne(db, id, submitter);
    }

    /**
     * Returns the api submitter.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api submitter
     */
    @DeleteMapping(value = "/v1/dbs/{db}/submitters/{id}")
    public ApiSubmitter deleteSubmitter(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
