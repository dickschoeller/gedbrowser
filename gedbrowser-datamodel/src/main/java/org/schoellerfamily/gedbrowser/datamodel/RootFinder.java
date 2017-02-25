package org.schoellerfamily.gedbrowser.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Dick Schoeller
 */
public final class RootFinder implements FinderStrategy {
    /**
     * Constructor.
     */
    RootFinder() {
        // Empty constructor.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GedObject find(final GedObject owner, final String str) {
        final Root root = (Root) owner;
        return root.getObjects().get(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final GedObject owner, final GedObject gob) {
        final Root root = (Root) owner;
        root.extraInsert(gob);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends GedObject> T find(
            final GedObject owner, final String str, final Class<T> clazz) {
        return clazz.cast(owner.find(str));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilename(final GedObject owner) {
        final Root root = (Root) owner;
        return root.getTheFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDbName(final GedObject owner) {
        final Root root = (Root) owner;
        return root.getTheDbName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Person> findBySurname(final GedObject owner,
            final String surname) {
        final Root root = (Root) owner;
        final Map<String, GedObject> objectMap = root.getObjects();
        final Collection<GedObject> objects = objectMap.values();
        final List<Person> matches = new ArrayList<>();
        for (final GedObject object : objects) {
            if (object instanceof Person) {
                final Person person = (Person) object;
                final String personSurname = person.getSurname();
                if (personSurname.equals(surname)) {
                    matches.add((Person) object);
                }
            }
        }

        Collections.sort(matches, new PersonComparator());

        return matches;
    }

    /**
     * @author Dick Schoeller
     */
    public static final class PersonComparator implements Comparator<Person>,
            Serializable {
        /** */
        private static final long serialVersionUID = 3;

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(final Person arg0,
                final Person arg1) {
            final int nameComparison =
                    arg0.getIndexName().compareTo(arg1.getIndexName());
            if (nameComparison != 0) {
                return nameComparison;
            }
            // If the names are the same, use the sort date (approximates on
            // birth).
            GetDateVisitor visitor0 = new GetDateVisitor("Birth");
            arg0.accept(visitor0);
            final String sortDate0 = visitor0.getSortDate();
            GetDateVisitor visitor1 = new GetDateVisitor("Birth");
            arg1.accept(visitor1);
            final String sortDate1 = visitor1.getSortDate();
            final int birthComparison =
                    sortDate0.compareTo(sortDate1);
            if (birthComparison != 0) {
                return birthComparison;
            }
            // If the dates are the same, use the I number.
            return arg0.getString().compareTo(arg1.getString());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findBySurnamesBeginWith(final GedObject owner,
            final String beginsWith) {
        final Root root = (Root) owner;
        final Map<String, GedObject> objectMap = root.getObjects();
        final Collection<GedObject> objects = objectMap.values();
        final Set<String> matches = new TreeSet<>();
        for (final GedObject object : objects) {
            if (object instanceof Person) {
                final Person person = (Person) object;
                final String personSurname = person.getSurname();
                if (personSurname.startsWith(beginsWith)) {
                    matches.add(personSurname);
                }
            }
        }
        return matches;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findSurnameInitialLetters(final GedObject owner) {
        final Root root = (Root) owner;
        final Map<String, GedObject> objectMap = root.getObjects();
        final Collection<GedObject> objects = objectMap.values();
        final Set<String> matches = new TreeSet<>();
        for (final GedObject object : objects) {
            if (object instanceof Person) {
                final Person person = (Person) object;
                final String firstLetter = person.getSurname().substring(0, 1);
                matches.add(firstLetter);
            }
        }
        return matches;
    }
}
