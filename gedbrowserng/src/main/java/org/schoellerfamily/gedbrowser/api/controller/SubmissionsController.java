package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.crud.SubmissionCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
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
public class SubmissionsController extends CrudInvoker {
    /**
     * @return the CRUD object for manipulating submissions
     */
    private ObjectCrud<ApiSubmission> crud() {
        return new SubmissionCrud(getLoader(), getConverter(), getManager());
    }

    /**
     * @param db the name of the db to access
     * @param submission the data for the submission
     * @return the submission as created
     */
    @PostMapping(value = "/v1/dbs/{db}/submissions")
    @ResponseBody
    public ApiSubmission create(
            @PathVariable final String db,
            @RequestBody final ApiSubmission submission) {
        return crud().createOne(db, submission);
    }

    /**
     * @param db the name of the db to access
     * @return the list of submissions
     */
    @GetMapping(value = "/v1/dbs/{db}/submissions")
    @ResponseBody
    public List<ApiSubmission> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the submission
     */
    @GetMapping(value = "/v1/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiSubmission read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the submission to update
     * @param submission the data for the submission
     * @return the submission as created
     */
    @PutMapping(value = "/v1/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiSubmission update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiSubmission submission) {
        return crud().updateOne(db, id, submission);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the deleted object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiSubmission delete(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
