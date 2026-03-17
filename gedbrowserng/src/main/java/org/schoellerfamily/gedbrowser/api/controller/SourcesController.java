package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.crud.SourceCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
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
 * Handles requests for sources.
 *
 * @author Richard Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
@RequiredArgsConstructor
public class SourcesController {
    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    private ObjectCrud<ApiSource> crud() {
        return new SourceCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * Returns the api source.
     *
     * @param db the db
     * @param source the source
     * @return the resulting api source
     */
    @PostMapping(value = "/v1/dbs/{db}/sources")
    public ApiSource create(
            @PathVariable final String db,
            @RequestBody final ApiSource source) {
        return crud().createOne(db, source);
    }

    /**
     * Returns the list.
     *
     * @param db the db
     * @return the resulting list
     */
    @GetMapping(value = "/v1/dbs/{db}/sources")
    public List<ApiSource> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * Returns the api source.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api source
     */
    @GetMapping(value = "/v1/dbs/{db}/sources/{id}")
    public ApiSource read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * Returns the api source.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param source the source
     * @return the resulting api source
     */
    @PutMapping(value = "/v1/dbs/{db}/sources/{id}")
    public ApiSource update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiSource source) {
        return crud().updateOne(db, id, source);
    }

    /**
     * Returns the api source.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api source
     */
    @DeleteMapping(value = "/v1/dbs/{db}/sources/{id}")
    public ApiSource delete(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
