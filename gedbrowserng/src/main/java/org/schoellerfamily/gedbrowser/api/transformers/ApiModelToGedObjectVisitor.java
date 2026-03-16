package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObjectVisitor;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Visits api model to ged object elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
public final class ApiModelToGedObjectVisitor implements ApiObjectVisitor {
    /** */
    private final GedObjectBuilder builder;

    /** */
    private final GedObject parent;

    /** */
    private GedObject gedObject;

    /**
     * Creates a new ApiModelToGedObjectVisitor.
     *
     * @param builder the builder
     */
    public ApiModelToGedObjectVisitor(final GedObjectBuilder builder) {
        this.builder = builder;
        this.parent = builder.getRoot();
    }

    /**
     * Creates a new ApiModelToGedObjectVisitor.
     *
     * @param builder the builder
     * @param parent the parent
     */
    public ApiModelToGedObjectVisitor(final GedObjectBuilder builder,
            final GedObject parent) {
        this.builder = builder;
        this.parent = parent;
    }

    /**
     * Gets the ged object.
     *
     * @return the ged object
     */
    public GedObject getGedObject() {
        return gedObject;
    }

    /**
     * Executes visit.
     *
     * @param attribute the attribute
     */
    @Override
    public void visit(final ApiAttribute attribute) {
        gedObject = builder.createEvent(parent, attribute.getType(),
                attribute.getString(), attribute.getTail());
        new AttributeListHelper(this).addAttributes(attribute);
    }

    /**
     * Executes visit.
     *
     * @param baseObject the base object
     */
    @Override
    public void visit(final ApiObject baseObject) {
        gedObject = builder.createEvent(parent, baseObject.getType(),
                baseObject.getString());
        addAttributes(baseObject);
    }

    /**
     * Executes visit.
     *
     * @param family the family
     */
    @Override
    public void visit(final ApiFamily family) {
        gedObject = new Family(parent, new ObjectId(family.getString()));
        new AttributeListHelper(this).addAttributes(family);
    }

    /**
     * Executes visit.
     *
     * @param head the head
     */
    @Override
    public void visit(final ApiHead head) {
        gedObject = new Head(parent, "Head");
        addAttributes(head);
    }

    /**
     * Executes visit.
     *
     * @param note the note
     */
    @Override
    public void visit(final ApiNote note) {
        gedObject = new Note(builder.getRoot(),
                new ObjectId(note.getString()), note.getTail());
        builder.getRoot().insert(gedObject);
        addAttributes(note);
    }

    /**
     * Executes visit.
     *
     * @param person the person
     */
    @Override
    public void visit(final ApiPerson person) {
        gedObject = new Person(builder.getRoot(),
                new ObjectId(person.getString()));
        new AttributeListHelper(this).addAttributes(person);
    }

    /**
     * Executes visit.
     *
     * @param source the source
     */
    @Override
    public void visit(final ApiSource source) {
        gedObject = new Source(builder.getRoot(),
                new ObjectId(source.getString()));
        new AttributeListHelper(this).addAttributes(source);
    }

    /**
     * Executes visit.
     *
     * @param submission the submission
     */
    @Override
    public void visit(final ApiSubmission submission) {
        gedObject = new Submission(builder.getRoot(),
                new ObjectId(submission.getString()));
        addAttributes(submission);
    }

    /**
     * Executes visit.
     *
     * @param submitter the submitter
     */
    @Override
    public void visit(final ApiSubmitter submitter) {
        gedObject = new Submitter(builder.getRoot(),
                new ObjectId(submitter.getString()));
        addAttributes(submitter);
    }

    private void addAttributes(final ApiObject apiParent) {
        new AttributeListHelper(this).addAttributes(apiParent);
    }

    /* default */ ApiModelToGedObjectVisitor createVisitor() {
        return new ApiModelToGedObjectVisitor(builder, gedObject);
    }
}
