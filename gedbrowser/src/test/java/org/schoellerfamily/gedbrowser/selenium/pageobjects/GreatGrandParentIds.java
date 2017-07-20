package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * Class represents the ID strings for 8 great-grandparents.
 *
 * @author Dick Schoeller
 */
public final class GreatGrandParentIds {
    /** */
    private final GrandParentIds fathers;
    /** */
    private final GrandParentIds mothers;

    /**
     * Constructor.
     *
     * @param fathers father's grandparents
     * @param mothers mother's grandparents
     */
    public GreatGrandParentIds(final GrandParentIds fathers,
            final GrandParentIds mothers) {
        this.fathers = fathers;
        this.mothers = mothers;
    }

    /**
     * @return the father's father's father's id
     */
    public String getFff() {
        return fathers.getFf();
    }

    /**
     * @return the father's father's mother's id
     */
    public String getFfm() {
        return fathers.getFm();
    }

    /**
     * @return the father's mother's father's id
     */
    public String getFmf() {
        return fathers.getMf();
    }

    /**
     * @return the father's mother's mother's id
     */
    public String getFmm() {
        return fathers.getMm();
    }

    /**
     * @return the mother's father's father's id
     */
    public String getMff() {
        return mothers.getFf();
    }

    /**
     * @return the mother's father's mother's id
     */
    public String getMfm() {
        return mothers.getFm();
    }

    /**
     * @return the mother's mother's father's id
     */
    public String getMmf() {
        return mothers.getMf();
    }

    /**
     * @return the mother's mother's mother's id
     */
    public String getMmm() {
        return mothers.getMm();
    }
}
