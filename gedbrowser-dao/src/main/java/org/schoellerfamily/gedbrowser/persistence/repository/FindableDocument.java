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
}
