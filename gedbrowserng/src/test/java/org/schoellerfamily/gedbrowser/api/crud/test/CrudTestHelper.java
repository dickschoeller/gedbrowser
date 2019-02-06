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
    private final ApiPerson.Builder builder;

    /** */
    private final PersonCrud personCrud;

    /** */
    private final FamilyCrud familyCrud;

    /**
     * @param personCrud the person CRUD object
     * @param familyCrud the family CRUD object
     */
    CrudTestHelper(final PersonCrud personCrud, final FamilyCrud familyCrud) {
        this.personCrud = personCrud;
        this.familyCrud = familyCrud;
        builder = new ApiPerson.Builder().build();
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
    public ApiPerson.Builder getPersonBuilder() {
        return builder;
    }

    /**
     * @return a newly created, very simple person
     */
    public ApiPerson createPerson() {
        return personCrud.createPerson(getDb(), buildPerson());
    }

    /**
     * @return a new person object
     */
    public ApiPerson buildPerson() {
        return new ApiPerson(builder);
    }

    /**
     * @param person the person that we are "regetting"
     * @return the newly gotten person
     */
    public ApiPerson getPerson(final ApiPerson person) {
        return personCrud.readPerson(getDb(), person.getString());
    }

    /**
     * @param famID the ID of the family to read
     * @return the family
     */
    public ApiFamily readFamily(final String famID) {
        return familyCrud.readFamily(getDb(), famID);
    }


    /**
     * @return the newly created person
     */
    public ApiPerson createAlexander() {
        final ApiPerson.Builder builder1 = new ApiPerson.Builder()
                .id("")
                .add(new ApiAttribute("name", "Alexander/Romanov/", ""))
                .add(new ApiAttribute("attribute", "Sex", "M"))
                .surname("Romanov")
                .indexName("Romanov, Alexander")
                .build();
        return new ApiPerson(builder1);
    }

    /**
     * @return the newly created person
     */
    public ApiPerson createAlexandra() {
        final ApiPerson.Builder builder1 = new ApiPerson.Builder()
                .id("")
                .add(new ApiAttribute("name", "Alexandra/Romanov/", ""))
                .add(new ApiAttribute("attribute", "Sex", "F"))
                .surname("Romanov")
                .indexName("Romanov, Alexandra")
                .build();
        return new ApiPerson(builder1);
    }

}
