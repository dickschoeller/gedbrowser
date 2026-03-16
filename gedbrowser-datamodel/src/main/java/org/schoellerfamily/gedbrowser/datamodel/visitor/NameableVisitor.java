package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;

/**
 * Visits nameable elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
public final class NameableVisitor implements GedObjectVisitor {
    /**
     * Creates a new NameableVisitor.
     */
    public NameableVisitor() {
    }

    /**
     * Hold the name for the person.
     */
    private Name thename;

    /**
     * Hold the visited nameable if there is one.
     */
    private GedObject visitedNameable;

    /**
     * Gets the name attribute.
     *
     * @return the name attribute
     */
    public Name getNameAttribute() {
        if (thename == null) {
            return new Name(visitedNameable);
        }
        return thename;
    }

    /**
     * Gets the surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return getNameAttribute().getSurname();
    }

    /**
     * Gets the index name.
     *
     * @return the index name
     */
    public String getIndexName() {
        return getNameAttribute().getIndexName();
    }

    /**
     * Executes visit.
     *
     * @param name the name to use
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
     * Executes visit.
     *
     * @param person the person
     */
    @Override
    public void visit(final Person person) {
        visitedNameable = person;
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
        }
    }

    /**
     * Executes visit.
     *
     * @param submitter the submitter
     */
    @Override
    public void visit(final Submitter submitter) {
        visitedNameable = submitter;
        for (final GedObject gob : submitter.getAttributes()) {
            gob.accept(this);
        }
    }
}
