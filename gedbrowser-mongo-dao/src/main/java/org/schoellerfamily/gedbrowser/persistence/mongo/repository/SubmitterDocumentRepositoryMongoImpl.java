package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SubmitterDocumentMongo;
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
public class SubmitterDocumentRepositoryMongoImpl implements
    FindableDocument<Submitter, SubmitterDocument>,
    LastId<SubmitterDocumentMongo> {
    /** */
    private final MongoTemplate mongoTemplate;
    /** */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;
    @Override
    public final SubmitterDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final SubmitterDocument submitterDocument =
                mongoTemplate.findOne(searchQuery,
                        SubmitterDocumentMongo.class);
        if (submitterDocument == null) {
            return null;
        }
        final Submitter submitter = (Submitter) toObjConverter.createGedObject(
                null, submitterDocument);
        submitterDocument.setGedObject(submitter);
        return submitterDocument;
    }

    @Override
    public final SubmitterDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final SubmitterDocument submitterDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (submitterDocument == null) {
            return null;
        }
        final Submitter submitter = submitterDocument.getGedObject();
        submitter.setParent(rootDocument.getGedObject());
        return submitterDocument;
    }

    @Override
    public final Iterable<SubmitterDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<SubmitterDocumentMongo> submitterDocumentsMongo =
                mongoTemplate.find(searchQuery, SubmitterDocumentMongo.class);
        return submitterDocumentsMongo.stream()
            .map(submitterDocument -> {
                final Submitter submitter = (Submitter) toObjConverter
                        .createGedObject(null, submitterDocument);
                submitterDocument.setGedObject(submitter);
                return (SubmitterDocument) submitterDocument;
            }).toList();
    }

    @Override
    public final Iterable<SubmitterDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<SubmitterDocument> submitterDocuments =
                findAll(rootDocument.getFilename());
        if (submitterDocuments == null) {
            return null;
        }
        for (final SubmitterDocument submitterDocument : submitterDocuments) {
            final Submitter submitter = submitterDocument.getGedObject();
            submitter.setParent(rootDocument.getGedObject());
        }
        return submitterDocuments;
    }

    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, SubmitterDocumentMongo.class);
    }

    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }

    @Override
    public String lastId(final RootDocument rootDocument) {
        return lastId(mongoTemplate, SubmitterDocumentMongo.class,
                rootDocument.getFilename(), "SUB");
    }

    @Override
    public String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, SubmitterDocumentMongo.class,
                rootDocument.getFilename(), "SUB");
    }
}
