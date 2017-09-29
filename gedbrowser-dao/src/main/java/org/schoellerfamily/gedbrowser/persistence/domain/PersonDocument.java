package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
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

    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
