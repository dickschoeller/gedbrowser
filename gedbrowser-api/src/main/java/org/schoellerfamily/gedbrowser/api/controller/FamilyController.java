package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class FamilyController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /**
     * Handles data conversion from DB model to API model.
     */
    private final DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/families")
    @ResponseBody
    public List<ApiFamily> families(
            @PathVariable final String db) {
        logger.info("Entering families, db: " + db);
        final List<ApiFamily> list = new ArrayList<>();
        for (final FamilyDocument family : fetchFamilies(db)) {
            list.add(d2dm.convert(family));
        }
        return list;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiFamily family(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering family, db: " + db + ", id: " + id);
        return d2dm.convert(fetchFamily(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the attributes of the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/attributes")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering family attributes, db: " + db + ", id: " + id);
        return d2dm.convert(fetchFamily(db, id)).getAttributes();
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering family attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetchFamily(db, id)).getAttributes();
        if (index >= attributes.size()) {
            return null;
        }
        return attributes.get(index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/children")
    @ResponseBody
    public List<ApiObject> children(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering children, db: " + db + ", id: " + id);
        final List<ApiObject> attributes =
                d2dm.convert(fetchFamily(db, id)).getAttributes();
        final List<ApiObject> children = new ArrayList<>();
        for (final ApiObject attribute : attributes) {
            if ("child".equals(attribute.getType())) {
                children.add(attribute);
            }
        }
        return children;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/children/{index}")
    @ResponseBody
    public ApiObject child(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering child, db: " + db + ", id: " + id + ", index: "
                + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetchFamily(db, id)).getAttributes();
        final List<ApiObject> children = new ArrayList<>();
        for (final ApiObject attribute : attributes) {
            if ("child".equals(attribute.getType())) {
                children.add(attribute);
            }
        }
        if (index >= children.size()) {
            return null;
        }
        return children.get(index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param type the type we are looking for
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/{type}")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering family attributes, db: " + db + ", id: " + id
                + ", index: " + type);
        final List<ApiObject> attributes =
                d2dm.convert(fetchFamily(db, id)).getAttributes();
        final List<ApiObject> list = new ArrayList<>();
        for (final ApiObject object : attributes) {
            if (type.equals(object.getType())) {
                list.add(object);
            }
            if ("attribute".equals(object.getType())
                    && type.equalsIgnoreCase(object.getString())) {
                list.add(object);
            }
        }
        return list;
    }

    /**
     * @param dbName the name of the database
     * @return the list of persons
     */
    private List<FamilyDocument> fetchFamilies(final String dbName) {
        return find(fetchRoot(dbName));
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the person
     * @return the person
     */
    private FamilyDocument fetchFamily(final String dbName,
            final String idString) {
        final FamilyDocument family = find(fetchRoot(dbName), idString);
        if (family == null) {
            logger.debug("Family not found: " + idString);
//            throw new PersonNotFoundException(
//                    "Person " + idString + " not found", idString,
//                    root.getDbName(), context);
        }
        return family;
    }

    /**
     * @param dbName the name of the database
     * @return the root object
     */
    private RootDocument fetchRoot(final String dbName) {
        final RootDocument root = loader.loadDocument(dbName);
        if (root == null) {
            logger.debug("Data set not found: " + dbName);
//            throw new DataSetNotFoundException(
//                    "Data set " + dbName + " not found", dbName);
        }
        return root;
    }

    /**
     * @param root the root document of the data set to search
     * @param idString the ID of the family
     * @return the family document
     */
    private FamilyDocument find(final RootDocument root,
            final String idString) {
        return (FamilyDocument) repositoryManager.get(Family.class)
                .findByRootAndString(root, idString);
    }

    /**
     * @param root the root document of the data set to search
     * @return the list of family documents
     */
    private List<FamilyDocument> find(final RootDocument root) {
        final List<FamilyDocument> all = new ArrayList<>();
        for (final GedDocument<?> document
                : repositoryManager.get(Family.class).findAll(root)) {
            all.add((FamilyDocument) document);
        }
        return all;
    }
}
