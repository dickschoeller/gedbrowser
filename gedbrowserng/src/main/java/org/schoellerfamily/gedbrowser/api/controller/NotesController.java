package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.NoteCrud;
import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
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
public class NotesController extends CrudInvoker {
    /**
     * @return the CRUD object for manipulating notes
     */
    private ObjectCrud<ApiNote> crud() {
        return new NoteCrud(getLoader(), getConverter(), getManager());
    }

    /**
     * @param db the name of the db to access
     * @param note the data for the note
     * @return the note as created
     */
    @PostMapping(value = "/v1/dbs/{db}/notes")
    @ResponseBody
    public ApiNote create(
            @PathVariable final String db,
            @RequestBody final ApiNote note) {
        return crud().createOne(db, note);
    }

    /**
     * @param db the name of the db to access
     * @return the list of notes
     */
    @GetMapping(value = "/v1/dbs/{db}/notes")
    @ResponseBody
    public List<ApiNote> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the note
     */
    @GetMapping(value = "/v1/dbs/{db}/notes/{id}")
    @ResponseBody
    public ApiNote read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the note to update
     * @param note the data for the note
     * @return the note as created
     */
    @PutMapping(value = "/v1/dbs/{db}/notes/{id}")
    @ResponseBody
    public ApiNote update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiNote note) {
        return crud().updateOne(db, id, note);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the deleted object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/notes/{id}")
    @ResponseBody
    public ApiNote delete(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
