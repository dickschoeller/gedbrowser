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

    /**
     * {@inheritDoc}
     */
    @Override
    default Root getRoot() {
        return getAttributeBuilder().getRoot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Attribute createAttribute() {
        return getAttributeBuilder().createAttribute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Attribute createAttribute(final GedObject ged, final String string) {
        return getAttributeBuilder().createAttribute(ged, string);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Attribute createAttribute(final GedObject ged, final String string,
            final String tail) {
        return getAttributeBuilder().createAttribute(ged, string, tail);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Place addPlaceToEvent(final Attribute event, final String placeName) {
        return getAttributeBuilder().addPlaceToEvent(event, placeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Date addDateToGedObject(final GedObject gob, final String string) {
        return getAttributeBuilder().addDateToGedObject(gob, string);
    }
}
