package org.schoellerfamily.gedbrowser.api.datamodel;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;



/**
 * Represents api lifespan in the domain model.
 *
 * @author Richard Schoeller
 */
@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@JsonDeserialize(builder = ApiLifespan.ApiLifespanBuilder.class)
@JsonPropertyOrder({ "birthDate", "deathDate", "birthYear", "deathYear" })
public final class ApiLifespan {
    /**
     * Holds the birth date, empty string means unknown.
     */
    private final String birthDate;

    /**
     * Holds the death date, null means unknown.
     */
    private final String deathDate;

    /**
     * Holds the birth year, empty string means unknown.
     */
    private final String birthYear;

    /**
     * Holds the death year, null means unknown.
     */
    private final String deathYear;

    /**
     * Builder metadata for Jackson.
     */
    @JsonPOJOBuilder(withPrefix = "")
    public static class ApiLifespanBuilder {
    }
}
