package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.api.crud.SpouseCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class SpousesController extends CrudInvoker {
    /**
     * @return the CRUD object for manipulating spouses
     */
    private SpouseCrud spouseCrud() {
        return new SpouseCrud(getLoader(), getConverter(), getManager());
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse
     * @return the person as created
     */
    @PostMapping(value = "/v1/dbs/{db}/persons/{id}/spouses")
    @ResponseBody
    public ApiPerson createSpouse(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return spouseCrud().createSpouse(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse
     * @return the person as created
     */
    @PutMapping(value = "/v1/dbs/{db}/persons/{id}/spouses")
    @ResponseBody
    public ApiPerson linkSpouse(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return spouseCrud().linkSpouse(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse
     * @return the person as created
     */
    @PostMapping(value = "/v1/dbs/{db}/families/{id}/spouses")
    @ResponseBody
    public ApiPerson createSpouseInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return spouseCrud().createSpouseInFamily(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param person the data for the spouse
     * @return the person as created
     */
    @PutMapping(value = "/v1/dbs/{db}/families/{id}/spouses")
    @ResponseBody
    public ApiPerson linkSpouseInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        return spouseCrud().linkSpouseInFamily(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person whose spouse we are adding
     * @param sid the data for the spouse
     * @return the person whose link was deleted
     */
    @DeleteMapping(value = "/v1/dbs/{db}/families/{id}/spouses/{sid}")
    @ResponseBody
    public ApiPerson unlinkSpouseInFamily(@PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String sid) {
        return spouseCrud().unlinkSpouseInFamily(db, id, sid);
    }
}
