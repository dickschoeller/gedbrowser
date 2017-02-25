package org.schoellerfamily.gedbrowser.datamodel.navigator;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;

/**
 * @author Dick Schoeller
 */
public class PersonNavigator {
    /** */
    private final Person person;

    /** */
    private final PersonVisitor visitor;

    /**
     * @param person the person to navigate
     */
    public PersonNavigator(final Person person) {
        this.person = person;
        this.visitor = new PersonVisitor();
        person.accept(visitor);
    }

    /**
     * @return the person that is navigated
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Find the mother of this person.  If not found, return an unset Person.
     *
     * @return the mother.
     */
    public Person getMother() {
        return visitor.getMother();
    }

    /**
     * Find the father of this person.  If not found, return an unset Person.
     *
     * @return the father.
     */
    public Person getFather() {
        return visitor.getFather();
    }

    /**
     * Get the list of families that this person is a spouse in.
     *
     * @return the list of families
     */
    public List<Family> getFamilies() {
        return visitor.getFamilies();
    }

    /**
     * Get the list of all of the spouses of this person.
     *
     * @return the list of spouses.
     */
    public List<Person> getSpouses() {
        return visitor.getSpouses();
    }

    /**
     * Get the list of all of children of this person.
     *
     * @return the list of children
     */
    public List<Person> getChildren() {
        return visitor.getChildren();
    }

    /**
     * @return list of all families that this person is a child of
     */
    public List<Family> getFamiliesC() {
        return visitor.getFamiliesC();
    }
}
