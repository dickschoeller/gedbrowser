package org.schoellerfamily.gedbrowser.datamodel;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;

/**
 * @author Dick Schoeller
 */
public interface FinderObject
    extends VisitableObject, ParentableObject, InsertableObject, StringObject {
    /**
     * @param <T> the type to return
     * @param str the ID string of the object you're looking for
     * @param clazz the class object of the type you're looking for
     * @return the found object
     */
    <T extends GedObject> T find(String str, Class<T> clazz);

    /**
     * @param str the ID string of the object being sought
     * @return the object found from the searching the top level object list
     */
    GedObject find(String str);

    /**
     * @return the filename associated with this data set
     */
    String getFilename();

    /**
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
     * @param surname the surname of the persons being sought.
     * @return collection of matches.
     */
    Collection<Person> findBySurname(String surname);

    /**
     * @param beginsWith the string that the surnames should beginWith.
     * @return collection of matches.
     */
    Collection<String> findBySurnamesBeginWith(String beginsWith);

    /**
     * @return the collection of initial letters
     */
    Collection<String> findSurnameInitialLetters();

    /**
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
     * @param surname the surname of persons being sought.
     * @return collection of matches.
     */
    default Collection<Person> findInParentBySurname(
            final String surname) {
        if (getParent() == null) {
            return null;
        }

        return getParent().findBySurname(surname);
    }

    /**
     * @param beginsWith the string that the surnames should beginWith.
     * @return collection of matches.
     */
    default Collection<String> findInParentBySurnamesBeginWith(
            final String beginsWith) {
        return getParent().findBySurnamesBeginWith(beginsWith);
    }

    /**
     * @return the collection of initial letters
     */
    default Collection<String> findInParentSurnameInitialLetters() {
        return getParent().findSurnameInitialLetters();
    }


    /**
     * @param <T> the type to look for
     * @param clazz the type we're looking for
     * @return the collection of objects of the type
     */
    default <T extends GedObject> Collection<T> findInParent(Class<T> clazz) {
        return getParent().find(clazz);
    }
}
