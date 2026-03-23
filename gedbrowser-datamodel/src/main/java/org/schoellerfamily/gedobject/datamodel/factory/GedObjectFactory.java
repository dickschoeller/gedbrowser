package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/**
 * @author Richard Schoeller
 */
public interface GedObjectFactory {

    /**
     * Factory method creates the appropriate GedObject from the provided strings.
     *
     * @param parent the parent GedObject
     * @param xref   an optional ID string
     * @param tag    the GEDCOM tag
     * @param tail   the rest of the line
     * @return the GedObject
     */
    GedObject create(GedObject parent, String xref, String tag, String tail);

    /**
     * Factory method creates the appropriate GedObject from the provided strings.
     *
     * @param parent   the parent GedObject
     * @param objectId an objectId
     * @param tag      the GEDCOM tag
     * @param tail     the rest of the line
     * @return the GedObject
     */
    GedObject create(GedObject parent, ObjectId objectId, String tag, String tail);

}
