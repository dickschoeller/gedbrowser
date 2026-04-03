package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Defines the contract for ged object holder.
 *
 * @author Richard Schoeller
 */
public interface GedObjectHolder {
    /**
     * Sets the GedObject associated with this line.
     *
     * @param gedObject the GedObject
     */
    void setGedObject(GedObject gedObject);

    /**
     * Gets the GedObject associated with this line.
     *
     * @return the GedObject
     */
    GedObject getGedObject();
}
