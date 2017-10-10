package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;

/**
 * @author Dick Schoeller
 *
 * @param <G> the type of GedObject associated with the return type
 * @param <D> the type returned by the finders
 */
public  interface FindableDocument
    <G extends GedObject, D extends GedDocument<G>>  {
    /**
     * @param filename the value of filename
     * @param string the value of string
     * @return the matching item
     */
    D findByFileAndString(String filename, String string);

    /**
     * @param rootDocument the root of our data set
     * @param string the value of string
     * @return the matching item
     */
    D findByRootAndString(RootDocument rootDocument, String string);

    /**
     * @param filename the value of filename
     * @return the list of all documents of this type in that filename
     */
    Iterable<D> findAll(String filename);

    /**
     * @param rootDocument the root of our data set
     * @return the list of all document of this type in that root
     */
    Iterable<D> findAll(RootDocument rootDocument);

    /**
     * @param filename the value of filename
     * @return the list of all documents of this type in that filename
     */
    long count(String filename);

    /**
     * @param rootDocument the root of our data set
     * @return the list of all document of this type in that root
     */
    long count(RootDocument rootDocument);

    /**
     * @param rootDocument the root of the data set
     * @return the last ID matching the standard pattern in this data set
     */
    String lastId(RootDocument rootDocument);

    /**
     * @param rootDocument the root of the data set
     * @return the last ID matching the standard pattern in this data set
     */
    String newId(RootDocument rootDocument);
}
