package org.schoellerfamily.gedbrowser.writer.creator;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;

/**
 * @author Dick Schoeller
 */
public interface AttributeLineVisitor extends GedObjectLineVisitor {

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Attribute attribute) {
        final GedWriterLine line = new GedWriterLine(getLevel(), attribute,
                getLevel() + " " + mapTag(attribute.getString()) + tail(attribute));
        getLines().add(line);
        contAndConc(attribute);
        handleChildren(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Name name) {
        final GedWriterLine line = new GedWriterLine(getLevel(), name,
                getLevel() + " NAME " + name.getString());
        getLines().add(line);
        handleChildren(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Place place) {
        final GedWriterLine line = new GedWriterLine(getLevel(), place,
                getLevel() + " PLAC " + place.getString());
        getLines().add(line);
        handleChildren(place);
    }

}
