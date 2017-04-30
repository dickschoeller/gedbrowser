package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.AbstractGedObjectFactory.GedObjectFactory;

/**
 * @author Dick Schoeller
 *
 */
public final class GedLineToGedObject {
    /** */
    private final GedObjectFactory factory = new GedObjectFactory();

    /**
     * @param line the input gedline
     * @return the output gedobject
     */
    public Root create(final AbstractGedLine line) {
        final GedObjectCreatorVisitor visitor =
                new GedObjectCreatorVisitor(factory, null);
        line.accept(visitor);
        return (Root) visitor.getGedObject();
    }
}
