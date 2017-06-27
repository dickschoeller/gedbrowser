package org.schoellerfamily.gedbrowser.datamodel;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.finder.ParentFinder;

/**
 * @author Dick Schoeller
 */
public abstract class AbstractFinderObject implements FinderObject {
    /** */
    private transient FinderStrategy finder;

    /**
     * Constructor.
     */
    public AbstractFinderObject() {
        finder = new ParentFinder();
    }

    /**
     * @param <T> the type to return
     * @param str the ID string of the object you're looking for
     * @param clazz the class object of the type you're looking for
     * @return the found object
     */
    public final <T extends GedObject> T find(
            final String str, final Class<T> clazz) {
        return finder.find(this, str, clazz);
    }

    /**
     * @param str the ID string of the object being sought
     * @return the object found from the searching the top level object list
     */
    public final GedObject find(final String str) {
        return finder.find(this, str);
    }

    /**
     * @return the filename associated with this data set
     */
    public final String getFilename() {
        return finder.getFilename(this);
    }

    /**
     * @return the filename associated with this data set
     */
    public final String getDbName() {
        return finder.getDbName(this);
    }

    /**
     * @param gob object to insert
     */
    public final void insert(final FinderObject gob) {
        if (gob == null) {
            return;
        }
        extraInsert(gob);
        finder.insert(this, gob);
    }

    /**
     * Set the strategy that implements finding.
     *
     * @param finder the finder.
     */
    public final void setFinder(final FinderStrategy finder) {
        this.finder = finder;
    }

    /**
     *GSet the strategy that implements finding.
     *
     * @return the finder
     */
    public final FinderStrategy getFinder() {
        return finder;
    }

    /**
     * @param surname the surname of the persons being sought.
     * @return collection of matches.
     */
    public final Collection<Person> findBySurname(final String surname) {
        return finder.findBySurname(this, surname);
    }

    /**
     * @param beginsWith the string that the surnames should beginWith.
     * @return collection of matches.
     */
    public final Collection<String> findBySurnamesBeginWith(
            final String beginsWith) {
        return finder.findBySurnamesBeginWith(this, beginsWith);
    }

    /**
     * @return the collection of initial letters
     */
    public final Collection<String> findSurnameInitialLetters() {
        return finder.findSurnameInitialLetters(this);
    }


    /**
     * @param <T> the type to look for
     * @param clazz the class object of the type to look for
     * @return the collection of objects of the class provided
     */
    public final <T extends GedObject> Collection<T> find(
            final Class<T> clazz) {
        return finder.find(this, clazz);
    }
}
