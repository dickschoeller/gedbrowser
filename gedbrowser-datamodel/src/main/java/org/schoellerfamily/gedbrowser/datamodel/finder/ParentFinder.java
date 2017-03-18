package org.schoellerfamily.gedbrowser.datamodel.finder;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public final class ParentFinder implements FinderStrategy {
    /**
     * Constructor.
     */
    public ParentFinder() {
        // Empty constructor.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GedObject find(final GedObject owner, final String str) {
        return owner.findInParent(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilename(final GedObject owner) {
        return owner.getParentFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDbName(final GedObject owner) {
        return owner.getParentDbName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final GedObject owner, final GedObject gob) {
        owner.addAttribute(gob);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends GedObject> T find(
            final GedObject owner, final String str, final Class<T> clazz) {
        return owner.findInParent(str, clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Person> findBySurname(final GedObject owner,
            final String surname) {
        return owner.findInParentBySurname(surname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findBySurnamesBeginWith(final GedObject owner,
            final String beginsWith) {
        return owner.findInParentBySurnamesBeginWith(beginsWith);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findSurnameInitialLetters(final GedObject owner) {
        return owner.findInParentSurnameInitialLetters();
    }
}
