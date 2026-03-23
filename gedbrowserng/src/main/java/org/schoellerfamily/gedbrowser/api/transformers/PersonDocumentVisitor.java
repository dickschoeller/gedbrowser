package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiLifespan;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;

/**
 * The visitor for PersonDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface PersonDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final PersonDocument document) {
        final ApiLifespan lifespan = buildLifespan(document);
        final ApiPerson person = ApiPerson.builder()
            .type(document.getType())
            .string(document.getString())
            .indexName(document.getIndexName())
            .surname(document.getSurname())
            .lifespan(lifespan)
            .attributes(processAttributes(document))
            .build();
        setBaseObject(person);
    }


    /**
     * Method to create a lifespan for the provided person.
     *
     * @param document the person
     * @return the lifespan
     */
    private ApiLifespan buildLifespan(final PersonDocument document) {
        final String birthDate = date(document, "Birth");
        final String deathDate = date(document, "Death");
        final String birthYear = year(document, "Birth");
        final String deathYear = year(document, "Death");

        return ApiLifespan.builder()
            .birthDate(birthDate)
            .deathDate(deathDate)
            .birthYear(birthYear)
            .deathYear(deathYear)
            .build();
    }

    /**
     * Get a particular date type.
     *
     * @param document the person document we're assessing
     * @param type which event type
     * @return the date string
     */
    private String date(final PersonDocument document, final String type) {
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
    private String year(final PersonDocument document, final String type) {
        final GetDateVisitor dateVisitor = new GetDateVisitor(type);
        document.getGedObject().accept(dateVisitor);
        return dateVisitor.getYear();
    }
}
