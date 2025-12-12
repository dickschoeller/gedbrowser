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
     * Constructor.
     */
    public RootFinder() {
        // Empty constructor.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GedObject find(final FinderObject owner, final String str) {
        final Root root = (Root) owner;
        return root.getObjects().get(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final FinderObject owner, final FinderObject gob) {
        final Root root = (Root) owner;
        root.extraInsert(gob);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends GedObject> T find(
            final FinderObject owner, final String str, final Class<T> clazz) {
        return clazz.cast(owner.find(str));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilename(final FinderObject owner) {
        final Root root = (Root) owner;
        return root.getTheFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDbName(final FinderObject owner) {
        final Root root = (Root) owner;
        return root.getTheDbName();
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
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
