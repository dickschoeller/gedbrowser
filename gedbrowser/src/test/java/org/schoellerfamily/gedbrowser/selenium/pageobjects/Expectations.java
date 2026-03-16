package org.schoellerfamily.gedbrowser.selenium.pageobjects;



/**
 * Represents expectations.
 *
 * @author Richard Schoeller
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
     * Executes expectations.
     *
     * @param title the title
     * @param fatherId the unique identifier for father
     * @param fatherString the father string
     * @param motherId the unique identifier for mother
     * @param motherString the mother string
     * @param childOneId the unique identifier for child one
     * @param leaves the leaves
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
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the father id.
     *
     * @return the father id
     */
    public String getFatherId() {
        return fatherId;
    }

    /**
     * Gets the father string.
     *
     * @return the father string
     */
    public String getFatherString() {
        return fatherString;
    }

    /**
     * Gets the mother id.
     *
     * @return the mother id
     */
    public String getMotherId() {
        return motherId;
    }

    /**
     * Gets the mother string.
     *
     * @return the mother string
     */
    public String getMotherString() {
        return motherString;
    }

    /**
     * Gets the child one id.
     *
     * @return the child one id
     */
    public String getChildOneId() {
        return childOneId;
    }

    /**
     * Gets the leaves.
     *
     * @return the leaves
     */
    public GreatGreatGrandParentIds getLeaves() {
        return leaves;
    }
}
