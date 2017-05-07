package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public interface AttributeBuilder {
    /**
     * Get the root object of the data set.
     *
     * @return the root object
     */
    Root getRoot();

    /**
     * Create an empty attribute.
     *
     * @return the attribute
     */
    Attribute createAttribute();

    /**
     * Create an attribute.
     *
     * @param ged parent
     * @param string attribute type string
     * @return the attribute
     */
    Attribute createAttribute(GedObject ged, String string);

    /**
     * Create an attribute.
     *
     * @param ged parent
     * @param string attribute type string
     * @param tail attribute details
     * @return the attribute
     */
    Attribute createAttribute(GedObject ged, String string, String tail);

    /**
     * Create a place with the given name and add it to the given event.
     *
     * @param event the event
     * @param placeName the place name
     * @return the place
     */
    Place addPlaceToEvent(Attribute event, String placeName);

    /**
     * @param gob the GedObject to add the name to
     * @param string the date string
     * @return the new date object
     */
    Date addDateToGedObject(GedObject gob, String string);
}
