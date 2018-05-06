package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;

/**
 * This interface wraps up the interfaces for the CRUD operations into a
 * single package. These interfaces contain default methods that implement
 * their respective part for the classes that declare implementing the
 * interface.
 *
 * @author Dick Schoeller
 *
 * @param <X> the data model type we are manipulating
 * @param <Y> the DB type associated with the type X
 * @param <Z> the Api type associated with the type X
 */
@SuppressWarnings("PMD.CommentSize")
public interface CrudOperations<X extends GedObject,
        Y extends GedDocument<X>,
        Z extends ApiObject>
    extends CreateOperations<X, Y, Z>,
        ReadOperations<X, Y, Z>,
        UpdateOperations<X, Y, Z>,
        DeleteOperations<X, Y, Z> {

}
