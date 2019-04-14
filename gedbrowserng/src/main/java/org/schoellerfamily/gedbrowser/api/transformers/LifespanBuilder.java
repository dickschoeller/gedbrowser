package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiLifespan;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;

/**
 * @author Dick Schoeller
 */
public interface LifespanBuilder {

    /**
     * Method to create a lifespan for the provided person.
     *
     * @param document the person
     * @return the lifespan
     */
    default ApiLifespan buildLifespan(final PersonDocument document) {
        final String birthDate = date(document, "Birth");
        final String deathDate = date(document, "Death");
        final String birthYear = year(document, "Birth");
        final String deathYear = year(document, "Death");

        return new ApiLifespan(birthDate, deathDate, birthYear, deathYear);
    }

    /**
     * Get a particular date type.
     *
     * @param document the person document we're assessing
     * @param type which event type
     * @return the date string
     */
    default String date(final PersonDocument document, final String type) {
        final GetDateVisitor dateVisitor = new GetDateVisitor(type);
        document.getGedObject().accept(dateVisitor);
        return dateVisitor.getDate();
    }


    /**
     * Get the year part of a particular date type.
     *
     * @param document the person document we're assessing
     * @param type which event type
     * @return the year string
     */
    default String year(final PersonDocument document, final String type) {
        final GetDateVisitor dateVisitor = new GetDateVisitor(type);
        document.getGedObject().accept(dateVisitor);
        return dateVisitor.getYear();
    }


}
