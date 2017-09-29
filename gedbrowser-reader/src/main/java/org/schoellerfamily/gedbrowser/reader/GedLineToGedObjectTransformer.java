package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedobject.datamodel.factory.AbstractGedObjectFactory.GedObjectFactory;

/**
 * @author Dick Schoeller
 *
 */
public final class GedLineToGedObjectTransformer {
    /** */
    private final GedObjectFactory factory = new GedObjectFactory();

    /**
     * @param line the input gedline
     * @return the output gedobject
     */
    public Root create(final AbstractGedLine line) {
        final GedLineToGedObjectVisitor visitor =
                new GedLineToGedObjectVisitor(factory, null);
        line.accept(visitor);
        return (Root) visitor.getGedObject();
    }
}
