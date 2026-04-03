package org.schoellerfamily.gedbrowser.api.crud.test;

import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;

/**
 * Provides support for testing crud test helper behavior.
 *
 * @author Richard Schoeller
 */
public class CrudTestHelper {
    /** */
    private final ApiPerson.ApiPersonBuilder<?, ?> builder;

    /** */
    private final PersonCrud personCrud;

    /** */
    private final FamilyCrud familyCrud;

    CrudTestHelper(final PersonCrud personCrud, final FamilyCrud familyCrud) {
        this.personCrud = personCrud;
        this.familyCrud = familyCrud;
        builder = ApiPerson.builder();
    }

    /**
     * Gets the db.
     *
     * @return the db
     */
    public String getDb() {
        return "gl120368";
    }

    /**
     * Gets the person builder.
     *
     * @return the person builder
     */
    public ApiPerson.ApiPersonBuilder<?, ?> getPersonBuilder() {
        return builder;
    }

    /**
     * Creates the person.
     *
     * @return the resulting api person
     */
    public ApiPerson createPerson() {
        return personCrud.createOne(getDb(), buildPerson());
    }

    /**
     * Builds the person.
     *
     * @return the resulting api person
     */
    public ApiPerson buildPerson() {
        return builder.build();
    }

    /**
     * Returns the person.
     *
     * @param person the person
     * @return the person
     */
    public ApiPerson getPerson(final ApiPerson person) {
        return personCrud.readOne(getDb(), person.getString());
    }

    /**
     * Returns the api family.
     *
     * @param famID the unique identifier for fam i
     * @return the resulting api family
     */
    public ApiFamily readFamily(final String famID) {
        return familyCrud.readOne(getDb(), famID);
    }


    /**
     * Creates the alexander.
     *
     * @return the resulting api person
     */
    public ApiPerson createAlexander() {
        return ApiPerson.builder()
            .type("person")
            .string("")
            .attribute(ApiAttribute.builder()
                .type("name")
                .string("Alexander/Romanov/")
                .tail("")
                .build())
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Sex")
                .tail("M")
                .build())
            .surname("Romanov")
            .indexName("Romanov, Alexander")
            .build();
    }

    /**
     * Creates the alexandra.
     *
     * @return the resulting api person
     */
    public ApiPerson createAlexandra() {
        return ApiPerson.builder()
            .type("person")
            .string("")
            .attribute(ApiAttribute.builder()
                .type("name")
                .string("Alexandra/Romanov/")
                .tail("")
                .build())
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Sex")
                .tail("F")
                .build())
            .surname("Romanov")
            .indexName("Romanov, Alexandra")
            .build();
    }
}
