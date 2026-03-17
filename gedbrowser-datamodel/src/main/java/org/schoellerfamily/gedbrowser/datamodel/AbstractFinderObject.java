package org.schoellerfamily.gedbrowser.datamodel;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.finder.ParentFinder;

/**
 * Represents abstract finder object in the domain model.
 *
 * @author Richard Schoeller
 */
public abstract class AbstractFinderObject implements FinderObject {
    /**
     * The finder value.
     */
    private transient FinderStrategy finder;

    /**
     * Creates a new AbstractFinderObject.
     */
    protected AbstractFinderObject() {
        finder = new ParentFinder();
    }

    /**
     * Returns the t.
     *
     * @param <T> the type to return
     * @param str the str
     * @param clazz the clazz
     * @return the resulting t
     */
    public final <T extends GedObject> T find(
            final String str, final Class<T> clazz) {
        return finder.find(this, str, clazz);
    }

    /**
     * Returns the ged object.
     *
     * @param str the str
     * @return the resulting ged object
     */
    public final GedObject find(final String str) {
        return finder.find(this, str);
    }

    /**
     * Gets the filename.
     *
     * @return the filename
     */
    public final String getFilename() {
        return finder.getFilename(this);
    }

    /**
     * Gets the db name.
     *
     * @return the db name
     */
    public final String getDbName() {
        return finder.getDbName(this);
    }

    /**
     * Executes insert.
     *
     * @param gob the gob
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
     * GSet the strategy that implements finding.
     *
     * @return the finder
     */
    public final FinderStrategy getFinder() {
        return finder;
    }

    /**
     * Finds by surname.
     *
     * @param surname the surname to use
     * @return the resulting collection of person
     */
    public final Collection<Person> findBySurname(final String surname) {
        return finder.findBySurname(this, surname);
    }

    /**
     * Finds the by surnames begin with.
     *
     * @param beginsWith the begins with
     * @return the resulting collection
     */
    public final Collection<String> findBySurnamesBeginWith(
            final String beginsWith) {
        return finder.findBySurnamesBeginWith(this, beginsWith);
    }

    /**
     * Finds surname initial letters.
     *
     * @return the resulting collection of string
     */
    public final Collection<String> findSurnameInitialLetters() {
        return finder.findSurnameInitialLetters(this);
    }


    /**
     * Returns the collection.
     *
     * @param <T> the type to return
     * @param clazz the clazz
     * @return the resulting collection
     */
    public final <T extends GedObject> Collection<T> find(
            final Class<T> clazz) {
        return finder.find(this, clazz);
    }
}
