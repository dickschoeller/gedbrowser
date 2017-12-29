package org.schoellerfamily.gedbrowser.datamodel;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.schoellerfamily.gedbrowser.datamodel.visitor.RootVisitor;

/**
 * @author Dick Schoeller
 */
public final class Index {
    // TODO It would be nice to organize the last by date, but to do that, we
    // would need the whole and GedObject in here and a nastier comparator for
    // the tree set.

    /**
     * The storage for the index.
     *
     * Structure: TreeMap<"Surname", TreeMap<"Surname, Given", TreeSet<"ID">>>
     * This allows us to organize by surname and then surname,given and finally
     * the set of IDs that have the same surname,given.
     */
    private final transient SortedMap<String,
            SortedMap<String, SortedSet<String>>> surnameIndex =
            new TreeMap<String, SortedMap<String, SortedSet<String>>>();

    /**
     * The root object that this index is associated with.
     */
    private final transient Root mRoot;

    /**
     * @param root the root object for this index.
     */
    public Index(final Root root) {
        this.mRoot = root;
    }

    /**
     * Initialize the index from the root's objects.
     */
    public void init() {
        surnameIndex.clear();

        final RootVisitor visitor = new RootVisitor();
        mRoot.accept(visitor);
        for (final Person person : visitor.getPersons()) {
            final String key = person.getString();
            // Surname for inclusion in the index.
            // This is the string by which the person will be indexed.
            final String indexName = person.getIndexName();

            if (!indexName.isEmpty()) {
                final SortedMap<String, SortedSet<String>> names =
                        findNamesPerSurname(person.getSurname());
                final SortedSet<String> ids = findIdsPerName(indexName, names);
                ids.add(key);
            }
        }
    }

    /**
     * Find the map of full names to a sets of IDs for the given surname. If the
     * surname is not already present, create a new map and add it to the index.
     *
     * @param surname surname to search
     * @return the associated map
     */
    private SortedMap<String, SortedSet<String>> findNamesPerSurname(
            final String surname) {
        if (surnameIndex.containsKey(surname)) {
            return surnameIndex.get(surname);
        }
        final SortedMap<String, SortedSet<String>> namesPerSurname =
                new TreeMap<String, SortedSet<String>>();
        surnameIndex.put(surname, namesPerSurname);
        return namesPerSurname;
    }

    /**
     * Find the set of IDs associated with a full name in the provided map.
     *
     * @param indexName the full name in index form
     * @param names the map of full names to sets of IDs
     * @return the set of ID strings
     */
    private SortedSet<String> findIdsPerName(final String indexName,
            final SortedMap<String, SortedSet<String>> names) {
        if (names.containsKey(indexName)) {
            return names.get(indexName);
        }
        final TreeSet<String> idsPerName = new TreeSet<String>();
        names.put(indexName, idsPerName);
        return idsPerName;
    }

    /**
     * @return the number of surnames in the index
     */
    public int surnameCount() {
        return surnameIndex.size();
    }

    /**
     * @return the set of surnames in the index
     */
    public Set<String> getSurnames() {
        return Collections.unmodifiableSet(surnameIndex.keySet());
    }

    /**
     * Get the set of full names that occur for a given surname.
     *
     * @param surname the surname to search.
     * @return the keys
     */
    public Set<String> getNamesPerSurname(final String surname) {
        if (surname == null) {
            return Collections.emptySet();
        }
        if (!surnameIndex.containsKey(surname)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(surnameIndex.get(surname).keySet());
    }

    // TODO want a method to provide the count of names for a given surname.
    // TODO want a method to provide the total count of names.
    // TODO want a method to provide the IDs per name/surname.

    /**
     * Get map of full names to sets of IDs for the provided surname.
     *
     * @param surname the surname to search.
     * @return the map
     */
    private Map<String, SortedSet<String>> getNamesPerSurnameMap(
            final String surname) {
        if (!surnameIndex.containsKey(surname)) {
            return Collections.emptyMap();
        }
        return surnameIndex.get(surname);
    }

    /**
     * Get the set of IDs for the given surname and full name.
     *
     * @param surname the surname to search
     * @param name the full name in index form
     * @return the set of IDs
     */
    public Set<String> getIdsPerName(final String surname, final String name) {
        if (surname == null || name == null) {
            return Collections.emptySet();
        }
        final Map<String, SortedSet<String>> namesPerSurname =
                getNamesPerSurnameMap(surname);
        if (!namesPerSurname.containsKey(name)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(namesPerSurname.get(name));
    }
}
