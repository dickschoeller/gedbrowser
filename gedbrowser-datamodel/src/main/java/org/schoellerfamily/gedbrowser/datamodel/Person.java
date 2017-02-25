package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Person extends GedObject implements Nameable, FamilyLinkage {
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
        final Name name = getName();
        return name.getSurname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIndexName() {
        final Name name = getName();
        return name.getIndexName();
    }

    /**
     * Doesn't care about dates, just checks whether there is a death
     * attribute.
     *
     * @return true if a death attribute is found
     */
    public boolean hasDeathAttribute() {
        for (final GedObject gob : getAttributes()) {
            if (!(gob instanceof Attribute)) {
                continue;
            }
            final Attribute attr = (Attribute) gob;
            if ("Death".equals(attr.getString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Name getName() {
        return getNameAttribute();
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
