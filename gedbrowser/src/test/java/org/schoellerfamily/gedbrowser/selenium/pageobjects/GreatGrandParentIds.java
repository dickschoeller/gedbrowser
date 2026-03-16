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
     * Creates a new GreatGrandParentIds.
     *
     * @param fathers the fathers
     * @param mothers the mothers
     */
    public GreatGrandParentIds(final GrandParentIds fathers,
            final GrandParentIds mothers) {
        this.fathers = fathers;
        this.mothers = mothers;
    }

    /**
     * Gets the fff.
     *
     * @return the fff
     */
    public String getFff() {
        return fathers.getFf();
    }

    /**
     * Gets the ffm.
     *
     * @return the ffm
     */
    public String getFfm() {
        return fathers.getFm();
    }

    /**
     * Gets the fmf.
     *
     * @return the fmf
     */
    public String getFmf() {
        return fathers.getMf();
    }

    /**
     * Gets the fmm.
     *
     * @return the fmm
     */
    public String getFmm() {
        return fathers.getMm();
    }

    /**
     * Gets the mff.
     *
     * @return the mff
     */
    public String getMff() {
        return mothers.getFf();
    }

    /**
     * Gets the mfm.
     *
     * @return the mfm
     */
    public String getMfm() {
        return mothers.getFm();
    }

    /**
     * Gets the mmf.
     *
     * @return the mmf
     */
    public String getMmf() {
        return mothers.getMf();
    }

    /**
     * Gets the mmm.
     *
     * @return the mmm
     */
    public String getMmm() {
        return mothers.getMm();
    }
}
