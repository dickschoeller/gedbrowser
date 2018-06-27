package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
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
public class PersonsController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
     * @return the CRUD object for manipulating persons
     */
    private PersonCrud personCrud() {
        return new PersonCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param db the name of the db to access
     * @param person the data for the person
     * @return the person as created
     */
    @PostMapping(value = "/v1/dbs/{db}/persons")
    @ResponseBody
    public ApiObject createPerson(@PathVariable final String db,
            @RequestBody final ApiPerson person) {
        return personCrud().createPerson(db, person);
    }

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    @GetMapping(value = "/v1/dbs/{db}/persons")
    @ResponseBody
    public List<ApiPerson> readPersons(
            @PathVariable final String db) {
        return personCrud().readPersons(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    @GetMapping(value = "/v1/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiPerson readPerson(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("entering read person: " + id);
        return personCrud().readPerson(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person to update
     * @param person the data for the person
     * @return the person as created
     */
    @PutMapping(value = "/v1/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiObject updatePerson(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        logger.info("entering update person: " + id);
        return personCrud().updatePerson(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the deleted person object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiPerson deletePerson(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("entering delete person: " + id);
        return personCrud().deletePerson(db, id);
    }
}
