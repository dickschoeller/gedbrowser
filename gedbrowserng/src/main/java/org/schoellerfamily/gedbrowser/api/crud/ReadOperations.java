package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;
import java.util.Locale;

import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * This interface contains default methods that implement the read operations
 * for the classes that declare implementing the interface.
 *
 * @author Dick Schoeller
 *
 * @param <X> the data model type we are manipulating
 * @param <Y> the DB type associated with the type X
 * @param <Z> the Api type associated with the type X
 */
@SuppressWarnings("PMD.CommentSize")
public interface ReadOperations<
        X extends GedObject,
        Y extends GedDocument<X>,
        Z extends ApiObject> {
    /**
     * @return the DB repository for this type
     */
    FindableDocument<X, Y> getRepository();

    /**
     * @return the loader
     */
    GedObjectFileLoader getLoader();

    /**
     * @return the data model class
     */
    Class<X> getGedClass();

    /**
     * @param repositoryManager the repository manager
     * @param dbName the name of the database
     * @return the root object
     */
    default RootDocument readRoot(final RepositoryManagerMongo repositoryManager,
        final String dbName) {
        final RootDocument root = getLoader().loadDocument(repositoryManager, dbName);
        if (root == null) {
            throw new DataSetNotFoundException("Data set %s not found".formatted(dbName), dbName);
        }
        return root;
    }

    /**
     * Generic single item fetcher.
     *
     * @param repositoryManager the repository manager
     * @param dbName   the name of the database
     * @param idString the ID of the item to fetch
     * @return the found object
     */
    default Y read(final RepositoryManagerMongo repositoryManager, final String dbName,
        final String idString) {
        final Y document = read(readRoot(repositoryManager, dbName), idString);
        if (document == null) {
            final String type = getGedClass().getSimpleName().toLowerCase(Locale.ENGLISH);
            throw new ObjectNotFoundException(
                "Object %s of type %s not found".formatted(idString, type), type, idString, dbName);
        }
        return document;
    }

    /**
     * @param repositoryManager the repository manager
     * @param dbName the name of the database
     * @return the list of persons
     */
    default List<Y> read(final RepositoryManagerMongo repositoryManager, final String dbName) {
        return read(readRoot(repositoryManager, dbName));
    }

    /**
     * @param root     the root document of the data set to search
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
        try {
            final Iterable<Y> a = getRepository().findAll(root);
            return java.util.stream.StreamSupport.stream(a.spliterator(), false)
                .sorted(new GetStringComparator())
                .toList();
        } catch (RuntimeException e) {
            throw e;
        }
    }

    /**
     * @param db the dataset name
     * @return the list of API objects
     */
    List<Z> readAll(String db);

    /**
     * @param db the dataset name
     * @param id the ID of the object we want
     * @return the object
     */
    Z readOne(String db, String id);
}
