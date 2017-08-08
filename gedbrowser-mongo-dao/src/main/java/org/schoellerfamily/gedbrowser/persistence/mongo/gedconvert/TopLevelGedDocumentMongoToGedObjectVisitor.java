package org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NoteDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmissionDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmitterDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;

/**
 * @author Dick Schoeller
 */
public abstract class TopLevelGedDocumentMongoToGedObjectVisitor
        implements TopLevelGedDocumentMongoVisitor {
    /**
     * The parent of the object we are going to create.
     */
    private final GedObject parent;

    /**
     * The created object.
     */
    private GedObject gedObject;

    /**
     * @param parent the parent of the object we are going to create
     */
    public TopLevelGedDocumentMongoToGedObjectVisitor(final GedObject parent) {
        this.parent = parent;
    }

    /**
     * @return the parent gedObject of the one we are going to create
     */
    public GedObject getParent() {
        return parent;
    }

    /**
     * @param gedObject the created gedObject
     */
    public void setGedObject(final GedObject gedObject) {
        this.gedObject = gedObject;
    }

    /**
     * @return the created object
     */
    public GedObject getGedObject() {
        return gedObject;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final PersonDocumentMongo document) {
        gedObject = new Person(parent, new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final FamilyDocumentMongo document) {
        gedObject = new Family(parent, new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final SourceDocumentMongo document) {
        gedObject = new Source(parent, new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final HeadDocumentMongo document) {
        gedObject = new Head(parent, "Header");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final SubmissionDocumentMongo document) {
        gedObject = new Submission(parent, new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final SubmitterDocumentMongo document) {
        gedObject = new Submitter(parent, new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final TrailerDocumentMongo document) {
        gedObject = new Trailer(parent, document.getString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final NoteDocumentMongo document) {
        gedObject = new Note(parent, new ObjectId(document.getString()),
                document.getTail());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(
            final GedDocumentMongo<? extends GedObject> document) {
        throw new PersistenceException(
                "whoops: " + document.getClass().getSimpleName());
    }
}
