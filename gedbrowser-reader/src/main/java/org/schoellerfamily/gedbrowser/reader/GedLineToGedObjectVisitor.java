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
     * @return the gedObject that we created
     */
    public GedObject getGedObject() {
        return gedObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedLine gedline) {
        gedObject = factory.create(parentGob(), gedline.getXref(),
                gedline.getTag(), gedline.getTail());

        populateGob(gedObject, gedline);
    }

    /**
     * {@inheritDoc}
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

    /**
     * @return get the parent GedObject
     */
    private GedObject parentGob() {
        if (parent == null) {
            return null;
        }
        return parent.getGedObject();
    }

    /**
     * @param gob the gob being filled in
     * @param current the current line
     */
    private void populateGob(final GedObject gob,
            final AbstractGedLine current) {
        if (gob != null) {
            current.setGedObject(gob);

            createChildren(gob, current);
        }
    }

    /**
     * @param gob the gob being filled in
     * @param current the current line
     */
    private void createChildren(final GedObject gob,
            final AbstractGedLine current) {
        for (final AbstractGedLine child : current.getChildren()) {
            final GedLineToGedObjectVisitor visitor = createVisitor(current);
            child.accept(visitor);
            gob.insert(visitor.getGedObject());
        }
    }

    /**
     * @param current current line, parent in the new visitor
     * @return the new visitor
     */
    private GedLineToGedObjectVisitor createVisitor(
            final AbstractGedLine current) {
        return new GedLineToGedObjectVisitor(factory, current);
    }
}
