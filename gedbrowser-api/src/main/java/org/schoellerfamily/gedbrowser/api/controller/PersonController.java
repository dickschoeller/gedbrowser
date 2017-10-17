package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class PersonController
    extends OperationsEnabler<Person, PersonDocument>
    implements CreateOperations<Person, PersonDocument, ApiPerson>,
        Fetcher<Person, PersonDocument, ApiPerson> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Person> getGedClass() {
        return Person.class;
    }

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/persons")
    @ResponseBody
    public List<ApiPerson> readPersons(
            @PathVariable final String db) {
        logger.info("Entering read /dbs/" + db + "/persons");
        return getD2dm().convert(fetch(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiPerson readPerson(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id);
        return getD2dm().convert(fetch(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the attributes of the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/persons/{id}/attributes")
    @ResponseBody
    public List<ApiAttribute> readPersonAttributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id
                + "/attributes");
        return getD2dm().attributes(fetch(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/persons/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject readPersonAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id
                + "/attributes/" + index);
        return getD2dm().attribute(fetch(db, id), index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param type the type we are looking for
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/persons/{id}/{type}")
    @ResponseBody
    public List<ApiAttribute> readPersonAttributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id + "/"
            + type);
        return getD2dm().attributes(fetch(db, id), type);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/persons/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject readPersonAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id + "/"
            + type + "/" + index);
        return getD2dm().attribute(fetch(db, id), type, index);
    }

    /**
     * @param db the name of the db to access
     * @param person the data for the person
     * @return the person as created
     */
    @PostMapping(value = "/dbs/{db}/persons")
    @ResponseBody
    public ApiObject createPerson(@PathVariable final String db,
            @RequestBody final ApiPerson person) {
        logger.info("Entering create person in db: " + db);
        return create(fetchRoot(db), person, (i, id) ->
            new ApiPerson(i.getType(), id, i.getAttributes(), i.getIndexName(),
                    i.getSurname()));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @param index the index of the attribute
     * @param attribute the attribute value to add
     * @return the attribute
     */
    @PostMapping(value = "/dbs/{db}/persons/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject createPersonAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index,
            @RequestBody final ApiAttribute attribute) {
        logger.info("Entering person createAttribute,"
                + " db: " + db + ", id: " + id + ", index: " + index);
        return createAttribute(fetch(db, id), index, attribute);
    }
}
