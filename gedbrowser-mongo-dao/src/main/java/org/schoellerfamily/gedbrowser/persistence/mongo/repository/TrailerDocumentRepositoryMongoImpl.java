package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    TrailerDocumentMongo;
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
public class TrailerDocumentRepositoryMongoImpl implements
    FindableDocument<Trailer, TrailerDocument> {
    /** */
    private final MongoTemplate mongoTemplate;
    /** */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;

    @Override
    public final TrailerDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and(FILENAME).is(filename));
        final TrailerDocument trailerDocument =
                mongoTemplate.findOne(searchQuery, TrailerDocumentMongo.class);
        if (trailerDocument == null) {
            return null;
        }
        final Trailer trailer = (Trailer) toObjConverter.createGedObject(
                null, trailerDocument);
        trailerDocument.setGedObject(trailer);
        return trailerDocument;
    }

    @Override
    public final TrailerDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final TrailerDocument trailerDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (trailerDocument == null) {
            return null;
        }
        final Trailer trailer = trailerDocument.getGedObject();
        trailer.setParent(rootDocument.getGedObject());
        return trailerDocument;
    }

    @Override
    public final Iterable<TrailerDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        final List<TrailerDocumentMongo> trailerDocumentsMongo =
                mongoTemplate.find(searchQuery, TrailerDocumentMongo.class);
        return trailerDocumentsMongo.stream()
            .map(trailerDocument -> {
                final Trailer trailer = (Trailer) toObjConverter.createGedObject(
                        null, trailerDocument);
                trailerDocument.setGedObject(trailer);
                return (TrailerDocument) trailerDocument;
            }).toList();
    }

    @Override
    public final Iterable<TrailerDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<TrailerDocument> trailerDocuments =
                findAll(rootDocument.getFilename());
        if (trailerDocuments == null) {
            return null;
        }
        for (final TrailerDocument trailerDocument : trailerDocuments) {
            final Trailer trailer = trailerDocument.getGedObject();
            trailer.setParent(rootDocument.getGedObject());
        }
        return trailerDocuments;
    }

    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        return mongoTemplate.count(searchQuery, TrailerDocumentMongo.class);
    }

    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }

    @Override
    public final String lastId(final RootDocument rootDocument) {
        return "";
    }

    @Override
    public final String newId(final RootDocument rootDocument) {
        return "";
    }
}
