package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.NoteCrud;
import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
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
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
@RequiredArgsConstructor
public class NotesController {
    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

        /** */
    private final RepositoryManagerMongo repositoryManager;

    private ObjectCrud<ApiNote> crud() {
        return new NoteCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * Returns the api note.
     *
     * @param db the db
     * @param note the note
     * @return the resulting api note
     */
    @PostMapping(value = "/v1/dbs/{db}/notes")
    public ApiNote create(
            @PathVariable final String db,
            @RequestBody final ApiNote note) {
        return crud().createOne(db, note);
    }

    /**
     * Returns the list.
     *
     * @param db the db
     * @return the resulting list
     */
    @GetMapping(value = "/v1/dbs/{db}/notes")
    public List<ApiNote> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * Returns the api note.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api note
     */
    @GetMapping(value = "/v1/dbs/{db}/notes/{id}")
    public ApiNote read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * Returns the api note.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param note the note
     * @return the resulting api note
     */
    @PutMapping(value = "/v1/dbs/{db}/notes/{id}")
    public ApiNote update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiNote note) {
        return crud().updateOne(db, id, note);
    }

    /**
     * Returns the api note.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api note
     */
    @DeleteMapping(value = "/v1/dbs/{db}/notes/{id}")
    public ApiNote delete(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
