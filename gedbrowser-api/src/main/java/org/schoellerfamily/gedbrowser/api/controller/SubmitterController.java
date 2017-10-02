package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.api.loader.GedFileLoader;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
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
public class SubmitterController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

//    /** */
//    @Value("${gedbrowser.home}")
//    private transient String gedbrowserHome;

//    /** */
//    @Autowired
//    private transient ApplicationInfo appInfo;

    /** */
    @Autowired
    private transient GedFileLoader loader;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /**
     * Handles data conversion from DB model to API model.
     */
    private DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @param db the name of the db to access
     * @return the list of submitters
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/submitters")
    @ResponseBody
    public List<ApiSubmitter> submitters(
            @PathVariable final String db) {
        logger.info("Entering submitters, db: " + db);
        final List<ApiSubmitter> list = new ArrayList<>();
        for (final SubmitterDocument person : fetchSubmitters(db)) {
            list.add(d2dm.convert(person));
        }
        return list;
    }

    /**
     * @param dbName the name of the database
     * @return the list of submitters
     */
    private List<SubmitterDocument> fetchSubmitters(final String dbName) {
        final RootDocument root = fetchRoot(dbName);
        return find(root);
    }

    /**
     * @param dbName the name of the database
     * @return the root object of the data set
     */
    private RootDocument fetchRoot(final String dbName) {
        final RootDocument root = loader.load(dbName);
        if (root == null) {
            logger.debug("Data set not found: " + dbName);
//            throw new DataSetNotFoundException(
//                    "Data set " + dbName + " not found", dbName);
        }
        return root;
    }

    /**
     * @param root the root document of the data set
     * @return the list of submitters
     */
    private List<SubmitterDocument> find(final RootDocument root) {
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
        repo = repositoryManager
                .get(org.schoellerfamily.gedbrowser.datamodel.Submitter.class);
        final List<SubmitterDocument> all = new ArrayList<>();
        for (final GedDocument<?> document : repo.findAll(root)) {
            all.add((SubmitterDocument) document);
        }
        return all;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}")
    @ResponseBody
    public ApiSubmitter submitter(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submitter, db: " + db + ", id: " + id);
        return d2dm.convert(fetchSubmitter(db, id));
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the submitter
     * @return the submitter
     */
    private SubmitterDocument fetchSubmitter(final String dbName,
            final String idString) {
        final RootDocument root = fetchRoot(dbName);
        final SubmitterDocument submitter = find(root, idString);
        if (submitter == null) {
            logger.debug("Submitter not found: " + idString);
//            throw new PersonNotFoundException(
//                    "Person " + idString + " not found", idString,
//                    root.getDbName(), context);
        }
        return submitter;
    }

    /**
     * @param root the root document of the data set
     * @param idString the ID string to look for
     * @return the submitter document
     */
    private SubmitterDocument find(final RootDocument root,
            final String idString) {
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
            repo = repositoryManager.get(
                    org.schoellerfamily.gedbrowser.datamodel.Submitter.class);
        final SubmitterDocument person = (SubmitterDocument) repo
                .findByRootAndString(root, idString);
        person.setGedObject(null);
        return person;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the attributes of the submitter
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}/attributes")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submitter attributes, db: " + db
                + ", id: " + id);
        return d2dm.convert(fetchSubmitter(db, id)).getAttributes();
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering submitter attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        final List<ApiObject> attributes = d2dm.convert(fetchSubmitter(db, id))
                .getAttributes();
        if (index >= attributes.size()) {
            return null;
        }
        return attributes.get(index);
    }
}
