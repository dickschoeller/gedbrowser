package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
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
public final class FamiliesController {
    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    private ObjectCrud<ApiFamily> crud() {
        return new FamilyCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * Returns the api family.
     *
     * @param db the db
     * @param family the family
     * @return the resulting api family
     */
    @PostMapping(value = "/v1/dbs/{db}/families")
    public ApiFamily create(
            @PathVariable final String db,
            @RequestBody final ApiFamily family) {
        return crud().createOne(db, family);
    }

    /**
     * Returns the list.
     *
     * @param db the db
     * @return the resulting list
     */
    @GetMapping(value = "/v1/dbs/{db}/families")
    public List<ApiFamily> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * Returns the api family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api family
     */
    @GetMapping(value = "/v1/dbs/{db}/families/{id}")
    public ApiFamily read(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().readOne(db, id);
    }

    /**
     * Returns the api family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param family the family
     * @return the resulting api family
     */
    @PutMapping(value = "/v1/dbs/{db}/families/{id}")
    public ApiFamily update(
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiFamily family) {
        return crud().updateOne(db, id, family);
    }

    /**
     * Returns the api family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api family
     */
    @DeleteMapping(value = "/v1/dbs/{db}/families/{id}")
    public ApiFamily delete(
            @PathVariable final String db,
            @PathVariable final String id) {
        return crud().deleteOne(db, id);
    }
}
