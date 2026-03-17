package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.ObjectCrud;
import org.schoellerfamily.gedbrowser.api.crud.OperationsEnabler;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.schoellerfamily.gedbrowser.security.util.RequestUserUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Handles requests for persons.
 *
 * @author Richard Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonsController {

    /** */
    private final UserService userService;

    /** */
    private final CalendarProvider provider;

    /** */
    private final RepositoryManagerMongo repositoryManager;

    /** */
    private final GedObjectFileLoader loader;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final PersonGeoService personGeoService;

    private ObjectCrud<ApiPerson> crud() {
        return new PersonCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * Executes create.
     *
     * @param request the request
     * @param db the db
     * @param person the person
     * @return the resulting api person
     */
    @PostMapping(value = "/v1/dbs/{db}/persons")
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
     * Executes read.
     *
     * @param request the request
     * @param db the db
     * @return the resulting list
     */
    @GetMapping(value = "/v1/dbs/{db}/persons")
    public List<ApiPerson> read(final HttpServletRequest request,
            @PathVariable final String db) {
        final List<PersonDocument> allPersons = ((PersonCrud) crud()).read(repositoryManager, db);
        return hide(request, allPersons);
    }

    private List<ApiPerson> hide(final HttpServletRequest request,
            final List<PersonDocument> allPersons) {
        log.info("Start hiding list of persons");
        final RequestUserUtil requestUserUtil = new RequestUserUtil(request, userService);
        final boolean hasUser = requestUserUtil.hasUser();
        final boolean hasAdmin = requestUserUtil.hasAdmin();
        final OperationsEnabler<?> enabler = (OperationsEnabler<?>) crud();
        final List<ApiPerson> list = new java.util.ArrayList<>();
        for (final PersonDocument doc : allPersons) {
            final Person person = doc.getGedObject();
            if (!shouldHideConfidential(person, hasAdmin)
                    && !shouldHideLiving(person, hasUser)) {
                final ApiPerson apiPerson = (ApiPerson) enabler.getD2dm().convert(doc);
                list.add(apiPerson);
            }
        }
        log.info("Done hiding list of persons");
        return list;
    }

    /**
     * Executes read.
     *
     * @param request the request
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api person
     */
    @GetMapping(value = "/v1/dbs/{db}/persons/{id}")
    public ApiPerson read(
            final HttpServletRequest request,
            @PathVariable final String db,
            @PathVariable final String id) {
        final PersonCrud personCrud = (PersonCrud) crud();
        final PersonDocument personDoc = personCrud.read(repositoryManager, db, id);
        final Person person = personDoc.getGedObject();
        final RequestUserUtil util = new RequestUserUtil(request, userService);
        if (shouldHideConfidential(person, util.hasAdmin())) {
            throw new ObjectNotFoundException("person not found", "ApiPerson", db, id);
        }
        if (shouldHideLiving(person, util.hasUser())) {
            return createDummyLivingPerson(id);
        }
        log.info("entering read person: {}", id);
        final ApiPerson apiPerson = personCrud.getD2dm().convert(personDoc);
        final List<PlaceInfo> places = personGeoService.fetchPlaces(person, util);
        return apiPerson.toBuilder().places(places).build();
    }

    private boolean shouldHideConfidential(final Person person, final boolean hasAdmin) {
        if (hasAdmin) {
            return false;
        }
        final PersonVisitor visitor = new PersonVisitor();
        person.accept(visitor);
        return visitor.isConfidential();
    }

    private boolean shouldHideLiving(final Person person, final boolean hasUser) {
        return !hasUser && new LivingEstimator(person, provider).estimate();
    }

    @SuppressWarnings("S3252")
    private ApiPerson createDummyLivingPerson(final String id) {
        return ApiPerson.builder()
            .string(id)
            .indexName("Living")
            .surname("")
            .build();
    }

    /**
     * Executes update.
     *
     * @param request the request
     * @param db the db
     * @param id the unique identifier for the target
     * @param person the person
     * @return the resulting api person
     */
    @PutMapping(value = "/v1/dbs/{db}/persons/{id}")
    public ApiPerson update(
            final HttpServletRequest request,
            @PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        final RequestUserUtil requestUserUtil = new RequestUserUtil(request, userService);
        if (!requestUserUtil.hasAdmin()) {
            throw new AccessDeniedException("go away");
        }
        log.info("entering update person: {}", id);
        return crud().updateOne(db, id, person);
    }

    /**
     * Executes delete.
     *
     * @param request the request
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api person
     */
    @DeleteMapping(value = "/v1/dbs/{db}/persons/{id}")
    public ApiPerson delete(
            final HttpServletRequest request,
            @PathVariable final String db,
            @PathVariable final String id) {
        final RequestUserUtil requestUserUtil = new RequestUserUtil(request, userService);
        if (!requestUserUtil.hasAdmin()) {
            throw new AccessDeniedException("go away");
        }
        log.info("entering delete person: {}", id);
        return crud().deleteOne(db, id);
    }
}
