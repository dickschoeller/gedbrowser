package org.schoellerfamily.gedbrowser.datamodel.finder;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.FinderObject;
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

    @Override
    public GedObject find(final FinderObject owner, final String str) {
        return owner.findInParent(str);
    }

    @Override
    public String getFilename(final FinderObject owner) {
        return owner.getParentFilename();
    }

    @Override
    public String getDbName(final FinderObject owner) {
        return owner.getParentDbName();
    }

    @Override
    public void insert(final FinderObject owner, final FinderObject gob) {
        ((GedObject) owner).addAttribute((GedObject) gob);
    }

    @Override
    public <T extends GedObject> T find(
            final FinderObject owner, final String str, final Class<T> clazz) {
        return owner.findInParent(str, clazz);
    }

    @Override
    public Collection<Person> findBySurname(final FinderObject owner,
            final String surname) {
        return owner.findInParentBySurname(surname);
    }

    @Override
    public Collection<String> findBySurnamesBeginWith(final FinderObject owner,
            final String beginsWith) {
        return owner.findInParentBySurnamesBeginWith(beginsWith);
    }

    @Override
    public Collection<String> findSurnameInitialLetters(
            final FinderObject owner) {
        return owner.findInParentSurnameInitialLetters();
    }

    @Override
    public <T extends GedObject> Collection<T> find(final FinderObject owner,
            final Class<T> clazz) {
        return owner.findInParent(clazz);
    }
}
