package org.schoellerfamily.gedbrowser.selenium.pageobjects;

/**
 * This class holds the expected values for a number of different
 * aspects of a person page.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
public final class Expectations {
    /** */
    private final String title;
    /** */
    private final String fatherId;
    /** */
    private final String fatherString;
    /** */
    private final String motherId;
    /** */
    private final String motherString;
    /** */
    private final String childOneId;
    /** */
    private final GreatGreatGrandParentIds leaves;

    /**
     * @param title expected title string
     * @param fatherId expected father ID string
     * @param fatherString expected father name and ID string
     * @param motherId expected mother ID string
     * @param motherString expected mother name and ID string
     * @param childOneId expected ID string of first child
     * @param leaves the set of ancestors at the leaf nodes
     */
    public Expectations(final String title, final String fatherId,
            final String fatherString, final String motherId,
            final String motherString, final String childOneId,
            final GreatGreatGrandParentIds leaves) {
        this.title = title;
        this.fatherId = fatherId;
        this.fatherString = fatherString;
        this.motherId = motherId;
        this.motherString = motherString;
        this.childOneId = childOneId;
        this.leaves = leaves;
    }

    /**
     * @return the expected title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the expected father ID
     */
    public String getFatherId() {
        return fatherId;
    }

    /**
     * @return the expected father string
     */
    public String getFatherString() {
        return fatherString;
    }

    /**
     * @return the expected mother ID
     */
    public String getMotherId() {
        return motherId;
    }

    /**
     * @return the expected mother string
     */
    public String getMotherString() {
        return motherString;
    }

    /**
     * @return the expected first child ID
     */
    public String getChildOneId() {
        return childOneId;
    }

    /**
     * @return the leaves
     */
    public GreatGreatGrandParentIds getLeaves() {
        return leaves;
    }
}
