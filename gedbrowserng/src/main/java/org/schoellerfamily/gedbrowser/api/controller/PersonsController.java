package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson.Builder;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.schoellerfamily.gedbrowser.security.util.RequestUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
public class PersonsController extends CrudInvoker {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private UserService userService;

    /** */
    @Autowired
    private transient CalendarProvider provider;

    /**
     * @return the CRUD object for manipulating persons
     */
    private ObjectCrud<ApiPerson> crud() {
        return new PersonCrud(getLoader(), getConverter(), getManager());
    }

    /**
     * @param request the servlet request object
     * @param db the name of the db to access
     * @param person the data for the person
     * @return the person as created
     */
    @PostMapping(value = "/v1/dbs/{db}/persons")
    @ResponseBody
    public ApiPerson create(
            final HttpServletRequest request,
            @PathVariable final String db,
            @RequestBody final ApiPerson person) {
        final RequestUserUtil requestUserUtil = new RequestUserUtil(request, userService);
        if (!requestUserUtil.hasAdmin()) {
            throw new AccessDeniedException("go away");
        }
        return crud().createOne(db, person);
    }

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    @GetMapping(value = "/v1/dbs/{db}/persons")
    @ResponseBody
    public List<ApiPerson> read(
            @PathVariable final String db) {
        return crud().readAll(db);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    @GetMapping(value = "/v1/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiPerson read(
            HttpServletRequest request,
            @PathVariable final String db,
            @PathVariable final String id) {
        final Person person = ((PersonCrud)crud()).read(db, id).getGedObject();
        if (!new RequestUserUtil(request, userService).hasUser() &&
                new LivingEstimator(person, provider).estimate()) {
            final Builder builder = new ApiPerson.Builder().id(id).indexName("Living").surname("").build();
            return new ApiPerson(builder);
        }
        logger.info("entering read person: " + id);
        return crud().readOne(db, id);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person to update
     * @param person the data for the person
     * @return the person as created
     */
    @PutMapping(value = "/v1/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiPerson update(
            HttpServletRequest request,
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        final RequestUserUtil requestUserUtil = new RequestUserUtil(request, userService);
        if (!requestUserUtil.hasAdmin()) {
            throw new AccessDeniedException("go away");
        }
        logger.info("entering update person: " + id);
        return crud().updateOne(db, id, person);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the deleted person object
     */
    @DeleteMapping(value = "/v1/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiPerson delete(
            HttpServletRequest request,
            @PathVariable final String db,
            @PathVariable final String id) {
        final RequestUserUtil requestUserUtil = new RequestUserUtil(request, userService);
        if (!requestUserUtil.hasAdmin()) {
            throw new AccessDeniedException("go away");
        }
        logger.info("entering delete person: " + id);
        return crud().deleteOne(db, id);
    }
}
