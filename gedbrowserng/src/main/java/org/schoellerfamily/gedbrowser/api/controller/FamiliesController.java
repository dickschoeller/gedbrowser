package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
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
public final class FamiliesController extends CrudInvoker {
    /**
     * @return the CRUD object for manipulating families
     */
    private ObjectCrud<ApiFamily> crud() {
        return new FamilyCrud(getLoader(), getConverter(), getManager());
    }

    /**
     * @param db the name of the db to access
     * @param family the data for the family
     * @return the family as created
     */
    @PostMapping(value = "/v1/dbs/{db}/families")
    @ResponseBody
    public ApiFamily create(
            @PathVariable final String db,
            @RequestBody final ApiFamily family) {
        return crud().createOne(db, family);
    }

    /**
     * @param db the name of the db to access
     * @return the list of families
     */
    @GetMapping(value = "/v1/dbs/{db}/families")
    @ResponseBody
    public List<ApiFamily> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @return the family
     */
    @GetMapping(value = "/v1/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiFamily read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family to update
     * @param family the data for the family
     * @return the family as created
     */
    @PutMapping(value = "/v1/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiFamily update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiFamily family) {
        return crud().updateOne(db, id, family);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @return the deleted object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiFamily delete(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
