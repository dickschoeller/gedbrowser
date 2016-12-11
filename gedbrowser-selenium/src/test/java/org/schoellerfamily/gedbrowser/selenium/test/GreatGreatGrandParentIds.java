package org.schoellerfamily.gedbrowser.selenium.test;

/**
 * Class represents the IDs of 16 great-great-grandparents who are the leaves in
 * an ancestor tree.
 *
 * @author Dick Schoeller
 */
public final class GreatGreatGrandParentIds {
    /** */
    private final GreatGrandParentIds fathers;
    /** */
    private final GreatGrandParentIds mothers;

    /**
     * @param fathers father's great grand parents
     * @param mothers mother's great grand parents
     */
    GreatGreatGrandParentIds(final GreatGrandParentIds fathers,
            final GreatGrandParentIds mothers) {
        this.fathers = fathers;
        this.mothers = mothers;
    }

    /**
     * @return the ffff
     */
    public String getFfff() {
        return fathers.getFff();
    }

    /**
     * @return the fffm
     */
    public String getFffm() {
        return fathers.getFfm();
    }

    /**
     * @return the ffmf
     */
    public String getFfmf() {
        return fathers.getFmf();
    }

    /**
     * @return the ffmm
     */
    public String getFfmm() {
        return fathers.getFmm();
    }

    /**
     * @return the fmff
     */
    public String getFmff() {
        return fathers.getMff();
    }

    /**
     * @return the fmfm
     */
    public String getFmfm() {
        return fathers.getMfm();
    }

    /**
     * @return the fmmf
     */
    public String getFmmf() {
        return fathers.getMmf();
    }

    /**
     * @return the fmmm
     */
    public String getFmmm() {
        return fathers.getMmm();
    }

    /**
     * @return the mfff
     */
    public String getMfff() {
        return mothers.getFff();
    }

    /**
     * @return the mffm
     */
    public String getMffm() {
        return mothers.getFfm();
    }

    /**
     * @return the mfmf
     */
    public String getMfmf() {
        return mothers.getFmf();
    }

    /**
     * @return the mfmm
     */
    public String getMfmm() {
        return mothers.getFmm();
    }

    /**
     * @return the mmff
     */
    public String getMmff() {
        return mothers.getMff();
    }

    /**
     * @return the mmfm
     */
    public String getMmfm() {
        return mothers.getMfm();
    }

    /**
     * @return the mmmf
     */
    public String getMmmf() {
        return mothers.getMmf();
    }

    /**
     * @return the mmmm
     */
    public String getMmmm() {
        return mothers.getMmm();
    }

}
