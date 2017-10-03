package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.transformers.ApiModelToGedObjectVisitor;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
public class PersonController extends Fetcher {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

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
        logger.info("Entering persons, db: " + db);
        final List<ApiPerson> list = new ArrayList<>();
        for (final PersonDocument person : fetchPersons(db)) {
            list.add(d2dm.convert(person));
        }
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
        logger.info("Entering person, db: " + db + ", id: " + id);
        return d2dm.convert(fetchPerson(db, id));
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
        logger.info("Entering person attributes, db: " + db + ", id: " + id);
        return d2dm.convert(fetchPerson(db, id)).getAttributes();
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
        logger.info("Entering person attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetchPerson(db, id)).getAttributes();
        if (index >= attributes.size()) {
            return null;
        }
        return attributes.get(index);
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
    public ApiObject createAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index,
            @RequestBody final ApiAttribute attribute) {
        logger.info("Entering person createAttribute, db: " + db
                + ", id: " + id
                + ", index: " + index);
        final PersonDocument personDocument = fetchPerson(db, id);
        final List<ApiObject> attributes = d2dm.convert(personDocument)
                .getAttributes();
        if (index >= attributes.size()) {
            return null;
        }
        final Person person = personDocument.getGedObject();
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(
                        new GedObjectBuilder(), person);
        attribute.accept(visitor);
        final GedObject g = visitor.getGedObject();
        person.getAttributes().remove(g);
        person.getAttributes().add(index, g);
        save(person);
//        return attributes.get(index);
        return attribute;
    }

    /**
     * Save the root to the database.
     *
     * @param person the root document
     */
    private void save(final Person person) {
        final PersonDocumentMongo personDoc =
                (PersonDocumentMongo) toDocConverter.createGedDocument(person);
        try {
            final PersonDocumentRepositoryMongo repo =
                    repositoryManager.getPersonDocumentRepository();
            final PersonDocumentMongo oldDoc = (PersonDocumentMongo) repo
                    .findByFileAndString(person.getFilename(),
                            person.getString());
            repo.delete(oldDoc);
            repo.save(personDoc);
        } catch (DataAccessException e) {
            logger.error("Could not save root: " + person.getDbName(), e);
        }
    }

}
