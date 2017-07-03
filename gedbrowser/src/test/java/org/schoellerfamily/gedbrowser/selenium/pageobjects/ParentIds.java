package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * Class represents the ID strings for a pair of parents.
 *
 * @author Dick Schoeller
 */
public final class ParentIds {
    /** */
    private final String father;
    /** */
    private final String mother;

    /**
     * Constructor.
     *
     * @param father father's id
     * @param mother mother's id
     */
    ParentIds(final String father, final String mother) {
        this.father = father;
        this.mother = mother;
    }

    /**
     * @return the father's id
     */
    public String getF() {
        return father;
    }

    /**
     * @return the mother's id
     */
    public String getM() {
        return mother;
    }
}
