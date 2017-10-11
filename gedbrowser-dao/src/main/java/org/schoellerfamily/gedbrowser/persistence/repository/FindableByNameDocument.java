package org.schoellerfamily.gedbrowser.persistence.repository;

import java.util.Collection;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;

/**
 * @author Dick Schoeller
 *
 * @param <G> the type of GedObject associated with the return type
 * @param <D> the type returned by the finders
 */
public interface FindableByNameDocument
    <G extends GedObject, D extends GedDocument<G>>
        extends FindableDocument<G, D> {

    /**
     * @param filename the value of filename
     * @param surname the value of surname
     * @return the collection of matching items
     */
    Collection<D> findByFileAndSurname(String filename, String surname);

    /**
     * @param rootDocument the root of our data set
     * @param surname the value of surname
     * @return the collection of matching items
     */
    Collection<D> findByRootAndSurname(RootDocument rootDocument,
            String surname);


    /**
     * @param filename the value of filename
     * @param beginsWith the initial part of the surname string
     * @return collection of matching items
     */
    Collection<D> findByFileAndSurnameBeginsWith(String filename,
            String beginsWith);

    /**
     * @param rootDocument the root of our data set
     * @param beginsWith the initial part of the surname string
     * @return collection of matching items
     */
    Collection<D> findByRootAndSurnameBeginsWith(RootDocument rootDocument,
            String beginsWith);
}
