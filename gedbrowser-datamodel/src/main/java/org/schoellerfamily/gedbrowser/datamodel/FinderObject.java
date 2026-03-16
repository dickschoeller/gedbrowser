package org.schoellerfamily.gedbrowser.datamodel;

import java.util.Collection;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;

/**
 * Defines the contract for finder object.
 *
 * @author Richard Schoeller
 */
public interface FinderObject
    extends VisitableObject, ParentableObject, InsertableObject, StringObject {
    /**
     * Performs find.
     *
     * @param <T> the type to return
     * @param str the ID string of the object you're looking for
     * @param clazz the class object of the type you're looking for
     * @return the found object
     */
    <T extends GedObject> T find(String str, Class<T> clazz);

    /**
     * Performs find.
     *
     * @param str the ID string of the object being sought
     * @return the object found from the searching the top level object list
     */
    GedObject find(String str);

    /**
     * Gets the filename.
     *
     * @return the filename associated with this data set
     */
    String getFilename();

    /**
     * Gets the db name.
     *
     * @return the filename associated with this data set
     */
    String getDbName();

    /**
     * Set the strategy that implements finding.
     *
     * @param finder the finder.
     */
    void setFinder(FinderStrategy finder);

    /**
     * Finds the by surname.
     *
     * @param surname the surname of the persons being sought.
     * @return collection of matches.
     */
    Collection<Person> findBySurname(String surname);

    /**
     * Finds the by surnames begin with.
     *
     * @param beginsWith the string that the surnames should beginWith.
     * @return collection of matches.
     */
    Collection<String> findBySurnamesBeginWith(String beginsWith);

    /**
     * Finds the surname initial letters.
     *
     * @return the collection of initial letters
     */
    Collection<String> findSurnameInitialLetters();

    /**
     * Finds the in parent.
     *
     * @param str the ID string of the object being sought
     * @return the object found from the searching the top level object list
     */
    default GedObject findInParent(final String str) {
        if (getParent() == null) {
            return null;
        }
        return getParent().find(str);
    }

    /**
     * Finds the in parent.
     *
     * @param <T> the type to return
     * @param str the ID string of the object you're looking for
     * @param clazz the class object of the type you're looking for
     * @return the found object
     */
    default <T extends GedObject> T findInParent(
            final String str, final Class<T> clazz) {
        return getParent().find(str, clazz);
    }

    /**
     * Finds the in parent by surname.
     *
     * @param surname the surname of persons being sought.
     * @return collection of matches.
     */
    default Collection<Person> findInParentBySurname(final String surname) {
        if (getParent() == null) {
            return List.of();
        }

        return getParent().findBySurname(surname);
    }

    /**
     * Finds the in parent by surnames begin with.
     *
     * @param beginsWith the string that the surnames should beginWith.
     * @return collection of matches.
     */
    default Collection<String> findInParentBySurnamesBeginWith(
            final String beginsWith) {
        return getParent().findBySurnamesBeginWith(beginsWith);
    }

    /**
     * Finds the in parent surname initial letters.
     *
     * @return the collection of initial letters
     */
    default Collection<String> findInParentSurnameInitialLetters() {
        return getParent().findSurnameInitialLetters();
    }


    /**
     * Finds the in parent.
     *
     * @param <T> the type to return
     * @param clazz the type we're looking for
     * @return the collection of objects of the type
     */
    default <T extends GedObject> Collection<T> findInParent(final Class<T> clazz) {
        return getParent().find(clazz);
    }
}
