package org.schoellerfamily.gedbrowser.datamodel.finder;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.FinderObject;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public interface FinderStrategy {
    /**
     * @param owner the GedObject in whose context we are searching
     * @param str the ID string of the object being sought
     * @return the object found from the searching the top level object list
     */
    GedObject find(FinderObject owner, String str);

    /**
     * @param <T> the return type
     * @param owner the GedObject in whose context we are searching
     * @param str the ID string of the object being sought
     * @param clazz the class of the return type
     * @return the found object or null
     */
    <T extends GedObject> T find(FinderObject owner, String str,
            Class<T> clazz);

    /**
     * @param owner the GedObject in whose context we are searching
     * @param surname the surname string of the object being sought
     * @return a collection of matching persons.
     */
    Collection<Person> findBySurname(FinderObject owner, String surname);

    /**
     * @param owner the GedObject in whose context we are searching
     * @param beginsWith the beginning substring we are looking for
     * @return a collection of matching surnames.
     */
    Collection<String> findBySurnamesBeginWith(FinderObject owner,
            String beginsWith);

    /**
     * @param owner the GedObject in whose context we are searching
     * @return the filename associated with this data set.
     */
    String getFilename(FinderObject owner);

    /**
     * @param owner the GedObject in whose context we are searching
     * @return the filename associated with this data set.
     */
    String getDbName(FinderObject owner);

    /**
     * @param owner the GedObject in whose context we are searching
     * @param gob object to insert.
     */
    void insert(FinderObject owner, FinderObject gob);

    /**
     * @param owner the GedObject in whose context we are searching
     * @return a collection of initial letters
     */
    Collection<String> findSurnameInitialLetters(FinderObject owner);

    /**
     * Find all of the objects of a given type in the context.
     *
     * @param <T> the type of object that we are searching for
     * @param owner the GedObject in whose context we are searching
     * @param clazz class object of the type we are searching for
     * @return a collection of objects of that type
     */
    <T extends GedObject> Collection<T> find(FinderObject owner,
            Class<T> clazz);
}
