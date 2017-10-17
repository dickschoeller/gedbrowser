package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * @param <X> the data model type we are creating
 * @param <Y> the DB type associated with the type X
 * @author Dick Schoeller
 */
public interface NewId <X extends GedObject, Y extends GedDocument<X>> {
    /**
     * @return the DB repository for this type
     */
    FindableDocument<X, Y> getRepository();

    /**
     * @param root the root
     * @return the next ID string
     */
    default String newId(final RootDocument root) {
        return getRepository().newId(root);
    }
}
