package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public interface GedObjectCreator {

    /**
     * Create the GedObject for this line.
     *
     * @param parentLine the parent GedLine.
     * @return the GedObject
     */
    default GedObject createGedObject(final AbstractGedLine parentLine) {
        GedObject parentGob;
        if (parentLine == null) {
            parentGob = null;
        } else {
            parentGob = parentLine.getGedObject();
        }

        final GedObject gob = createGedObject(parentGob);
        if (gob == null) {
            return null;
        }

        setGedObject(gob);

        createChildren(gob);

        return gob;
    }

    /**
     * Sets the GedObject associated with this line.
     *
     * @param gedObject the GedObject
     */
    void setGedObject(GedObject gedObject);

    /**
     * Create the GedObjects for the child lines.
     *
     * @param subObject the GedObject whose children are to be created.
     */
    void createChildren(GedObject subObject);

    /**
     * Create a GedObject for this line.
     *
     * @param parentGedObj the parent GedObject
     * @return the GedObject
     */
    GedObject createGedObject(GedObject parentGedObj);
}
