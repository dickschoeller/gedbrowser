package org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert;

import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.ChildDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamCDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamSDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HusbandDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NoteLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmissionLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmitterLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.WifeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;

/**
 * @author Dick Schoeller
 */
public abstract class GedLinkDocumentMongoToGedObjectConverterVisitor
        extends TopLevelGedDocumentMongoToGedObjectVisitor
        implements GedDocumentMongoVisitor {
    /**
     * @param parent the parent object of the one being created by visiting
     */
    public GedLinkDocumentMongoToGedObjectConverterVisitor(
            final GedObject parent) {
        super(parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final ChildDocumentMongo document) {
        final Child child = new Child(getParent(), "Child",
                new ObjectId(document.getString()));
        child.setFromString(getParent().getString());
        setGedObject(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final FamCDocumentMongo document) {
        final FamC famc = new FamC(getParent(), "Child of Family",
                new ObjectId(document.getString()));
        famc.setFromString(getParent().getString());
        setGedObject(famc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final FamSDocumentMongo document) {
        final FamS fams = new FamS(getParent(), "Spouse of Family",
                new ObjectId(document.getString()));
        fams.setFromString(getParent().getString());
        setGedObject(fams);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final HusbandDocumentMongo document) {
        final Husband husband = new Husband(getParent(), "Husband",
                new ObjectId(document.getString()));
        husband.setFromString(getParent().getString());
        setGedObject(husband);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final NoteLinkDocumentMongo document) {
        setGedObject(new NoteLink(getParent(), "Note",
                new ObjectId(document.getString())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final SourceLinkDocumentMongo document) {
        setGedObject(new SourceLink(getParent(), "Source",
                new ObjectId(document.getString())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final SubmissionLinkDocumentMongo document) {
        setGedObject(new SubmissionLink(getParent(), "Submission",
                new ObjectId(document.getString())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final SubmitterLinkDocumentMongo document) {
        setGedObject(new SubmitterLink(getParent(), "Submitter",
                new ObjectId(document.getString())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void visit(final WifeDocumentMongo document) {
        final WifeDocumentMongo wifeDocument = (WifeDocumentMongo) document;
        final Wife wife = new Wife(getParent(), "Wife",
                new ObjectId(wifeDocument.getString()));
        wife.setFromString(getParent().getString());
        setGedObject(wife);
    }
}
