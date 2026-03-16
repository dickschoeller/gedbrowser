package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.crud.SubmissionCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
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
 * Handles requests for submissions.
 *
 * @author Richard Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
@RequiredArgsConstructor
public class SubmissionsController {
    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    private ObjectCrud<ApiSubmission> crud() {
        return new SubmissionCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * Returns the api submission.
     *
     * @param db the db
     * @param submission the submission
     * @return the resulting api submission
     */
    @PostMapping(value = "/v1/dbs/{db}/submissions")
    public ApiSubmission create(
            @PathVariable final String db,
            @RequestBody final ApiSubmission submission) {
        return crud().createOne(db, submission);
    }

    /**
     * Returns the list.
     *
     * @param db the db
     * @return the resulting list
     */
    @GetMapping(value = "/v1/dbs/{db}/submissions")
    public List<ApiSubmission> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * Returns the api submission.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api submission
     */
    @GetMapping(value = "/v1/dbs/{db}/submissions/{id}")
    public ApiSubmission read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * Returns the api submission.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param submission the submission
     * @return the resulting api submission
     */
    @PutMapping(value = "/v1/dbs/{db}/submissions/{id}")
    public ApiSubmission update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiSubmission submission) {
        return crud().updateOne(db, id, submission);
    }

    /**
     * Returns the api submission.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api submission
     */
    @DeleteMapping(value = "/v1/dbs/{db}/submissions/{id}")
    public ApiSubmission delete(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
