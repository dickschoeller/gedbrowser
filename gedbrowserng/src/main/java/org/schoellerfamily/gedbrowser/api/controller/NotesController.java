package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.NoteCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
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
public class NotesController {
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
     * @return the CRUD object for manipulating notes
     */
    private NoteCrud noteCrud() {
        return new NoteCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param note the data for the note
     * @return the note as created
     */
    @PostMapping(value = "/v1/dbs/{db}/notes")
    @ResponseBody
    public ApiObject createNote(@PathVariable final String db,
            @RequestBody final ApiNote note) {
        return noteCrud().createNote(db, note);
    }

    /**
     * @param db the name of the db to access
     * @return the list of notes
     */
    @GetMapping(value = "/v1/dbs/{db}/notes")
    @ResponseBody
    public List<ApiNote> readNotes(
            @PathVariable final String db) {
        return noteCrud().readAll(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the note
     */
    @GetMapping(value = "/v1/dbs/{db}/notes/{id}")
    @ResponseBody
    public ApiNote readNote(
            @PathVariable final String db,
            @PathVariable final String id) {
        return noteCrud().readNote(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the note to update
     * @param note the data for the note
     * @return the note as created
     */
    @PutMapping(value = "/v1/dbs/{db}/notes/{id}")
    @ResponseBody
    public ApiObject updateNote(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiNote note) {
        return noteCrud().updateOne(db, id, note);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the deleted object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/notes/{id}")
    @ResponseBody
    public ApiNote deleteNote(
            @PathVariable final String db,
            @PathVariable final String id) {
        return noteCrud().deleteNote(db, id);
    }
}
