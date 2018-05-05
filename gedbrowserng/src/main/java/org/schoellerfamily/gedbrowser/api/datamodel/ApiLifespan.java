package org.schoellerfamily.gedbrowser.api.datamodel;

import java.io.Serializable;

/**
 * @author Dick Schoeller
 */
public final class ApiLifespan implements Serializable {
    /** */
    private static final long serialVersionUID = 1L;

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
     * Constructor.
     */
    public ApiLifespan() {
        super();
        birthDate = "";
        deathDate = "";
        birthYear = "";
        deathYear = "";
    }

    /**
     * @param birthDate the birth date
     * @param deathDate the death date
     * @param birthYear the birth year
     * @param deathYear the death year
     */
    public ApiLifespan(final String birthDate, final String deathDate,
            final String birthYear, final String deathYear) {
        this.birthDate = defaultValue(birthDate);
        this.deathDate = defaultValue(deathDate);
        this.birthYear = defaultValue(birthYear);
        this.deathYear = defaultValue(deathYear);
    }

    /**
     * @param input the input string
     * @return input or empty string if input is null
     */
    private static String defaultValue(final String input) {
        if (input == null) {
            return "";
        }
        return input;
    }

    /**
     * @return the birth date
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @return the death date
     */
    public String getDeathDate() {
        return deathDate;
    }

    /**
     * @return the birth year
     */
    public String getBirthYear() {
        return birthYear;
    }

    /**
     * @return the death year
     */
    public String getDeathYear() {
        return deathYear;
    }
}
