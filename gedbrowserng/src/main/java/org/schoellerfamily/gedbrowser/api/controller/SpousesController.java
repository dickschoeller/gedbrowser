package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.crud.SpouseCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;



/**
 * Handles requests for spouses.
 *
 * @author Richard Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
@RequiredArgsConstructor
public class SpousesController {
    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    private SpouseCrud spouseCrud() {
        return new SpouseCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * Creates the spouse.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    @PostMapping(value = "/v1/dbs/{db}/persons/{id}/spouses")
    public ApiPerson createSpouse(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return spouseCrud().createSpouse(db, id, person);
    }

    /**
     * Returns the api person.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    @PutMapping(value = "/v1/dbs/{db}/persons/{id}/spouses")
    public ApiPerson linkSpouse(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return spouseCrud().linkSpouse(db, id, person);
    }

    /**
     * Creates the spouse in family.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    @PostMapping(value = "/v1/dbs/{db}/families/{id}/spouses")
    public ApiPerson createSpouseInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return spouseCrud().createSpouseInFamily(db, id, person);
    }

    /**
     * Returns the api person.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    @PutMapping(value = "/v1/dbs/{db}/families/{id}/spouses")
    public ApiPerson linkSpouseInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return spouseCrud().linkSpouseInFamily(db, id, person);
    }

    /**
     * Returns the api person.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param sid the unique identifier for s
     * @return the resulting api person
     */
    @DeleteMapping(value = "/v1/dbs/{db}/families/{id}/spouses/{sid}")
    public ApiPerson unlinkSpouseInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String sid) {
        return spouseCrud().unlinkSpouseInFamily(db, id, sid);
    }
}
