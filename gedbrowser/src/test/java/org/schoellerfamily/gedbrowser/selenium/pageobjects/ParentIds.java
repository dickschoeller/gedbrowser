package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * Represents parent ids.
 *
 * @author Richard Schoeller
 */
public final class ParentIds {
    /** */
    private final String father;
    /** */
    private final String mother;

    /**
     * Creates a new ParentIds.
     *
     * @param father the father
     * @param mother the mother
     */
    public ParentIds(final String father, final String mother) {
        this.father = father;
        this.mother = mother;
    }

    /**
     * Gets the f.
     *
     * @return the f
     */
    public String getF() {
        return father;
    }

    /**
     * Gets the m.
     *
     * @return the m
     */
    public String getM() {
        return mother;
    }
}
