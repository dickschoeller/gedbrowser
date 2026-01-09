package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmissionDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@Component
@RequiredArgsConstructor
public class SubmissionDocumentRepositoryMongoImpl implements
    FindableDocument<Submission, SubmissionDocument>,
    LastId<SubmissionDocumentMongo> {
    /** */
    private final MongoTemplate mongoTemplate;
    /** */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;
    @Override
    public final SubmissionDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final SubmissionDocument submDocument = mongoTemplate
                .findOne(searchQuery, SubmissionDocumentMongo.class);
        if (submDocument == null) {
            return null;
        }
        final Submission submission = (Submission) toObjConverter
                .createGedObject(null, submDocument);
        submDocument.setGedObject(submission);
        return submDocument;
    }

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

    @Override
    public final Iterable<SubmissionDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<SubmissionDocumentMongo> submissionDocumentsMongo =
                mongoTemplate.find(searchQuery, SubmissionDocumentMongo.class);
        return submissionDocumentsMongo.stream()
            .map(submDocument -> {
                final Submission submission = (Submission) toObjConverter
                        .createGedObject(null, submDocument);
                submDocument.setGedObject(submission);
                return (SubmissionDocument) submDocument;
            }).toList();
    }

    @Override
    public final Iterable<SubmissionDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<SubmissionDocument> submissionDocuments =
                findAll(rootDocument.getFilename());
        if (submissionDocuments == null) {
            return null;
        }
        for (final SubmissionDocument submDocument : submissionDocuments) {
            final Submission submission = submDocument.getGedObject();
            submission.setParent(rootDocument.getGedObject());
        }
        return submissionDocuments;
    }

    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, SubmissionDocumentMongo.class);
    }

    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }

    @Override
    public String lastId(final RootDocument rootDocument) {
        return lastId(mongoTemplate, SubmissionDocumentMongo.class,
                rootDocument.getFilename(), "SUBN");
    }

    @Override
    public String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, SubmissionDocumentMongo.class,
                rootDocument.getFilename(), "SUBN");
    }
}
