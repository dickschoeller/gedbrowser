package org.schoellerfamily.gedbrowser.datamodel.finder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
        final List<Person> matches = new ArrayList<>();
        for (final Person person : visitor.getPersons()) {
            final String personSurname = person.getSurname();
            if (personSurname.equals(surname)) {
                matches.add(person);
            }
        }

        Collections.sort(matches, new PersonComparator());

        return matches;
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
        final Set<String> matches = new TreeSet<>();
        for (final Person person : visitor.getPersons()) {
            final String personSurname = person.getSurname();
            if (personSurname.startsWith(beginsWith)) {
                matches.add(personSurname);
            }
        }
        return matches;
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
        final Set<String> matches = new TreeSet<>();
        for (final Person person : visitor.getPersons()) {
            final String firstLetter = person.getSurname().substring(0, 1);
            matches.add(firstLetter);
        }
        return matches;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends GedObject> Collection<T> find(final FinderObject owner,
            final Class<T> clazz) {
        final Root root = (Root) owner;
        final Collection<T> results = new ArrayList<>();
        for (final GedObject gob : root.getObjects().values()) {
            if (gob.getClass().equals(clazz)) {
                results.add(clazz.cast(gob));
            }
        }
        return results;
    }
}
