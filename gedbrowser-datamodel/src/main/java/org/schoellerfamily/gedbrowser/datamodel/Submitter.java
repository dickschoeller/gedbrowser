package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.NameableVisitor;

/**
 * Represents submitter in the domain model.
 *
 * @author Richard Schoeller
 */
public final class Submitter extends AbstractSource implements Nameable {
    /**
     * Creates a new Submitter.
     */
    public Submitter() {
        super();
    }

    /**
     * Creates a new Submitter.
     *
     * @param parent the parent
     * @param xref the xref
     */
    public Submitter(final GedObject parent, final ObjectId xref) {
        super(parent, xrefString(xref));
    }

    private static String xrefString(final ObjectId xref) {
        if (xref == null) {
            return null;
        }
        return xref.getIdString();
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public Name getName() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getNameAttribute();
    }

    /**
     * Gets the surname.
     *
     * @return the surname
     */
    @Override
    public String getSurname() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getSurname();
    }

    /**
     * Gets the index name.
     *
     * @return the index name
     */
    @Override
    public String getIndexName() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getIndexName();
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
