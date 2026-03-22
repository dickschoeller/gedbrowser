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
 * Defines the contract for update operations.
 *
 * @author Richard Schoeller
 *
 * @param <X> the data model type we are manipulating
 *
 * @param <Y> the DB type associated with the type X
 *
 * @param <Z> the Api type associated with the type X
 */
@SuppressWarnings("PMD.CommentSize")
public interface UpdateOperations<X extends GedObject,
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
     * Implements updating an object that already exists.
     *
     * @param root the root of the db
     * @param in the requested object to update
     * @return the actual created object
     */
    default Z update(final RootDocument root, final Z in) {
        final ApiModelToGedObjectVisitor visitor =
            new ApiModelToGedObjectVisitor(
                new GedObjectBuilder(root.getGedObject()),
                root.getGedObject());
        in.accept(visitor);
        @SuppressWarnings("unchecked")
        final X gob = (X) visitor.getGedObject();
        return convert(update(gob));
    }

    /**
     * Save a GedObject to the database.
     *
     * @param gob the GedObject to update
     * @return the updated document
     */
    @SuppressWarnings("unchecked")
    default Y update(final X gob) {
        final Y document = (Y) getConverter().createGedDocument(gob);
        try {
            final FindableDocument<X, Y> repo = getRepository();
            final Y oldDoc = repo.findByFileAndString(gob.getFilename(), gob.getString());
            document.setIdString(oldDoc.getIdString());
            return ((CrudRepository<Y, String>) repo).save(document);
        } catch (Exception _) {
            return null;
        }
    }

    /**
     * @param db the dataset
     * @param id the ID of the thing to update
     * @param object the new data
     * @return the updated object
     */
    Z updateOne(String db, String id, Z object);
}
