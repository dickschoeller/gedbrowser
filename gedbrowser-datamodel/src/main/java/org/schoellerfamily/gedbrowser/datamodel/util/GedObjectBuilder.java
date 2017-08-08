package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;

/**
 * @author Dick Schoeller
 */
public final class GedObjectBuilder implements PersonBuilderFacade,
        FamilyBuilderFacade, SourceBuilderFacade, AttributeBuilderFacade {
    /** */
    private final Root root;

    /** */
    private final PersonBuilder personBuilder;

    /** */
    private final FamilyBuilder familyBuilder;

    /** */
    private final SourceBuilder sourceBuilder;

    /** */
    private final AttributeBuilder attributeBuilder;

    /**
     * Constructor.
     */
    public GedObjectBuilder() {
        this(new Root());
    }

    /**
     * Constructor.
     *
     * @param root root of the data set we're working with
     */
    public GedObjectBuilder(final Root root) {
        this.root = root;
        this.personBuilder = new PersonBuilderImpl(this);
        this.familyBuilder = new FamilyBuilderImpl(this);
        this.sourceBuilder = new SourceBuilderImpl(this);
        this.attributeBuilder = new AttributeBuilderImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Root getRoot() {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonBuilder getPersonBuilder() {
        return personBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyBuilder getFamilyBuilder() {
        return familyBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SourceBuilder getSourceBuilder() {
        return sourceBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttributeBuilder getAttributeBuilder() {
        return attributeBuilder;
    }

    /**
     * Create a trailer for the data set.
     *
     * @return the created trailer
     */
    public Trailer createTrailer() {
        final Trailer trailer = new Trailer(getRoot(), "Trailer");
        getRoot().insert(trailer);
        return trailer;
    }

    /**
     * Create a head for the data set.
     *
     * @return the created trailer
     */
    public Head createHead() {
        final Head head = new Head(getRoot(), "Head");
        getRoot().insert(head);
        return head;
    }

    /**
     * @param string the ID for the submission
     * @return the submission
     */
    public Submission createSubmission(final String string) {
        final Submission submission =
                new Submission(getRoot(), new ObjectId(string));
        getRoot().insert(submission);
        return submission;
    }

    /**
     * Create a link to the submission in the head. If head doesn't already
     * exist create it.
     *
     * @param submission
     *            the submission to link
     * @return the submission link
     */
    public SubmissionLink createSubmissionLink(final Submission submission) {
        Head head = getRoot().find("Head", Head.class);
        if (head == null) {
            head = createHead();
        }
        final SubmissionLink submissionLink =
                new SubmissionLink(head, "Submission",
                        new ObjectId(submission.getString()));
        head.insert(submissionLink);
        return submissionLink;
    }
}
