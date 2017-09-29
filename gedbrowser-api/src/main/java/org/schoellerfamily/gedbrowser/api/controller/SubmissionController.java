package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.loader.GedFileLoader;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
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
public class SubmissionController {
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
     * @param db the name of the db to access
     * @return the list of submissions
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/submissions")
    @ResponseBody
    public List<ApiSubmission> submissions(
            @PathVariable final String db) {
        logger.info("Entering submissions, db: " + db);
        final List<ApiSubmission> list = new ArrayList<>();
        final DocumentToApiModelTransformer d2dm =
                new DocumentToApiModelTransformer();
        for (final SubmissionDocument person : fetchSubmissions(db)) {
            list.add(d2dm.convert(person));
        }
        return list;
    }

    /**
     * @param dbName the name of the database
     * @return the list of submissions
     */
    private List<SubmissionDocument> fetchSubmissions(final String dbName) {
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
     * @return the list of submissions
     */
    private List<SubmissionDocument> find(final RootDocument root) {
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
            repo = repositoryManager.get(
                    org.schoellerfamily.gedbrowser.datamodel.Submission.class);
        final List<SubmissionDocument> all = new ArrayList<>();
        for (final GedDocument<?> document : repo.findAll(root)) {
            all.add((SubmissionDocument) document);
        }
        return all;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiSubmission submission(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering person, db: " + db + ", id: " + id);
        final DocumentToApiModelTransformer d2dm =
                new DocumentToApiModelTransformer();
        return d2dm.convert(fetchSubmission(db, id));
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the submission
     * @return the submission
     */
    private SubmissionDocument fetchSubmission(final String dbName,
            final String idString) {
        final RootDocument root = fetchRoot(dbName);
        final SubmissionDocument submission = find(root, idString);
        if (submission == null) {
            logger.debug("Submission not found: " + idString);
//            throw new PersonNotFoundException(
//                    "Person " + idString + " not found", idString,
//                    root.getDbName(), context);
        }
        return submission;
    }

    /**
     * @param root the root document of the data set
     * @param idString the ID string to look for
     * @return the submission document
     */
    private SubmissionDocument find(final RootDocument root,
            final String idString) {
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
            repo = repositoryManager.get(
                    org.schoellerfamily.gedbrowser.datamodel.Submission.class);
        final SubmissionDocument person = (SubmissionDocument) repo
                .findByRootAndString(root, idString);
        person.setGedObject(null);
        return person;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the attributes of the submission
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submissions/{id}/attributes")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering attributes, db: " + db + ", id: " + id);
        final DocumentToApiModelTransformer d2dm =
                new DocumentToApiModelTransformer();
        return d2dm.convert(fetchSubmission(db, id)).getAttributes();
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submissions/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        final DocumentToApiModelTransformer d2dm =
                new DocumentToApiModelTransformer();
        final List<ApiObject> attributes =
                d2dm.convert(fetchSubmission(db, id)).getAttributes();
        if (index >= attributes.size()) {
            return null;
        }
        return attributes.get(index);
    }
}
