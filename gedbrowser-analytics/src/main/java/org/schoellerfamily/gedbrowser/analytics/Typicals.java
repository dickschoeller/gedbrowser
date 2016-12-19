package org.schoellerfamily.gedbrowser.analytics;

/**
 * Interface describing how to get typical values used in age calculations.
 *
 * @author Dick Schoeller
 */
public interface Typicals {

    /**
     * @return typical age at death.
     */
    int ageAtDeath();

    /**
     * @return typical age at bar/bat mitzvah.
     */
    int ageAtBarMitzvah();

    /**
     * @return typical age at marriage.
     */
    int ageAtMarriage();

    /**
     * @return typical gap between children.
     */
    int gapBetweenChildren();

    /**
     * @return typical difference in age between husband and wife.
     */
    int gapBetweenSpouses();
}
