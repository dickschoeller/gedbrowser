package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.transformers.ApiModelToGedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.repository.CrudRepository;

/**
 * This interface contains default methods that implement the create
 * operations for the classes that declare implementing the interface.
 *
 * @author Dick Schoeller
 *
 * @param <X> the data model type we are manipulating
 * @param <Y> the DB type associated with the type X
 * @param <Z> the Api type associated with the type X
 */
@SuppressWarnings("PMD.CommentSize")
public interface CreateOperations<X extends GedObject,
            Y extends GedDocument<X>,
            Z extends ApiObject>
        extends Converter<Y, Z>, NewId<X, Y> {
    /**
     * @return the DB repository for this type
     */
    FindableDocument<X, Y> getRepository();

    /**
     * @return the converter
     */
    GedObjectToGedDocumentMongoConverter getConverter();

    /**
     * @author Dick Schoeller
     *
     * @param <T> the data type that we are copying
     */
    public interface ApiCopier<T extends ApiObject> {
        /**
         * @param original the original object
         * @param id the different id
         * @return the copy
         */
        T copy(T original, String id);
    }

    /**
     * @param root the root of the db
     * @param in the requested object to create
     * @param copier implements type specific copying
     * @return the actual created object
     */
    default Z create(final RootDocument root, final Z in,
            final ApiCopier<Z> copier) {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(
                        new GedObjectBuilder(root.getGedObject()),
                        root.getGedObject());
        final String id = newId(root);
        final Z newObject = copier.copy(in, id);
        newObject.accept(visitor);
        @SuppressWarnings("unchecked")
        final X gob = (X) visitor.getGedObject();
        return convert(save(gob));
    }

    /**
     * Save a GedObject to the database.
     *
     * @param gob the GedObject to save
     * @return the new document
     */
    @SuppressWarnings("unchecked")
    default Y save(X gob) {
        Y document = (Y) getConverter().createGedDocument(gob);
        try {
            final FindableDocument<X, Y> repo = getRepository();
            Y oldDoc = repo.findByFileAndString(
                    gob.getFilename(), gob.getString());
            if (oldDoc != null) {
                document.setIdString(oldDoc.getIdString());
            }
            return ((CrudRepository<Y, String>) repo)
                    .save(document);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param db the name of the db to access
     * @param object the data for the object
     * @return the object as created
     */
    Z createOne(String db, Z object);
}
