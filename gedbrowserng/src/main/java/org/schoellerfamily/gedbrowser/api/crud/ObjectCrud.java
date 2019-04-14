package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;

/**
 * @author Dick Scholler
 * @param <Z> the type of object operated on
 */
public interface ObjectCrud <Z extends ApiObject> {
    /**
     * @param db the name of the db to access
     * @param object the data for the object
     * @return the object as created
     */
    Z createOne(String db, Z object);

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

    /**
     * @param db the dataset
     * @param id the ID of the thing to update
     * @param object the new data
     * @return the updated object
     */
    Z updateOne(String db, String id, Z object);

    /**
     * @param db the name of the db to access
     * @param id the ID of the object
     * @return the deleted object
     */
    Z deleteOne(String db, String id);
}
