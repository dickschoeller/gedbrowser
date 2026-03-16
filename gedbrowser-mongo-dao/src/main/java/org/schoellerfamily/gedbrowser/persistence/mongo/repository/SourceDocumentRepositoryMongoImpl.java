package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;



/**
 * Represents source document repository mongo impl for persistence operations.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
public class SourceDocumentRepositoryMongoImpl implements
    FindableDocument<Source, SourceDocument>, LastId<SourceDocumentMongo> {
    /** */
    private final MongoTemplate mongoTemplate;
    /** */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;
    /**
     * Finds the by file and string.
     *
     * @param filename the filename to use
     * @param string the string
     * @return the resulting source document
     */
    @Override
    public final SourceDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and(FILENAME).is(filename));
        final SourceDocument sourceDocument =
                mongoTemplate.findOne(searchQuery, SourceDocumentMongo.class);
        if (sourceDocument == null) {
            return null;
        }
        final Source source =
                (Source) toObjConverter.createGedObject(null, sourceDocument);
        sourceDocument.setGedObject(source);
        return sourceDocument;
    }

    /**
     * Finds the by root and string.
     *
     * @param rootDocument the root document
     * @param string the string
     * @return the resulting source document
     */
    @Override
    public final SourceDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final SourceDocument sourceDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (sourceDocument == null) {
            return null;
        }
        final Source source = sourceDocument.getGedObject();
        source.setParent(rootDocument.getGedObject());
        return sourceDocument;
    }

    /**
     * Finds the all.
     *
     * @param filename the filename to use
     * @return the resulting iterable
     */
    @Override
    public final Iterable<SourceDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        final List<SourceDocumentMongo> sourceDocumentsMongo =
                mongoTemplate.find(searchQuery, SourceDocumentMongo.class);
        return sourceDocumentsMongo.stream()
            .map(sourceDocument -> {
                final Source source = (Source) toObjConverter.createGedObject(
                        null, sourceDocument);
                sourceDocument.setGedObject(source);
                return (SourceDocument) sourceDocument;
            }).toList();
    }

    /**
     * Finds the all.
     *
     * @param rootDocument the root document
     * @return the resulting iterable
     */
    @Override
    public final Iterable<SourceDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<SourceDocument> sourceDocuments =
                findAll(rootDocument.getFilename());
        if (sourceDocuments == null) {
            return null;
        }
        for (final SourceDocument sourceDocument : sourceDocuments) {
            final Source source = sourceDocument.getGedObject();
            source.setParent(rootDocument.getGedObject());
        }
        return sourceDocuments;
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
        return mongoTemplate.count(searchQuery, SourceDocumentMongo.class);
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
        return lastId(mongoTemplate, SourceDocumentMongo.class,
                rootDocument.getFilename(), "S");
    }

    /**
     * Returns the string.
     *
     * @param rootDocument the root document
     * @return the resulting string
     */
    @Override
    public final String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, SourceDocumentMongo.class,
                rootDocument.getFilename(), "S");
    }
}
