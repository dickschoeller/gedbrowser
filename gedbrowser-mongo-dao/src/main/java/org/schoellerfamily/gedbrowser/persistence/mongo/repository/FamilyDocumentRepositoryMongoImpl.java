package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;



/**
 * Represents family document repository mongo impl for persistence operations.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
public class FamilyDocumentRepositoryMongoImpl implements
    FindableDocument<Family, FamilyDocument>, LastId<FamilyDocumentMongo> {
    /** */
    private final MongoTemplate mongoTemplate;
    /** */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;
    /**
     * Finds the by file and string.
     *
     * @param filename the filename to use
     * @param string the string
     * @return the resulting family document
     */
    @Override
    public final FamilyDocument findByFileAndString(final String filename,
            final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and(FILENAME).is(filename));
        final FamilyDocumentMongo familyDocument =
                mongoTemplate.findOne(searchQuery, FamilyDocumentMongo.class);
        if (familyDocument == null) {
            return null;
        }
        final Family family =
                (Family) toObjConverter.createGedObject(null, familyDocument);
        familyDocument.setGedObject(family);
        return familyDocument;
    }

    /**
     * Finds the by root and string.
     *
     * @param rootDocument the root document
     * @param string the string
     * @return the resulting family document
     */
    @Override
    public final FamilyDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final FamilyDocument familyDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (familyDocument == null) {
            return null;
        }
        final Family family = familyDocument.getGedObject();
        family.setParent(rootDocument.getGedObject());
        return familyDocument;
    }

    /**
     * Finds the all.
     *
     * @param filename the filename to use
     * @return the resulting iterable
     */
    @Override
    public final Iterable<FamilyDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        final List<FamilyDocumentMongo> familyDocumentsMongo =
                mongoTemplate.find(searchQuery, FamilyDocumentMongo.class);
        return familyDocumentsMongo.stream()
            .map(familyDocument -> {
                final Family family = (Family) toObjConverter.createGedObject(
                        null, familyDocument);
                familyDocument.setGedObject(family);
                return (FamilyDocument) familyDocument;
            }).toList();
    }

    /**
     * Finds the all.
     *
     * @param rootDocument the root document
     * @return the resulting iterable
     */
    @Override
    public final Iterable<FamilyDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<FamilyDocument> familyDocuments =
                findAll(rootDocument.getFilename());
        if (familyDocuments == null) {
            return null;
        }
        for (final FamilyDocument familyDocument : familyDocuments) {
            final Family family = familyDocument.getGedObject();
            family.setParent(rootDocument.getGedObject());
        }
        return familyDocuments;
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
        return mongoTemplate.count(searchQuery, FamilyDocumentMongo.class);
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
        return lastId(mongoTemplate, FamilyDocumentMongo.class,
                rootDocument.getFilename(), "F");
    }

    /**
     * Returns the string.
     *
     * @param rootDocument the root document
     * @return the resulting string
     */
    @Override
    public final String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, FamilyDocumentMongo.class,
                rootDocument.getFilename(), "F");
    }
}
