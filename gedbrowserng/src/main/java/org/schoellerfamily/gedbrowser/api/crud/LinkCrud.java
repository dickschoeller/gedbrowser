package org.schoellerfamily.gedbrowser.api.crud;

import java.util.Iterator;
import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * @author Dick Schoeller
 */
public interface LinkCrud {
    /**
     * @return the associated file loader
     */
    GedDocumentFileLoader getLoader();

    /**
     * @return the associated document converter
     */
    GedObjectToGedDocumentMongoConverter getConverter();

    /**
     * @return the associated repository manager
     */
    RepositoryManagerMongo getRepositoryManager();

    /**
     * Check if this is a link to the source we are unlinking.
     *
     * @param attribute the attribute to examine
     * @param id the id to check against
     * @return true if matches
     */
    boolean isTheLinkWeAreLookingFor(ApiAttribute attribute, String id);

    /**
     * @return a new family CRUD object
     */
    default FamilyCrud familyCrud() {
        return new FamilyCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new head CRUD object
     */
    default HeadCrud headCrud() {
        return new HeadCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new note CRUD object
     */
    default NoteCrud noteCrud() {
        return new NoteCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new person CRUD object
     */
    default PersonCrud personCrud() {
        return new PersonCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new source CRUD object
     */
    default SourceCrud sourceCrud() {
        return new SourceCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new submission CRUD object
     */
    default SubmissionCrud submissionCrud() {
        return new SubmissionCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new submitter CRUD object
     */
    default SubmitterCrud submitterCrud() {
        return new SubmitterCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @param db the db to unlink from
     * @param id the ID to unlink
     */
    default void unlinkFrom(final String db, final String id) {
        unlinkFrom(db, id, familyCrud());
        unlinkFrom(db, id, noteCrud());
        unlinkFrom(db, id, personCrud());
        unlinkFrom(db, id, sourceCrud());
        unlinkFromHead(db, id);
    }

    /**
     * @param db the db to unlink from
     * @param id the ID to unlink
     */
    default void unlinkFromHead(final String db, final String id) {
        unlinkFrom(db, id, headCrud());
        unlinkFrom(db, id, submissionCrud());
        unlinkFrom(db, id, submitterCrud());
    }

    /**
     * @param <Z> the data type of the object we are removing the link from
     * @param db the database
     * @param id the ID of the thing to unlink
     * @param crud the CRUD object for the type we're unlink from
     */
    default <Z extends ApiObject> void unlinkFrom(final String db,
            final String id, final CrudOperations<?, ?, Z> crud) {
        final List<Z> objects = crud.readAll(db);
        for (final Z object : objects) {
            if (unlink(object, id)) {
                crud.updateOne(db, object.getString(), object);
            }
        }
    }

    /**
     * Recursive method to drill down into the attributes of an API object and
     * remove links to the identified sources.
     *
     * @param object the object we are examining
     * @param id the id of the source to unlink
     * @return true if something under here was unlinked
     */
    default boolean unlink(final ApiObject object, final String id) {
        boolean modified = false;
        final Iterator<ApiAttribute> i = object.getAttributes().iterator();
        while (i.hasNext()) {
            final ApiAttribute attribute = i.next();
            if (isTheLinkWeAreLookingFor(attribute, id)) {
                i.remove();
                modified = true;
            } else if (unlink(attribute, id)) {
                modified = true;
            }
        }
        return modified;
    }
}
