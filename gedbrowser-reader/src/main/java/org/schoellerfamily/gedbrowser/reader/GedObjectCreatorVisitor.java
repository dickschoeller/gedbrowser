package org.schoellerfamily.gedbrowser.reader;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.AbstractGedObjectFactory.GedObjectFactory;

/**
 * @author Dick Schoeller
 */
public class GedObjectCreatorVisitor implements GedLineVisitor {
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
     * Constructor.
     *
     * @param factory the object factory to use
     * @param parent the parent object in the hierarchy
     */
    public GedObjectCreatorVisitor(final GedObjectFactory factory,
            final AbstractGedLine parent) {
        this.factory = factory;
        this.parent = parent;
    }

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
        Root root;
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
        GedObject parentGob;
        if (parent == null) {
            parentGob = null;
        } else {
            parentGob = parent.getGedObject();
        }
        return parentGob;
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
            final GedObjectCreatorVisitor visitor = createVisitor(current);
            child.accept(visitor);
            gob.insert(visitor.getGedObject());
        }
    }

    /**
     * @param current current line, parent in the new visitor
     * @return the new visitor
     */
    private GedObjectCreatorVisitor createVisitor(
            final AbstractGedLine current) {
        return new GedObjectCreatorVisitor(factory, current);
    }
}
