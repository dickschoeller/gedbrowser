package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.NameableVisitor;

/**
 * @author Dick Schoeller
 */
public final class Person extends GedObject implements Nameable {
    /**
     * Default constructor.
     */
    public Person() {
        super();
    }

    /**
     * @param parent parent object of this person
     * @param xref cross reference to this person
     */
    public Person(final GedObject parent, final ObjectId xref) {
        super(parent, xref.getIdString());
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
    public Name getName() {
        final NameableVisitor visitor = new NameableVisitor();
        this.accept(visitor);
        return visitor.getNameAttribute();
    }

    /**
     * @return the first letter of the surname (or a question mark)
     */
    public String getSurnameLetter() {
        return getSurname().substring(0, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
