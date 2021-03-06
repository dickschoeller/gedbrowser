package org.schoellerfamily.gedbrowser.api.crud;

import java.util.Locale;

import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * This interface contains default methods that implement the delete
 * operations for the classes that declare implementing the interface.
 *
 * @author Dick Schoeller
 *
 * @param <X> the data model type we are manipulating
 * @param <Y> the DB type associated with the type X
 * @param <Z> the Api type associated with the type X
 */
@SuppressWarnings("PMD.CommentSize")
public interface DeleteOperations<X extends GedObject,
            Y extends GedDocument<X>,
            Z extends ApiObject>
        extends Converter<Y, Z> {
    /**
     * @return the DB repository for this type
     */
    FindableDocument<X, Y> getRepository();

    /**
     * @return the converter
     */
    GedObjectToGedDocumentMongoConverter getConverter();

    /**
     * @return the data model class
     */
    Class<X> getGedClass();

    /**
     * @param root the root of the db
     * @param id the ID of the object to delete
     * @return the deleted object
     */
    @SuppressWarnings("unchecked")
    default Z delete(final RootDocument root, final String id) {
        final FindableDocument<X, Y> repo = getRepository();
        Y oldDoc = repo.findByRootAndString(root, id);
        if (oldDoc == null) {
            final String type =
                    getGedClass().getSimpleName().toLowerCase(Locale.ENGLISH);
            throw new ObjectNotFoundException(
                    "Object " + id + " of type " + type + " not found",
                    type, id, root.getDbName());
        }
        ((CrudRepository<Y, String>) repo).delete(oldDoc);
        return convert(oldDoc);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the object
     * @return the deleted object
     */
    Z deleteOne(String db, String id);
}
