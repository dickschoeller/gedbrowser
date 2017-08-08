package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
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
 *
 */
public class SaveVisitor implements TopLevelGedDocumentMongoVisitor {
    /** */
    private final RepositoryManagerMongo repositoryManager;

    /**
     * @param repositoryManager the repository manager
     */
    public SaveVisitor(final RepositoryManagerMongo repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final PersonDocumentMongo document) {
        repositoryManager.getPersonDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamilyDocumentMongo document) {
        repositoryManager.getFamilyDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceDocumentMongo document) {
        repositoryManager.getSourceDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final HeadDocumentMongo document) {
        repositoryManager.getHeadDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmissionDocumentMongo document) {
        repositoryManager.getSubmissionDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmitterDocumentMongo document) {
        repositoryManager.getSubmitterDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final TrailerDocumentMongo document) {
        repositoryManager.getTrailerDocumentRepository().save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final NoteDocumentMongo document) {
        final NoteDocumentRepositoryMongo noteDocumentRepository =
                repositoryManager.getNoteDocumentRepository();
        noteDocumentRepository.save(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedDocumentMongo<? extends GedObject> document) {
        // Intentionally empty.
    }
}
