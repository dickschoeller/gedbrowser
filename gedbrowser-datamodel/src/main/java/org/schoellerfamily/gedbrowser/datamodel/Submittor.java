package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.NameableVisitor;

/**
 * @author Dick Schoeller
 */
public final class Submittor extends AbstractSource implements Nameable {
    /**
     * Default constructor.
     */
    public Submittor() {
        super();
    }

    /**
     * @param parent parent object of this source
     * @param xref cross reference to this source
     */
    public Submittor(final GedObject parent, final ObjectId xref) {
        super(parent, xrefString(xref));
    }

    /**
     * Get the ID string from and ObjectId. Returns null on null input.
     *
     * @param xref an object ID
     * @return its string
     */
    private static String xrefString(final ObjectId xref) {
        if (xref == null) {
            return null;
        }
        return xref.getIdString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Name getName() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getNameAttribute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSurname() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getSurname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIndexName() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getIndexName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
