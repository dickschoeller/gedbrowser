package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;

/**
 * @author Dick Schoeller
 *
 * @param <X> the data model type we are creating
 * @param <Y> the DB type associated with the type X
 * @param <Z> the Api type associated with the type X
 */
public interface Fetcher <X extends GedObject,
    Y extends GedDocument<X>, Z extends ApiObject> extends NewId<X, Y> {
    /**
     * @return the loader
     */
    GedDocumentFileLoader getLoader();

    /**
     * @return the data model class
     */
    Class<X> getGedClass();

    /**
     * @param dbName the name of the database
     * @return the root object
     */
    default RootDocument fetchRoot(final String dbName) {
        final RootDocument root = getLoader().loadDocument(dbName);
        if (root == null) {
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
     * @return the found object
     */
    default Y fetch(final String dbName, final String idString) {
        final Y document = find(fetchRoot(dbName), idString);
        if (document == null) {
            final String type =
                    getGedClass().getSimpleName().toLowerCase(Locale.ENGLISH);
            throw new ObjectNotFoundException(
                    "Object " + idString + " of type " + type + " not found",
                    type, idString, dbName);
        }
        return document;
    }

    /**
     * @param dbName the name of the database
     * @return the list of persons
     */
    default List<Y> fetch(final String dbName) {
        return find(fetchRoot(dbName));
    }

    /**
     * @param root the root document of the data set to search
     * @param idString the ID of the family
     * @return the family document
     */
    default Y find(final RootDocument root, final String idString) {
        return getRepository().findByRootAndString(root, idString);
    }

    /**
     * @param root the root document of the data set
     * @return the list of submitters
     */
    default List<Y> find(final RootDocument root) {
        final List<Y> all = new ArrayList<>();
        for (final Y document : getRepository().findAll(root)) {
            all.add(document);
        }
        all.sort(new GetStringComparator());
        for (final Y document : all) {
            System.out.println(document.getString());
        }
        return all;
    }
}
