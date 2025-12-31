package org.schoellerfamily.gedbrowser.api.datamodel;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents the lifespan of a person to support easier rendering, without checking the whole
 * attribute list.
 *
 * @author Dick Schoeller
 */
@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@Jacksonized
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
}
