package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public interface AttributeBuilderFacade extends AttributeBuilder {
    /**
     * @return the attribute builder
     */
    AttributeBuilder getAttributeBuilder();

    @Override
    default Root getRoot() {
        return getAttributeBuilder().getRoot();
    }

    @Override
    default Attribute createAttribute() {
        return getAttributeBuilder().createAttribute();
    }

    @Override
    default Attribute createAttribute(final GedObject ged, final String string) {
        return getAttributeBuilder().createAttribute(ged, string);
    }

    @Override
    default Attribute createAttribute(final GedObject ged, final String string,
            final String tail) {
        return getAttributeBuilder().createAttribute(ged, string, tail);
    }

    @Override
    default Place addPlaceToEvent(final Attribute event, final String placeName) {
        return getAttributeBuilder().addPlaceToEvent(event, placeName);
    }

    @Override
    default Date addDateToGedObject(final GedObject gob, final String string) {
        return getAttributeBuilder().addDateToGedObject(gob, string);
    }
}
