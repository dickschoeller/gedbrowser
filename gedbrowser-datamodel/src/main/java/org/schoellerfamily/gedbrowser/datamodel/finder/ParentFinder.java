package org.schoellerfamily.gedbrowser.datamodel.finder;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.FinderObject;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Represents parent finder in the domain model.
 *
 * @author Richard Schoeller
 */
public final class ParentFinder implements FinderStrategy {
    /**
     * Creates a new ParentFinder.
     */
    public ParentFinder() {
        // Empty constructor.
    }

    /**
     * Returns the ged object.
     *
     * @param owner the owner
     * @param str the str
     * @return the resulting ged object
     */
    @Override
    public GedObject find(final FinderObject owner, final String str) {
        return owner.findInParent(str);
    }

    /**
     * Returns the filename.
     *
     * @param owner the owner
     * @return the filename
     */
    @Override
    public String getFilename(final FinderObject owner) {
        return owner.getParentFilename();
    }

    /**
     * Returns the db name.
     *
     * @param owner the owner
     * @return the db name
     */
    @Override
    public String getDbName(final FinderObject owner) {
        return owner.getParentDbName();
    }

    /**
     * Executes insert.
     *
     * @param owner the owner
     * @param gob the gob
     */
    @Override
    public void insert(final FinderObject owner, final FinderObject gob) {
        ((GedObject) owner).addAttribute((GedObject) gob);
    }

    /**
     * Returns the t.
     *
     * @param owner the owner
     * @param str the str
     * @param clazz the clazz
     * @return the resulting t
     */
    @Override
    public <T extends GedObject> T find(
            final FinderObject owner, final String str, final Class<T> clazz) {
        return owner.findInParent(str, clazz);
    }

    /**
     * Finds the by surname.
     *
     * @param owner the owner
     * @param surname the surname to use
     * @return the resulting collection
     */
    @Override
    public Collection<Person> findBySurname(final FinderObject owner,
            final String surname) {
        return owner.findInParentBySurname(surname);
    }

    /**
     * Finds the by surnames begin with.
     *
     * @param owner the owner
     * @param beginsWith the begins with
     * @return the resulting collection
     */
    @Override
    public Collection<String> findBySurnamesBeginWith(final FinderObject owner,
            final String beginsWith) {
        return owner.findInParentBySurnamesBeginWith(beginsWith);
    }

    /**
     * Finds the surname initial letters.
     *
     * @param owner the owner
     * @return the resulting collection
     */
    @Override
    public Collection<String> findSurnameInitialLetters(
            final FinderObject owner) {
        return owner.findInParentSurnameInitialLetters();
    }

    /**
     * Returns the collection.
     *
     * @param owner the owner
     * @param clazz the clazz
     * @return the resulting collection
     */
    @Override
    public <T extends GedObject> Collection<T> find(final FinderObject owner,
            final Class<T> clazz) {
        return owner.findInParent(clazz);
    }
}
