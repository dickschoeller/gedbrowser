package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;



/**
 * Represents head document repository mongo impl for persistence operations.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
public class HeadDocumentRepositoryMongoImpl implements
    FindableDocument<Head, HeadDocument> {
    /** */
    private final MongoTemplate mongoTemplate;
    /** */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;

    /**
     * Finds the by file and string.
     *
     * @param filename the filename to use
     * @param string the string
     * @return the resulting head document
     */
    @Override
    public final HeadDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and(FILENAME).is(filename));
        final HeadDocument headDocument =
                mongoTemplate.findOne(searchQuery, HeadDocumentMongo.class);
        if (headDocument == null) {
            return null;
        }
        final Head head =
                (Head) toObjConverter.createGedObject(null, headDocument);
        headDocument.setGedObject(head);
        return headDocument;
    }

    /**
     * Finds the by root and string.
     *
     * @param rootDocument the root document
     * @param string the string
     * @return the resulting head document
     */
    @Override
    public final HeadDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final HeadDocument headDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (headDocument == null) {
            return null;
        }
        final Head head = headDocument.getGedObject();
        head.setParent(rootDocument.getGedObject());
        return headDocument;
    }

    /**
     * Finds the all.
     *
     * @param filename the filename to use
     * @return the resulting iterable
     */
    @Override
    public final Iterable<HeadDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        final List<HeadDocumentMongo> headDocumentsMongo =
                mongoTemplate.find(searchQuery, HeadDocumentMongo.class);
        return headDocumentsMongo.stream()
            .map(headDocument -> {
                final Head head =
                        (Head) toObjConverter.createGedObject(null, headDocument);
                headDocument.setGedObject(head);
                return (HeadDocument) headDocument;
            }).toList();
    }

    /**
     * Finds the all.
     *
     * @param rootDocument the root document
     * @return the resulting iterable
     */
    @Override
    public final Iterable<HeadDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<HeadDocument> headDocuments =
                findAll(rootDocument.getFilename());
        if (headDocuments == null) {
            return null;
        }
        for (final HeadDocument headDocument : headDocuments) {
            final Head head = headDocument.getGedObject();
            head.setParent(rootDocument.getGedObject());
        }
        return headDocuments;
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
        return mongoTemplate.count(searchQuery, HeadDocumentMongo.class);
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
        return "";
    }

    /**
     * Returns the string.
     *
     * @param rootDocument the root document
     * @return the resulting string
     */
    @Override
    public final String newId(final RootDocument rootDocument) {
        return "";
    }
}
