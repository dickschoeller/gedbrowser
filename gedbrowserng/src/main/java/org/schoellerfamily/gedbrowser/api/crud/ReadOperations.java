package org.schoellerfamily.gedbrowser.api.crud;

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
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * This interface contains default methods that implement the read
 * operations for the classes that declare implementing the interface.
 *
 * @author Dick Schoeller
 *
 * @param <X> the data model type we are manipulating
 * @param <Y> the DB type associated with the type X
 * @param <Z> the Api type associated with the type X
 */
@SuppressWarnings("PMD.CommentSize")
public interface ReadOperations <X extends GedObject,
    Y extends GedDocument<X>, Z extends ApiObject> {
    /**
     * @return the DB repository for this type
     */
    FindableDocument<X, Y> getRepository();

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
    default RootDocument readRoot(final String dbName) {
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
    default Y read(final String dbName, final String idString) {
        final Y document = read(readRoot(dbName), idString);
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
    default List<Y> read(final String dbName) {
        return read(readRoot(dbName));
    }

    /**
     * @param root the root document of the data set to search
     * @param idString the ID of the object
     * @return the family document
     */
    default Y read(final RootDocument root, final String idString) {
        return getRepository().findByRootAndString(root, idString);
    }

    /**
     * @param root the root document of the data set
     * @return the list of objects of the requested type
     */
    default List<Y> read(final RootDocument root) {
        final List<Y> all = new ArrayList<>();
        for (final Y document : getRepository().findAll(root)) {
            all.add(document);
        }
        all.sort(new GetStringComparator());
        return all;
    }

    /**
     * @param db the dataset name
     * @return the list of API objects
     */
    List<Z> readAll(String db);
}
