package org.schoellerfamily.gedbrowser.datamodel.finder;

import java.util.Collection;
import java.util.TreeSet;

import org.schoellerfamily.gedbrowser.datamodel.FinderObject;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonComparator;
import org.schoellerfamily.gedbrowser.datamodel.visitor.RootVisitor;

/**
 * @author Dick Schoeller
 */
public final class RootFinder implements FinderStrategy {
    /**
     * Creates a new RootFinder.
     *
     */
    public RootFinder() {
        // Empty constructor.
    }

    /**
     * Executes find.
     *
     * @param owner the owner
     * @param str the str
     * @return the resulting ged object
     */
    @Override
    public GedObject find(final FinderObject owner, final String str) {
        final Root root = (Root) owner;
        return root.getObjects().get(str);
    }

    /**
     * Executes insert.
     *
     * @param owner the owner
     * @param gob the gob
     */
    @Override
    public void insert(final FinderObject owner, final FinderObject gob) {
        final Root root = (Root) owner;
        root.extraInsert(gob);
    }

    /**
     * Returns the t.
     *
     * @param owner the owner
     * @param str the str
     * @param clazz the clazz
     * @return the resulting t
     */
    @Override
    public <T extends GedObject> T find(
            final FinderObject owner, final String str, final Class<T> clazz) {
        return clazz.cast(owner.find(str));
    }

    /**
     * Returns the filename.
     *
     * @param owner the owner
     * @return the filename
     */
    @Override
    public String getFilename(final FinderObject owner) {
        final Root root = (Root) owner;
        return root.getTheFilename();
    }

    /**
     * Returns the db name.
     *
     * @param owner the owner
     * @return the db name
     */
    @Override
    public String getDbName(final FinderObject owner) {
        final Root root = (Root) owner;
        return root.getTheDbName();
    }

    /**
     * Finds the by surname.
     *
     * @param owner the owner
     * @param surname the surname to use
     * @return the resulting collection
     */
    @Override
    public Collection<Person> findBySurname(final FinderObject owner,
            final String surname) {
        final Root root = (Root) owner;
        final RootVisitor visitor = new RootVisitor();
        root.accept(visitor);
        return visitor.getPersons().stream()
            .filter(person -> person.getSurname().equals(surname))
            .sorted(new PersonComparator())
            .toList();
    }

    /**
     * Finds the by surnames begin with.
     *
     * @param owner the owner
     * @param beginsWith the begins with
     * @return the resulting collection
     */
    @Override
    public Collection<String> findBySurnamesBeginWith(final FinderObject owner,
            final String beginsWith) {
        final Root root = (Root) owner;
        final RootVisitor visitor = new RootVisitor();
        root.accept(visitor);
        return visitor.getPersons().stream()
            .map(Person::getSurname)
            .filter(personSurname -> personSurname.startsWith(beginsWith))
            .collect(java.util.stream.Collectors.toCollection(TreeSet::new));
    }

    /**
     * Finds the surname initial letters.
     *
     * @param owner the owner
     * @return the resulting collection
     */
    @Override
    public Collection<String> findSurnameInitialLetters(
            final FinderObject owner) {
        final Root root = (Root) owner;
        final RootVisitor visitor = new RootVisitor();
        root.accept(visitor);
        return visitor.getPersons().stream()
            .map(person -> person.getSurname().substring(0, 1))
            .collect(java.util.stream.Collectors.toCollection(TreeSet::new));
    }

    /**
     * Executes find.
     *
     * @param owner the owner
     * @param clazz the clazz
     * @return the resulting collection
     */
    @Override
    public <T extends GedObject> Collection<T> find(final FinderObject owner,
            final Class<T> clazz) {
        final Root root = (Root) owner;
        return root.getObjects().values().stream()
            .filter(gob -> gob.getClass().equals(clazz))
            .map(clazz::cast)
            .toList();
    }
}
