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
     * Creates a new GrandParentIds.
     *
     * @param fathers the fathers
     * @param mothers the mothers
     */
    public GrandParentIds(final ParentIds fathers, final ParentIds mothers) {
        this.fathers = fathers;
        this.mothers = mothers;
    }

    /**
     * Gets the ff.
     *
     * @return the ff
     */
    public String getFf() {
        return fathers.getF();
    }

    /**
     * Gets the fm.
     *
     * @return the fm
     */
    public String getFm() {
        return fathers.getM();
    }

    /**
     * Gets the mf.
     *
     * @return the mf
     */
    public String getMf() {
        return mothers.getF();
    }

    /**
     * Gets the mm.
     *
     * @return the mm
     */
    public String getMm() {
        return mothers.getM();
    }
}
