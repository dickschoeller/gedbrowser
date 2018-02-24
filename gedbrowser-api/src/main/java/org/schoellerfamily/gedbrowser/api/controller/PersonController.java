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
public class PersonController
    extends OperationsEnabler<Person, PersonDocument>
    implements CrudOperations<Person, PersonDocument, ApiPerson> {
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
     * @param person the data for the person
     * @return the person as created
     */
    @PostMapping(value = "/dbs/{db}/persons")
    @ResponseBody
    public ApiObject createPerson(@PathVariable final String db,
            @RequestBody final ApiPerson person) {
        logger.info("Entering create person in db: " + db);
        return create(readRoot(db), person, (i, id) ->
            new ApiPerson(i.getType(), id, i.getAttributes(), i.getIndexName(),
                    i.getSurname(), i.getLifespan()));
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
        return createAttribute(read(db, id), index, attribute);
    }

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    @GetMapping(value = "/dbs/{db}/persons")
    @ResponseBody
    public List<ApiPerson> readPersons(
            @PathVariable final String db) {
        logger.info("Entering read /dbs/" + db + "/persons");
        return getD2dm().convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    @GetMapping(value = "/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiPerson readPerson(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id);
        return getD2dm().convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the attributes of the person
     */
    @GetMapping(value = "/dbs/{db}/persons/{id}/attributes")
    @ResponseBody
    public List<ApiAttribute> readPersonAttributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id
                + "/attributes");
        return getD2dm().attributes(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param index the index of the attribute
     * @return the attribute
     */
    @GetMapping(value = "/dbs/{db}/persons/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject readPersonAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id
                + "/attributes/" + index);
        return getD2dm().attribute(read(db, id), index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param type the type we are looking for
     * @return the attribute
     */
    @GetMapping(value = "/dbs/{db}/persons/{id}/{type}")
    @ResponseBody
    public List<ApiAttribute> readPersonAttributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id + "/"
            + type);
        return getD2dm().attributes(read(db, id), type);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @GetMapping(value = "/dbs/{db}/persons/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject readPersonAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id + "/"
            + type + "/" + index);
        return getD2dm().attribute(read(db, id), type, index);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the person to update
     * @param person the data for the person
     * @return the person as created
     */
    @PutMapping(value = "/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiObject updatePerson(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiPerson person) {
        logger.info("Entering update person in db: " + db);
        if (!id.equals(person.getString())) {
            return null;
        }
        return update(readRoot(db), person);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the deletedtct
     */
    @DeleteMapping(value = "/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiPerson deletePerson(
            @PathVariable final String db,
            @PathVariable final String id) {
        return delete(readRoot(db), id);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param index the index of the attribute
     * @return the deleted object
     */
    @DeleteMapping(value = "/dbs/{db}/persons/{id}/attributes/{index}")
    @ResponseBody
    public ApiAttribute deletePersonAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        return deleteAttribute(readRoot(db), id, index);
    }
}
