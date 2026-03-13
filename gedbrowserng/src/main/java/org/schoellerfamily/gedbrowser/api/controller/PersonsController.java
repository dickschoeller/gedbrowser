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
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.security.service.UserService;
import org.schoellerfamily.gedbrowser.security.util.RequestUserUtil;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
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
 * @author Dick Schoeller
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
    private final GeoServiceClient geoServiceClient;

    /** */
    private final ApplicationInfo appInfo;

    /**
     * @return the CRUD object for manipulating persons
     */
    private ObjectCrud<ApiPerson> crud() {
        return new PersonCrud(loader, toDocConverter, repositoryManager);
    }

    /**
     * @param request the servlet request object
     * @param db the name of the db to access
     * @param person the data for the person
     * @return the person as created
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
     * @param request the servlet request coming in
     * @param db the name of the db to access
     * @return the list of persons
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
     * @param request the servlet request coming in
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
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
        final RenderingContext renderingContext = createRenderingContext(util);
        final List<PlaceInfo> places = fetchPlaces(person, renderingContext);
        return apiPerson.toBuilder().places(places).build();
    }

    /**
     * Build rendering context for person details based on authenticated user.
     *
     * @param requestUserUtil utility to inspect current request user
     * @return rendering context scoped to anonymous/user/admin
     */
    private RenderingContext createRenderingContext(final RequestUserUtil requestUserUtil) {
        if (requestUserUtil.hasAdmin() || requestUserUtil.hasUser()) {
            return new RenderingContext(requestUserUtil.getUser(), appInfo, provider);
        }
        return RenderingContext.anonymous(appInfo, provider);
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

    /**
     * Create a minimal person for return, who is only identified as living.
     *
     * @param id the person ID
     * @return the dummy person
     */
    private ApiPerson createDummyLivingPerson(final String id) {
        return ApiPerson.builder()
            .string(id)
            .indexName("Living")
            .surname("")
            .build();
    }

    /**
     * Fetch the places for a person and return them as a list.
     *
     * @param person the person to fetch places for
     * @param renderingContext the rendering context for the operation
     * @return the list of places
     */
    private List<PlaceInfo> fetchPlaces(final Person person,
            final RenderingContext renderingContext) {
        final PlaceListRenderer placeRenderer = new PlaceListRenderer(person,
            geoServiceClient, renderingContext);
        return placeRenderer.render();
    }

    /**
     * @param request the servlet request coming in
     * @param db the name of the db to access
     * @param id the id of the person to update
     * @param person the data for the person
     * @return the person as created
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
     * @param request the servlet request coming in
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the deleted person object
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
