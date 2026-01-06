package org.schoellerfamily.gedbrowser.analytics;

/**
 * Interface describing how to get typical values used in age calculations.
 *
 * @author Dick Schoeller
 */
public interface Typicals {

    /**
     * Get the age at death.
     *
     * @return typical age at death.
     */
    int ageAtDeath();

    /**
     * Get the age at bar or bat mitzvah.
     *
     * @return typical age at bar/bat mitzvah.
     */
    int ageAtBarMitzvah();

    /**
     * Get the typical age at marriage.
     *
     * @return typical age at marriage.
     */
    int ageAtMarriage();

    /**
     * Get the typical gap between children.
     *
     * @return typical gap between children.
     */
    int gapBetweenChildren();

    /**
     * Get the typical age difference between husband and wife.
     *
     * @return typical difference in age between husband and wife.
     */
    int gapBetweenSpouses();
}
