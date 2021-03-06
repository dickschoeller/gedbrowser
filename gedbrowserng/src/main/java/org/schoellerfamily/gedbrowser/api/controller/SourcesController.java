package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.crud.SourceCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
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
public class SourcesController extends CrudInvoker {
    /**
     * @return the CRUD object for manipulating persons
     */
    private ObjectCrud<ApiSource> crud() {
        return new SourceCrud(getLoader(), getConverter(), getManager());
    }

    /**
     * @param db the name of the db to access
     * @param source the data for the source
     * @return the source as created
     */
    @PostMapping(value = "/v1/dbs/{db}/sources")
    @ResponseBody
    public ApiSource create(
            @PathVariable final String db,
            @RequestBody final ApiSource source) {
        return crud().createOne(db, source);
    }

    /**
     * @param db the name of the db to access
     * @return the list of sources
     */
    @GetMapping(value = "/v1/dbs/{db}/sources")
    @ResponseBody
    public List<ApiSource> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the source
     */
    @GetMapping(value = "/v1/dbs/{db}/sources/{id}")
    @ResponseBody
    public ApiSource read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the source to update
     * @param source the data for the source
     * @return the source as created
     */
    @PutMapping(value = "/v1/dbs/{db}/sources/{id}")
    @ResponseBody
    public ApiSource update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiSource source) {
        return crud().updateOne(db, id, source);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the deleted object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/sources/{id}")
    @ResponseBody
    public ApiSource delete(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
