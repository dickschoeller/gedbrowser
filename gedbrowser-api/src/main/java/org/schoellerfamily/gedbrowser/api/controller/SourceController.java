package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
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
public class SourceController {
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
     * @return the list of sources
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/sources")
    @ResponseBody
    public List<ApiSource> sources(
            @PathVariable final String db) {
        logger.info("Entering sources, db: " + db);
        final List<ApiSource> list = new ArrayList<>();
        for (final SourceDocument person : fetchSources(db)) {
            list.add(d2dm.convert(person));
        }
        list.sort(new GetStringComparator());
        return list;
    }

    /**
     * @param dbName the name of the database
     * @return the list of sources
     */
    private List<SourceDocument> fetchSources(final String dbName) {
        return find(fetchRoot(dbName));
    }

    /**
     * @param dbName the name of the database
     * @return the root object of the data set
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
     * @param root the root document of the data set
     * @return the list of sources
     */
    private List<SourceDocument> find(final RootDocument root) {
        final List<SourceDocument> all = new ArrayList<>();
        for (final GedDocument<?> document
                : repositoryManager.get(Source.class).findAll(root)) {
            all.add((SourceDocument) document);
        }
        return all;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}")
    @ResponseBody
    public ApiSource source(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering source, db: " + db + ", id: " + id);
        return d2dm.convert(fetchSource(db, id));
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the source
     * @return the source
     */
    private SourceDocument fetchSource(final String dbName,
            final String idString) {
        final SourceDocument source = find(fetchRoot(dbName), idString);
        if (source == null) {
            logger.debug("Source not found: " + idString);
//            throw new PersonNotFoundException(
//                    "Person " + idString + " not found", idString,
//                    root.getDbName(), context);
        }
        return source;
    }

    /**
     * @param root the root document of the data set
     * @param idString the ID string to look for
     * @return the source document
     */
    private SourceDocument find(final RootDocument root,
            final String idString) {
        return (SourceDocument) repositoryManager.get(Source.class)
                .findByRootAndString(root, idString);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the attributes of the source
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}/attributes")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering source attributes, db: " + db + ", id: " + id);
        return d2dm.convert(fetchSource(db, id)).getAttributes();
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering source attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetchSource(db, id)).getAttributes();
        if (index >= attributes.size()) {
            return null;
        }
        return attributes.get(index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/dbs/{db}/sources/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject createAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering source createAttribute, db: " + db + ", id: " + id
                + ", index: " + index);
        return null;
    }
}
