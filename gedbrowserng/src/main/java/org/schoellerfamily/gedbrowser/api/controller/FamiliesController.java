package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
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
public final class FamiliesController {
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
     * @return the CRUD object for manipulating families
     */
    private FamilyCrud familyCrud() {
        return new FamilyCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param family the data for the family
     * @return the family as created
     */
    @PostMapping(value = "/v1/dbs/{db}/families")
    @ResponseBody
    public ApiObject createFamily(@PathVariable final String db,
            @RequestBody final ApiFamily family) {
        return familyCrud().createFamily(db, family);
    }

    /**
     * @param db the name of the db to access
     * @return the list of families
     */
    @GetMapping(value = "/v1/dbs/{db}/families")
    @ResponseBody
    public List<ApiFamily> readFamilies(
            @PathVariable final String db) {
        return familyCrud().readFamilies(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @return the family
     */
    @GetMapping(value = "/v1/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiFamily readFamily(
            @PathVariable final String db,
            @PathVariable final String id) {
        return familyCrud().readFamily(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family to update
     * @param family the data for the family
     * @return the family as created
     */
    @PutMapping(value = "/v1/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiObject updateFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiFamily family) {
        return familyCrud().updateFamily(db, id, family);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @return the deleted object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiFamily deleteFamily(
            @PathVariable final String db,
            @PathVariable final String id) {
        return familyCrud().deleteFamily(db, id);
    }
}
