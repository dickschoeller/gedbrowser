package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SubmissionDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class SubmissionDocumentRepositoryMongoImpl implements
    FindableDocument<Submission, SubmissionDocument> {
    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;
    /** */
    @Autowired
    private transient GedDocumentMongoToGedObjectConverter toObjConverter;

    /**
     * {@inheritDoc}
     */
    @Override
    public final SubmissionDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final SubmissionDocument submissionDocument =
                mongoTemplate.findOne(searchQuery, SubmissionDocumentMongo.class);
        if (submissionDocument == null) {
            return null;
        }
        final Submission submission =
                (Submission) toObjConverter.createGedObject(null, submissionDocument);
        submissionDocument.setGedObject(submission);
        return submissionDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SubmissionDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final SubmissionDocument submissionDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (submissionDocument == null) {
            return null;
        }
        final Submission submission = submissionDocument.getGedObject();
        submission.setParent(rootDocument.getGedObject());
        return submissionDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<SubmissionDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<SubmissionDocumentMongo> submissionDocumentsMongo =
                mongoTemplate.find(searchQuery, SubmissionDocumentMongo.class);
        if (submissionDocumentsMongo == null) {
            return null;
        }
        final List<SubmissionDocument> submissionDocuments = new ArrayList<>();
        for (final SubmissionDocument submissionDocument : submissionDocumentsMongo) {
            final Submission submission = (Submission) toObjConverter.createGedObject(
                    null, submissionDocument);
            submissionDocument.setGedObject(submission);
            submissionDocuments.add(submissionDocument);
        }
        return submissionDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<SubmissionDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<SubmissionDocument> submissionDocuments =
                findAll(rootDocument.getFilename());
        if (submissionDocuments == null) {
            return null;
        }
        for (final SubmissionDocument submissionDocument : submissionDocuments) {
            final Submission submission = submissionDocument.getGedObject();
            submission.setParent(rootDocument.getGedObject());
        }
        return submissionDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, SubmissionDocumentMongo.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }
}
