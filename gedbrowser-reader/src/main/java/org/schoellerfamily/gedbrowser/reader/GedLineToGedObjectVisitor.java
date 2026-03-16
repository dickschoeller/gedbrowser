package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedobject.datamodel.factory.AbstractGedObjectFactory.GedObjectFactory;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
public class GedLineToGedObjectVisitor implements GedLineVisitor {
    /** */
    private final GedObjectFactory factory;

    /**
     * This controls hierarchy.
     */
    private final AbstractGedLine parent;

    /**
     * This contains the root of the result.
     */
    private GedObject gedObject;

    /**
     * Gets the ged object.
     *
     * @return the ged object
     */
    public GedObject getGedObject() {
        return gedObject;
    }

    /**
     * Executes visit.
     *
     * @param gedline the gedline
     */
    @Override
    public void visit(final GedLine gedline) {
        gedObject = factory.create(parentGob(), gedline.getXref(),
                gedline.getTag(), gedline.getTail());

        populateGob(gedObject, gedline);
    }

    /**
     * Executes visit.
     *
     * @param gedfile the gedfile to use
     */
    @Override
    public void visit(final GedFile gedfile) {
        final Root root;
        if (gedfile.getFinder() == null) {
            root = new Root();
        } else {
            root = new Root(gedfile.getFinder());
        }
        root.setFilename(gedfile.getFilename());
        root.setDbName(gedfile.getDbName());
        populateGob(root, gedfile);
        gedObject = root;
    }

    private GedObject parentGob() {
        if (parent == null) {
            return null;
        }
        return parent.getGedObject();
    }

    private void populateGob(final GedObject gob,
            final AbstractGedLine current) {
        if (gob != null) {
            current.setGedObject(gob);

            createChildren(gob, current);
        }
    }

    private void createChildren(final GedObject gob,
            final AbstractGedLine current) {
        for (final AbstractGedLine child : current.getChildren()) {
            final GedLineToGedObjectVisitor visitor = createVisitor(current);
            child.accept(visitor);
            gob.insert(visitor.getGedObject());
        }
    }

    private GedLineToGedObjectVisitor createVisitor(
            final AbstractGedLine current) {
        return new GedLineToGedObjectVisitor(factory, current);
    }
}
