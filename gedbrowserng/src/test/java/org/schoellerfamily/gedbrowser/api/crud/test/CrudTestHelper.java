package org.schoellerfamily.gedbrowser.api.crud.test;

import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;

/**
 * @author Dick Schoeller
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
     * @return the DB string
     */
    public String getDb() {
        return "gl120368";
    }

    /**
     * @return the person builder
     */
    public ApiPerson.ApiPersonBuilder<?, ?> getPersonBuilder() {
        return builder;
    }

    /**
     * @return a newly created, very simple person
     */
    public ApiPerson createPerson() {
        return personCrud.createOne(getDb(), buildPerson());
    }

    /**
     * @return a new person object
     */
    public ApiPerson buildPerson() {
        return builder.build();
    }

    /**
     * @param person the person that we are "regetting"
     * @return the newly gotten person
     */
    public ApiPerson getPerson(final ApiPerson person) {
        return personCrud.readOne(getDb(), person.getString());
    }

    /**
     * @param famID the ID of the family to read
     * @return the family
     */
    public ApiFamily readFamily(final String famID) {
        return familyCrud.readOne(getDb(), famID);
    }


    /**
     * @return the newly created person
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
     * @return the newly created person
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
