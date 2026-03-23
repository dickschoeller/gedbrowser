package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;

/**
 * Interface for classes that have a base ApiObject.
 */
public interface HasBaseObject {
    /**
     * Return the base object for the visitor chain.
     *
     * @return the base ApiObject
     */
    ApiObject getBaseObject();
}
