package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;

/**
 * @author Dick Schoeller
 */
public final class NameableVisitor implements GedObjectVisitor {
    /**
     * Hold the name for the person.
     */
    private Name thename;

    /**
     * Hold the visited nameable if there is one.
     */
    private GedObject visitedNameable;

    /**
     * @return the name of the person
     */
    public Name getNameAttribute() {
        if (thename == null) {
            return new Name(visitedNameable);
        }
        return thename;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return getNameAttribute().getSurname();
    }

    /**
     * @return the name to use in indices
     */
    public String getIndexName() {
        return getNameAttribute().getIndexName();
    }

    /**
     * Visit a Name. The first name encountered is collected. All others are
     * ignored.
     *
     * @see GedObjectVisitor#visit(Name)
     */
    @Override
    public void visit(final Name name) {
        if (this.thename != null) {
            // We always take the first listed name.
            return;
        }
        this.thename = name;
    }

    /**
     * Visit a Person. This is could be primary focus of the visitation.
     * From here, interesting information is gathered from the attributes.
     *
     * @see GedObjectVisitor#visit(Person)
     */
    @Override
    public void visit(final Person person) {
        visitedNameable = person;
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
        }
    }

    /**
     * Visit a Submitter. This is could be primary focus of the visitation.
     * From here, interesting information is gathered from the attributes.
     *
     * @see GedObjectVisitor#visit(Submitter)
     */
    @Override
    public void visit(final Submitter submitter) {
        visitedNameable = submitter;
        for (final GedObject gob : submitter.getAttributes()) {
            gob.accept(this);
        }
    }
}
