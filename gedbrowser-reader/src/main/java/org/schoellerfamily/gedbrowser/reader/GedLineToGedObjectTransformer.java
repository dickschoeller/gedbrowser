package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.Root;

import org.schoellerfamily.gedobject.datamodel.factory.GedObjectStarterFactory;
import org.springframework.stereotype.Component;



/**
 * Represents ged line to ged object transformer.
 *
 * @author Richard Schoeller
 */
@Component
public final class GedLineToGedObjectTransformer {
    /** */
    private final GedObjectStarterFactory factory = new GedObjectStarterFactory();

    /**
     * Executes create.
     *
     * @param line the line
     * @return the resulting root
     */
    public Root create(final AbstractGedLine line) {
        final GedLineToGedObjectVisitor visitor =
                new GedLineToGedObjectVisitor(factory, null);
        line.accept(visitor);
        return (Root) visitor.getGedObject();
    }
}
