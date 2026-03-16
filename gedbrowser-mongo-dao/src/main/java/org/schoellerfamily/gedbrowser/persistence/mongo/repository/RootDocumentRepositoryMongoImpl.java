package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 *
 */
@RequiredArgsConstructor
public class RootDocumentRepositoryMongoImpl implements
    FindableDocument<Root, RootDocument> {
    /** */
    private final MongoTemplate mongoTemplate;
    /** */
    private final FinderStrategy finder;
    /** */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;

    /**
     * Finds the by file and string.
     *
     * @param filename the filename to use
     * @param string the string
     * @return the resulting root document
     */
    @Override
    public final RootDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and(FILENAME).is(filename));
        final RootDocument rootDocument = mongoTemplate.findOne(searchQuery,
                RootDocumentMongo.class);
        if (rootDocument == null) {
            return null;
        }
        final Root root =
                (Root) toObjConverter.createRoot(rootDocument, finder);
        rootDocument.setGedObject(root);
        return rootDocument;
    }

    /**
     * Finds the by root and string.
     *
     * @param rootDocument the root document
     * @param string the string
     * @return the resulting root document
     */
    @Override
    public final RootDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        throw new IllegalArgumentException(
                "Not implementable for this document type");
    }

    /**
     * Finds the all.
     *
     * @param filename the filename to use
     * @return the resulting iterable
     */
    @Override
    public final Iterable<RootDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        final List<RootDocumentMongo> rootDocumentsMongo =
                mongoTemplate.find(searchQuery, RootDocumentMongo.class);
        return rootDocumentsMongo.stream()
            .map(rootDocument -> {
                final Root root =
                        (Root) toObjConverter.createGedObject(null, rootDocument);
                rootDocument.setGedObject(root);
                return (RootDocument) rootDocument;
            }).toList();
    }

    /**
     * Finds the all.
     *
     * @param rootDocument the root document
     * @return the resulting iterable
     */
    @Override
    public final Iterable<RootDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<RootDocument> rootDocuments =
                findAll(rootDocument.getFilename());
        if (rootDocuments == null) {
            return null;
        }
        for (final RootDocument rootDocumentOut : rootDocuments) {
            final Root root = rootDocumentOut.getGedObject();
            root.setParent(rootDocumentOut.getGedObject());
        }
        return rootDocuments;
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
        return mongoTemplate.count(searchQuery, RootDocumentMongo.class);
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
