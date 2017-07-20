package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * Class represents the ID strings for a quad of grandparents.
 *
 * @author Dick Schoeller
 */
public final class GrandParentIds {
    /** */
    private final ParentIds fathers;
    /** */
    private final ParentIds mothers;

    /**
     * Constructor.
     *
     * @param fathers father's parents
     * @param mothers mother's parents
     */
    public GrandParentIds(final ParentIds fathers, final ParentIds mothers) {
        this.fathers = fathers;
        this.mothers = mothers;
    }

    /**
     * @return the father's father's id
     */
    public String getFf() {
        return fathers.getF();
    }

    /**
     * @return the father's mother's id
     */
    public String getFm() {
        return fathers.getM();
    }

    /**
     * @return the mother's father's id
     */
    public String getMf() {
        return mothers.getF();
    }

    /**
     * @return the mother's mother's id
     */
    public String getMm() {
        return mothers.getM();
    }
}
