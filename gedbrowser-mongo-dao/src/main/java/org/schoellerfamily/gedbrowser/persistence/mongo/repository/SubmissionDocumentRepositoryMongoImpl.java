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
 * Represents submission document repository mongo impl for persistence operations.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
public class SubmissionDocumentRepositoryMongoImpl implements
    FindableDocument<Submission, SubmissionDocument>,
    LastId<SubmissionDocumentMongo> {
    /**
     * The mongo template value.
     */
    private final MongoTemplate mongoTemplate;
    /**
     * The to obj converter value.
     */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;
    /**
     * Finds the by file and string.
     *
     * @param filename the filename to use
     * @param string the string
     * @return the resulting submission document
     */
    @Override
    public final SubmissionDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and(FILENAME).is(filename));
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

    /**
     * Finds the by root and string.
     *
     * @param rootDocument the root document
     * @param string the string
     * @return the resulting submission document
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
     * Finds the all.
     *
     * @param filename the filename to use
     * @return the resulting iterable
     */
    @Override
    public final Iterable<SubmissionDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
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

    /**
     * Finds the all.
     *
     * @param rootDocument the root document
     * @return the resulting iterable
     */
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

    /**
     * Executes count.
     *
     * @param filename the filename to use
     * @return the resulting long
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        return mongoTemplate.count(searchQuery, SubmissionDocumentMongo.class);
    }

    /**
     * Returns the long.
     *
     * @param rootDocument the root document
     * @return the resulting long
     */
    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }

    /**
     * Returns the string.
     *
     * @param rootDocument the root document
     * @return the resulting string
     */
    @Override
    public final String lastId(final RootDocument rootDocument) {
        return lastId(mongoTemplate, SubmissionDocumentMongo.class,
                rootDocument.getFilename(), "SUBN");
    }

    /**
     * Returns the string.
     *
     * @param rootDocument the root document
     * @return the resulting string
     */
    @Override
    public final String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, SubmissionDocumentMongo.class,
                rootDocument.getFilename(), "SUBN");
    }
}
