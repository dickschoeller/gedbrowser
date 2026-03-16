package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.NameableVisitor;

/**
 * @author Dick Schoeller
 */
public final class Submitter extends AbstractSource implements Nameable {
    /**
     * Default constructor.
     */
    public Submitter() {
        super();
    }

    /**
     * @param parent parent object of this source
     * @param xref cross reference to this source
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

    @Override
    public Name getName() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getNameAttribute();
    }

    @Override
    public String getSurname() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getSurname();
    }

    @Override
    public String getIndexName() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getIndexName();
    }

    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
