package org.schoellerfamily.gedbrowser.datamodel.finder;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.FinderObject;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Defines the contract for finder strategy.
 *
 * @author Richard Schoeller
 */
public interface FinderStrategy {
    /**
     * Performs find.
     *
     * @param owner the GedObject in whose context we are searching
     * @param str the ID string of the object being sought
     * @return the object found from the searching the top level object list
     */
    GedObject find(FinderObject owner, String str);

    /**
     * Performs find.
     *
     * @param <T> the type to return
     * @param owner the GedObject in whose context we are searching
     * @param str the ID string of the object being sought
     * @return the found object or null
     */
    <T extends GedObject> T find(FinderObject owner, String str,
            Class<T> clazz);

    /**
     * Finds the by surname.
     *
     * @param owner the GedObject in whose context we are searching
     * @param surname the surname string of the object being sought
     * @return a collection of matching persons.
     */
    Collection<Person> findBySurname(FinderObject owner, String surname);

    /**
     * Finds the by surnames begin with.
     *
     * @param owner the GedObject in whose context we are searching
     * @return a collection of matching surnames.
     */
    Collection<String> findBySurnamesBeginWith(FinderObject owner,
            String beginsWith);

    /**
     * Gets the filename.
     *
     * @param owner the GedObject in whose context we are searching
     * @return the filename associated with this data set.
     */
    String getFilename(FinderObject owner);

    /**
     * Gets the db name.
     *
     * @param owner the GedObject in whose context we are searching
     * @return the filename associated with this data set.
     */
    String getDbName(FinderObject owner);

    /**
     * Performs insert.
     *
     * @param owner the GedObject in whose context we are searching
     * @param gob object to insert.
     */
    void insert(FinderObject owner, FinderObject gob);

    /**
     * Finds the surname initial letters.
     *
     * @param owner the GedObject in whose context we are searching
     * @return a collection of initial letters
     */
    Collection<String> findSurnameInitialLetters(FinderObject owner);

    /**
     * Find all of the objects of a given type in the context.
     *
     * @param <T> the type to return
     * @param owner the GedObject in whose context we are searching
     * @return a collection of objects of that type
     */
    <T extends GedObject> Collection<T> find(FinderObject owner,
            Class<T> clazz);
}
