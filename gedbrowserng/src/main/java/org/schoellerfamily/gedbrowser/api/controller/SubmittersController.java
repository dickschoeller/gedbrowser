package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.crud.SubmitterCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
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
public class SubmittersController extends CrudInvoker {
    /**
     * @return the CRUD object for manipulating submitters
     */
    private ObjectCrud<ApiSubmitter> crud() {
        return new SubmitterCrud(getLoader(), getConverter(), getManager());
    }

    /**
     * @param db the name of the db to access
     * @param submitter the data for the submitter
     * @return the submitter as created
     */
    @PostMapping(value = "/v1/dbs/{db}/submitters")
    @ResponseBody
    public ApiSubmitter create(
            @PathVariable final String db,
            @RequestBody final ApiSubmitter submitter) {
        return crud().createOne(db, submitter);
    }

    /**
     * @param db the name of the db to access
     * @return the list of submitters
     */
    @GetMapping(value = "/v1/dbs/{db}/submitters")
    @ResponseBody
    public List<ApiSubmitter> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the person
     */
    @GetMapping(value = "/v1/dbs/{db}/submitters/{id}")
    @ResponseBody
    public ApiSubmitter read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the submitter to update
     * @param submitter the data for the submitter
     * @return the submitter as created
     */
    @PutMapping(value = "/v1/dbs/{db}/submitters/{id}")
    @ResponseBody
    public ApiSubmitter update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiSubmitter submitter) {
        return crud().updateOne(db, id, submitter);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the deleted object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/submitters/{id}")
    @ResponseBody
    public ApiSubmitter deleteSubmitter(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
