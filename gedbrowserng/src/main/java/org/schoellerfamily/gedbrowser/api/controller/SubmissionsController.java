package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.SubmissionCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class SubmissionsController {
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
     * @return the CRUD object for manipulating submissions
     */
    private SubmissionCrud submissionCrud() {
        return new SubmissionCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param submission the data for the submission
     * @return the submission as created
     */
    @PostMapping(value = "/v1/dbs/{db}/submissions")
    @ResponseBody
    public ApiSubmission createSubmission(@PathVariable final String db,
            @RequestBody final ApiSubmission submission) {
        return submissionCrud().createSubmission(db, submission);
    }

    /**
     * @param db the name of the db to access
     * @return the list of submissions
     */
    @GetMapping(value = "/v1/dbs/{db}/submissions")
    @ResponseBody
    public List<ApiSubmission> readSubmissions(
            @PathVariable final String db) {
        return submissionCrud().readAll(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the submission
     */
    @GetMapping(value = "/v1/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiSubmission readSubmission(
            @PathVariable final String db,
            @PathVariable final String id) {
        return submissionCrud().readSubmission(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the submission to update
     * @param submission the data for the submission
     * @return the submission as created
     */
    @PutMapping(value = "/v1/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiObject updateSubmission(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiSubmission submission) {
        return submissionCrud().updateOne(db, id, submission);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the deleted object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiSubmission deleteSubmission(
            @PathVariable final String db,
            @PathVariable final String id) {
        return submissionCrud().deleteSubmission(db, id);
    }
}
