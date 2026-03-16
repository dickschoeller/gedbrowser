package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.visitor.NameableVisitor;

/**
 * @author Dick Schoeller
 */
public final class Person extends GedObject implements Nameable {
    /**
     * Creates a new Person.
     *
     */
    public Person() {
        super();
    }

    /**
     * Creates a new Person.
     *
     * @param parent the parent
     * @param xref the xref
     */
    public Person(final GedObject parent, final ObjectId xref) {
        super(parent, xref.getIdString());
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
     * Gets the surname letter.
     *
     * @return the surname letter
     */
    public String getSurnameLetter() {
        return getSurname().substring(0, 1);
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
