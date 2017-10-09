package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 *
 * @param <Z> the data type we are fetching
 */
public class Fetcher <Z> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /**
     * @param dbName the name of the database
     * @return the root object
     */
    protected final RootDocument fetchRoot(final String dbName) {
        final RootDocument root = loader.loadDocument(dbName);
        if (root == null) {
            logger.debug("Data set not found: " + dbName);
            throw new DataSetNotFoundException(
                    "Data set " + dbName + " not found", dbName);
        }
        return root;
    }

    /**
     * Generic single item fetcher.
     *
     * @param dbName the name of the database
     * @param idString the ID of the item to fetch
     * @param clazz the class of the search
     * @return the found object
     */
    protected Z fetch(final String dbName, final String idString,
            final Class<? extends GedObject> clazz) {
        final Z document = find(fetchRoot(dbName), idString, clazz);
        if (document == null) {
            logger.debug("Object not found: " + idString);
            final String type =
                    clazz.getSimpleName().toLowerCase(Locale.ENGLISH);
            throw new ObjectNotFoundException(
                    "Object " + idString + " of type " + type + " not found",
                    type, idString, dbName);
        }
        return document;
    }

    /**
     * @param dbName the name of the database
     * @param clazz the class of the search
     * @return the list of persons
     */
    protected List<Z> fetch(final String dbName,
            final Class<? extends GedObject> clazz) {
        return find(fetchRoot(dbName), clazz);
    }

    /**
     * @param root the root document of the data set to search
     * @param idString the ID of the family
     * @param clazz the class that we are searching for
     * @param <T> the type returned
     * @return the family document
     */
    @SuppressWarnings("unchecked")
    protected <T> T find(final RootDocument root,
            final String idString, final Class<? extends GedObject> clazz) {
        return (T) repositoryManager.get(clazz)
                .findByRootAndString(root, idString);
    }

    /**
     * @param root the root document of the data set
     * @param clazz the class being searched
     * @param <T> the type returned
     * @return the list of submitters
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> find(final RootDocument root,
            final Class<? extends GedObject> clazz) {
        final List<T> all = new ArrayList<>();
        for (final GedDocument<?> document
                : repositoryManager.get(clazz).findAll(root)) {
            all.add((T) document);
        }
        return all;
    }
}
