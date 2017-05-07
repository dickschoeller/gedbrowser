package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 *
 */
public class AttributeBuilderImpl implements AttributeBuilder {
    /** */
    private final GedObjectBuilder gedObjectBuilder;

    /**
     * Constructor.
     *
     * @param gedObjectBuilder the containing builder
     */
    public AttributeBuilderImpl(final GedObjectBuilder gedObjectBuilder) {
        this.gedObjectBuilder = gedObjectBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Root getRoot() {
        return gedObjectBuilder.getRoot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Attribute createAttribute() {
        return new Attribute();
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public Place addPlaceToEvent(final Attribute event,
            final String placeName) {
        final Place place = new Place(event, placeName);
        event.insert(place);
        return place;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date addDateToGedObject(final GedObject gob, final String string) {
        final Date date = new Date(gob, string);
        gob.insert(date);
        return date;
    }
}
