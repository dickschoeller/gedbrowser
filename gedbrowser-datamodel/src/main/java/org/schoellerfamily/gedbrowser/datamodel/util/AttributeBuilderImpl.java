package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * Represents attribute builder impl in the domain model.
 *
 * @author Richard Schoeller
 */
public final class AttributeBuilderImpl implements AttributeBuilder {
    /** */
    private final GedObjectBuilder gedObjectBuilder;

    /**
     * Creates a new AttributeBuilderImpl.
     *
     * @param gedObjectBuilder the ged object builder
     */
    public AttributeBuilderImpl(final GedObjectBuilder gedObjectBuilder) {
        this.gedObjectBuilder = gedObjectBuilder;
    }

    /**
     * Gets the root.
     *
     * @return the root
     */
    @Override
    public Root getRoot() {
        return gedObjectBuilder.getRoot();
    }

    /**
     * Creates the attribute.
     *
     * @return the resulting attribute
     */
    @Override
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * Creates the attribute.
     *
     * @param ged the ged
     * @param string the string
     * @return the resulting attribute
     */
    @Override
    public Attribute createAttribute(final GedObject ged, final String string) {
        if (ged == null || string == null) {
            return new Attribute();
        }
        final Attribute attribute = new Attribute(ged, string);
        ged.insert(attribute);
        return attribute;
    }

    /**
     * Creates the attribute.
     *
     * @param ged the ged
     * @param string the string
     * @param tail the tail
     * @return the resulting attribute
     */
    @Override
    public Attribute createAttribute(final GedObject ged, final String string,
            final String tail) {
        if (ged == null || string == null || tail == null) {
            return new Attribute();
        }
        final Attribute attribute = new Attribute(ged, string, tail);
        ged.insert(attribute);
        return attribute;
    }

    /**
     * Executes add place to event.
     *
     * @param event the event
     * @param placeName the place name to use
     * @return the resulting place
     */
    @Override
    public Place addPlaceToEvent(final Attribute event,
            final String placeName) {
        final Place place = new Place(event, placeName);
        event.insert(place);
        return place;
    }

    /**
     * Executes add date to ged object.
     *
     * @param gob the gob
     * @param string the string
     * @return the resulting date
     */
    @Override
    public Date addDateToGedObject(final GedObject gob, final String string) {
        final Date date = new Date(gob, string);
        gob.insert(date);
        return date;
    }
}
