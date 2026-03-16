package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.crud.ParentCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class ParentsController {
    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    private ParentCrud parentCrud() {
        return new ParentCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse
     * @return the person as created
     */
    @PostMapping(value = "/v1/dbs/{db}/persons/{id}/parents")
    public ApiObject createParent(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return parentCrud().createParent(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse
     * @return the person as created
     */
    @PutMapping(value = "/v1/dbs/{db}/persons/{id}/parents")
    public ApiObject linkParent(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return parentCrud().linkParent(db, id, person);
    }
}
