package org.schoellerfamily.gedbrowser.analytics;

import lombok.NoArgsConstructor;



/**
 * Represents standard typicals.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class StandardTypicals implements Typicals {
    /** Typical age at death. */
    private static final int TYPICAL_AGE_AT_DEATH = 75;
    /** Typical age at bar/bat mitzvah. */
    private static final int AGE_AT_BAR_MITZVAH = 13;
    /** Typical age at marriage. */
    private static final int TYPICAL_AGE_AT_MARRIAGE = 25;
    /** Typical gap between children. */
    private static final int TYPICAL_GAP_BETWEEN_CHILDREN = 2;
    /** Typical gap between husband and wife. */
    private static final int TYPICAL_GAP_BETWEEN_SPOUSES = 5;

    /**
     * Returns the int.
     *
     * @return the resulting int
     */
    @Override
    public int ageAtDeath() {
        return TYPICAL_AGE_AT_DEATH;
    }

    /**
     * Returns the int.
     *
     * @return the resulting int
     */
    @Override
    public int ageAtBarMitzvah() {
        return AGE_AT_BAR_MITZVAH;
    }

    /**
     * Returns the int.
     *
     * @return the resulting int
     */
    @Override
    public int ageAtMarriage() {
        return TYPICAL_AGE_AT_MARRIAGE;
    }

    /**
     * Returns the int.
     *
     * @return the resulting int
     */
    @Override
    public int gapBetweenChildren() {
        return TYPICAL_GAP_BETWEEN_CHILDREN;
    }

    /**
     * Returns the int.
     *
     * @return the resulting int
     */
    @Override
    public int gapBetweenSpouses() {
        return TYPICAL_GAP_BETWEEN_SPOUSES;
    }
}
