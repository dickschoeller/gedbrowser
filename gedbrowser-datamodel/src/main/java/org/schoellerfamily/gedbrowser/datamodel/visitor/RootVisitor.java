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
 * @author Dick Schoeller
 */
public final class RootVisitor implements GedObjectVisitor {
    /** */
    private final List<Person> persons = new ArrayList<>();

//    /** */
//    private final List<Family> families = new ArrayList<>();

    /**
     * @return the persons found in the scanned root
     */
    public List<Person> getPersons() {
        return persons;
    }

//    /**
//     * @return the families found in the scanned root
//     */
//    public List<Family> getFamilies() {
//        return families;
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Family family) {
// Currently commented out.
// Keep around because we may want to gather these too.
//        families.add(family);
    }

    /**
     * Visit a Person. The collection of all Persons in the dataset is the
     * outcome of this algorithm.
     *
     * @see GedObjectVisitor#visit(Person)
     */
    @Override
    public void visit(final Person person) {
        persons.add(person);
    }

    /**
     * Visit the Root. From here we will look through the top level objects for
     * Persons.
     *
     * @see GedObjectVisitor#visit(Root)
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
