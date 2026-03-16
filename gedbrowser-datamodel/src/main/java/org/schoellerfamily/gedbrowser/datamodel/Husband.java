package org.schoellerfamily.gedbrowser.datamodel;

import lombok.NoArgsConstructor;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents husband in the domain model.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class Husband extends AbstractLink implements Spouse {

    /**
     * Creates a new Husband.
     *
     * @param parent the parent
     * @param string the string
     */
    public Husband(final GedObject parent, final String string,
            final ObjectId xref) {
        super(parent, string, xref);
    }

    /**
     * Get the person that this object refers to. If not found return an unset
     *
     * @return the father
     */
    public Person getFather() {
        if (!isSet()) {
            return new Person();
        }
        final Person father = (Person) find(getToString());
        if (father == null) {
            return new Person();
        } else {
            return father;
        }
    }

    /**
     * Gets the spouse.
     *
     * @return the spouse
     */
    @Override
    public Person getSpouse() {
        return (Person) find(getToString());
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
