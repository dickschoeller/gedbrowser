package org.schoellerfamily.gedbrowser.datamodel.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * Visits root elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
public final class RootVisitor implements GedObjectVisitor {
    /** */
    private final List<Person> persons = new ArrayList<>();

    /**
     * Gets the persons.
     *
     * @return the persons
     */
    public List<Person> getPersons() {
        return persons;
    }

    /**
     * Executes visit.
     *
     * @param family the family
     */
    @Override
    @SuppressWarnings({ "java:S125" })
    public void visit(final Family family) {
// Currently commented out.
// Keep around because we may want to gather these too.
//        families.add(family);
    }

    /**
     * Executes visit.
     *
     * @param person the person
     */
    @Override
    public void visit(final Person person) {
        persons.add(person);
    }

    /**
     * Executes visit.
     *
     * @param root the root
     */
    @Override
    public void visit(final Root root) {
        final Map<String, GedObject> objectMap = root.getObjects();
        final Collection<GedObject> objects = objectMap.values();
        for (final GedObject gob : objects) {
            gob.accept(this);
        }
    }
}
