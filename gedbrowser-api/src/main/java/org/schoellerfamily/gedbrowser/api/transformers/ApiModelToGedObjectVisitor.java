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
 * @author Dick Schoeller
 *
 */
public final class ApiModelToGedObjectVisitor implements ApiObjectVisitor {
    /** */
    private final GedObjectBuilder builder;

    /** */
    private final GedObject parent;

    /** */
    private GedObject gedObject;

    /**
     * @param builder class to assist in build GED objects
     */
    public ApiModelToGedObjectVisitor(final GedObjectBuilder builder) {
        this.builder = builder;
        this.parent = builder.getRoot();
    }

    /**
     * @param builder class to assist in build GED objects
     * @param parent the parent GED object
     */
    public ApiModelToGedObjectVisitor(final GedObjectBuilder builder,
            final GedObject parent) {
        this.builder = builder;
        this.parent = parent;
    }

    /**
     * @return the built object
     */
    public GedObject getGedObject() {
        return gedObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiAttribute attribute) {
        gedObject = builder.createEvent(parent, attribute.getType(),
                attribute.getString(), attribute.getTail());
        addAttributes(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiObject baseObject) {
        gedObject = builder.createEvent(parent, baseObject.getType(),
                baseObject.getString());
        addAttributes(baseObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiFamily family) {
        gedObject = new Family(parent, new ObjectId(family.getString()));
        addAttributes(family);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiHead head) {
        gedObject = new Head(parent, "Head");
        addAttributes(head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiNote note) {
        gedObject = new Note(builder.getRoot(),
                new ObjectId(note.getString()), note.getTail());
        builder.getRoot().insert(gedObject);
        addAttributes(note);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiPerson person) {
        gedObject = new Person(builder.getRoot(),
                new ObjectId(person.getString()));
        addAttributes(person);
    }

    /**
     * @param apiParent the parent object
     */
    private void addAttributes(final ApiPerson apiParent) {
        for (final ApiObject object : apiParent.getAttributes()) {
            final ApiModelToGedObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
        for (final ApiObject object : apiParent.getImages()) {
            final ApiModelToGedObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
        for (final ApiObject object : apiParent.getFamc()) {
            final ApiModelToGedObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
        for (final ApiObject object : apiParent.getFams()) {
            final ApiModelToGedObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
        for (final ApiObject object : apiParent.getRefn()) {
            final ApiModelToGedObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
        for (final ApiObject object : apiParent.getChanged()) {
            final ApiModelToGedObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiSource source) {
        gedObject = new Source(builder.getRoot(),
                new ObjectId(source.getString()));
        addAttributes(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiSubmission submission) {
        gedObject = new Submission(builder.getRoot(),
                new ObjectId(submission.getString()));
        addAttributes(submission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiSubmitter submitter) {
        gedObject = new Submitter(builder.getRoot(),
                new ObjectId(submitter.getString()));
        addAttributes(submitter);
    }

    /**
     * @param apiParent the parent object
     */
    private void addAttributes(final ApiObject apiParent) {
        for (final ApiObject object : apiParent.getAttributes()) {
            final ApiModelToGedObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
    }

    /**
     * @return the visitor
     */
    private ApiModelToGedObjectVisitor createVisitor() {
        return new ApiModelToGedObjectVisitor(builder, gedObject);
    }
}
