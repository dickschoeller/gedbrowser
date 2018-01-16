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
     * Constructor.
     */
    public ApiLifespan() {
        super();
        birthDate = "";
        deathDate = "";
    }

    /**
     * @param birthDate the birth date
     * @param deathDate the death date
     */
    public ApiLifespan(final String birthDate, final String deathDate) {
        this.birthDate = defaultValue(birthDate);
        this.deathDate = defaultValue(deathDate);
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
}
