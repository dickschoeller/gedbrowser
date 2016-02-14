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
    Collection<D> findByFileAndSurname(final String filename,
            final String surname);

    /**
     * @param rootDocument the root of our data set
     * @param surname the value of surname
     * @return the collection of matching items
     */
    Collection<D> findByRootAndSurname(
            final RootDocument rootDocument, final String surname);


    /**
     * @param filename the value of filename
     * @param beginsWith the initial part of the surname string
     * @return collection of matching items
     */
    Collection<D> findByFileAndSurnameBeginsWith(
            final String filename, final String beginsWith);

    /**
     * @param rootDocument the root of our data set
     * @param beginsWith the initial part of the surname string
     * @return collection of matching items
     */
    Collection<D> findByRootAndSurnameBeginsWith(
            final RootDocument rootDocument, final String beginsWith);

    /**
     * @param filename the value of filename
     * @return collection of matching items
     */
    Collection<D> findByFile(final String filename);

    /**
     * @param rootDocument the root of our data set
     * @return collection of matching items
     */
    Collection<D> findByRoot(final RootDocument rootDocument);
}
