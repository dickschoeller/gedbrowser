package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Represents the persisted form of person.
 *
 * @author Richard Schoeller
 */
public interface PersonDocument extends GedDocument<Person> {
    /**
     * @return the surname string for this person.
     */
    String getSurname();

    /**
     * @return the index string for this person.
     */
    String getIndexName();

    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
