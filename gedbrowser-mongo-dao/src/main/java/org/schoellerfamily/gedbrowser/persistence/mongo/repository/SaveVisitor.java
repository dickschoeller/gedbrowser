package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmittorDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;

/**
 * @author Dick Schoeller
 *
 */
public class SaveVisitor implements GedDocumentMongoVisitor {
    /** */
    private final RepositoryFinderMongo finder;

    /**
     * @param finder the finder
     */
    public SaveVisitor(final RepositoryFinderMongo finder) {
        this.finder = finder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final PersonDocumentMongo document) {
        finder.getPersonDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamilyDocumentMongo document) {
        finder.getFamilyDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceDocumentMongo document) {
        finder.getSourceDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final HeadDocumentMongo document) {
        finder.getHeadDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorDocumentMongo document) {
        finder.getSubmittorDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final TrailerDocumentMongo document) {
        finder.getTrailerDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedDocumentMongo<? extends GedObject> document) {
        // Intentionally empty.
    }
}
