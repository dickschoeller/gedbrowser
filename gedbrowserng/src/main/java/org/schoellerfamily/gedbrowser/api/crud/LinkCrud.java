package org.schoellerfamily.gedbrowser.api.crud;

import java.util.Iterator;
import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;

/**
 * @author Dick Schoeller
 */
public interface LinkCrud extends NewCruds {

    /**
     * Check if this is a link to the source we are unlinking.
     *
     * @param attribute the attribute to examine
     * @param id the id to check against
     * @return true if matches
     */
    boolean isTheLinkWeAreLookingFor(ApiAttribute attribute, String id);

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
