package org.schoellerfamily.gedbrowser.selenium.pageobjects;

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
     * Creates a new GreatGreatGrandParentIds.
     *
     * @param fathers the fathers
     * @param mothers the mothers
     */
    public GreatGreatGrandParentIds(final GreatGrandParentIds fathers,
            final GreatGrandParentIds mothers) {
        this.fathers = fathers;
        this.mothers = mothers;
    }

    /**
     * Gets the ffff.
     *
     * @return the ffff
     */
    public String getFfff() {
        return fathers.getFff();
    }

    /**
     * Gets the fffm.
     *
     * @return the fffm
     */
    public String getFffm() {
        return fathers.getFfm();
    }

    /**
     * Gets the ffmf.
     *
     * @return the ffmf
     */
    public String getFfmf() {
        return fathers.getFmf();
    }

    /**
     * Gets the ffmm.
     *
     * @return the ffmm
     */
    public String getFfmm() {
        return fathers.getFmm();
    }

    /**
     * Gets the fmff.
     *
     * @return the fmff
     */
    public String getFmff() {
        return fathers.getMff();
    }

    /**
     * Gets the fmfm.
     *
     * @return the fmfm
     */
    public String getFmfm() {
        return fathers.getMfm();
    }

    /**
     * Gets the fmmf.
     *
     * @return the fmmf
     */
    public String getFmmf() {
        return fathers.getMmf();
    }

    /**
     * Gets the fmmm.
     *
     * @return the fmmm
     */
    public String getFmmm() {
        return fathers.getMmm();
    }

    /**
     * Gets the mfff.
     *
     * @return the mfff
     */
    public String getMfff() {
        return mothers.getFff();
    }

    /**
     * Gets the mffm.
     *
     * @return the mffm
     */
    public String getMffm() {
        return mothers.getFfm();
    }

    /**
     * Gets the mfmf.
     *
     * @return the mfmf
     */
    public String getMfmf() {
        return mothers.getFmf();
    }

    /**
     * Gets the mfmm.
     *
     * @return the mfmm
     */
    public String getMfmm() {
        return mothers.getFmm();
    }

    /**
     * Gets the mmff.
     *
     * @return the mmff
     */
    public String getMmff() {
        return mothers.getMff();
    }

    /**
     * Gets the mmfm.
     *
     * @return the mmfm
     */
    public String getMmfm() {
        return mothers.getMfm();
    }

    /**
     * Gets the mmmf.
     *
     * @return the mmmf
     */
    public String getMmmf() {
        return mothers.getMmf();
    }

    /**
     * Gets the mmmm.
     *
     * @return the mmmm
     */
    public String getMmmm() {
        return mothers.getMmm();
    }

}
