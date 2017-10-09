package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class PersonController extends Fetcher<PersonDocument> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());
//
//    /** */
//    @Autowired
//    private transient RepositoryManagerMongo repositoryManager;
//
//    /** */
//    @Autowired
//    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /**
     * Handles data conversion from DB model to API model.
     */
    private final DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/persons")
    @ResponseBody
    public List<ApiPerson> persons(
            @PathVariable final String db) {
        logger.info("Entering read /dbs/" + db + "/persons");
        final List<ApiPerson> list = new ArrayList<>();
        for (final PersonDocument person : fetch(db, Person.class)) {
            list.add(d2dm.convert(person));
        }
        list.sort(new GetStringComparator());
        return list;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/persons/{id}")
    @ResponseBody
    public ApiPerson person(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id);
        return d2dm.convert(fetch(db, id, Person.class));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the attributes of the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/persons/{id}/attributes")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id
                + "/attributes");
        return d2dm.convert(fetch(db, id, Person.class)).getAttributes();
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
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id
                + "/attributes/" + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetch(db, id, Person.class)).getAttributes();
        if (index >= attributes.size()) {
            throw new ObjectNotFoundException(
                    "Attribute " + index + "of person " + id + " not found",
                    "attribute", "id/attributes/" + index, db);
        }
        return attributes.get(index);
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
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id + "/"
            + type);
        final List<ApiObject> attributes =
                d2dm.convert(fetch(db, id, Person.class)).getAttributes();
        final List<ApiObject> list = new ArrayList<>();
        for (final ApiObject object : attributes) {
            if (object.isType(type)) {
                list.add(object);
            }
        }
        return list;
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
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/persons/" + id + "/"
            + type + "/" + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetch(db, id, Person.class)).getAttributes();
        final List<ApiObject> list = new ArrayList<>();
        for (final ApiObject object : attributes) {
            if (object.isType(type)) {
                list.add(object);
            }
        }
        if (index >= list.size()) {
            throw new ObjectNotFoundException(
                    type + " " + index + " of person " + id + " not found",
                    "attribute", id + "/attributes/" + type + "/" + index, db);
        }
        return list.get(index);
    }
//
//    /**
//     * @param db the name of the db to access
//     * @param id the ID of the source
//     * @param index the index of the attribute
//     * @param attribute the attribute value to add
//     * @return the attribute
//     */
//    @PostMapping(value = "/dbs/{db}/persons/{id}/attributes/{index}")
//    @ResponseBody
//    public ApiObject createAttribute(
//            @PathVariable final String db,
//            @PathVariable final String id,
//            @PathVariable final int index,
//            @RequestBody final ApiAttribute attribute) {
//        logger.info("Entering person createAttribute,"
//                + " db: " + db
//                + ", id: " + id
//                + ", index: " + index);
//        final PersonDocument personDocument = fetch(db, id, Person.class);
//        final List<ApiObject> attributes = d2dm.convert(personDocument)
//                .getAttributes();
//        if (index >= attributes.size()) {
//            return null;
//        }
//        final Person person = personDocument.getGedObject();
//        final ApiModelToGedObjectVisitor visitor =
//                new ApiModelToGedObjectVisitor(
//                        new GedObjectBuilder(), person);
//        attribute.accept(visitor);
//        final GedObject g = visitor.getGedObject();
//        person.getAttributes().remove(g);
//        person.getAttributes().add(index, g);
//        save(person);
////        return attributes.get(index);
//        return attribute;
//    }
//
//    /**
//     * Save the root to the database.
//     *
//     * @param person the root document
//     */
//    private void save(final Person person) {
//        final PersonDocumentMongo personDoc =
//                (PersonDocumentMongo) toDocConverter.createGedDocument(
//    person);
//        try {
//            final PersonDocumentRepositoryMongo repo =
//                    repositoryManager.getPersonDocumentRepository();
//            final PersonDocumentMongo oldDoc = (PersonDocumentMongo) repo
//                    .findByFileAndString(person.getFilename(),
//                            person.getString());
//            repo.delete(oldDoc);
//            repo.save(personDoc);
//        } catch (DataAccessException e) {
//            logger.error("Could not save root: " + person.getDbName(), e);
//        }
//    }
//
}
