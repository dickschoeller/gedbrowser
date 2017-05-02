package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class FamilyDocumentRepositoryMongoImpl implements
    FindableDocument<Family, FamilyDocument> {
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
    public final FamilyDocument findByFileAndString(final String filename,
            final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public final Iterable<FamilyDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<FamilyDocumentMongo> familyDocumentsMongo =
                mongoTemplate.find(searchQuery, FamilyDocumentMongo.class);
        if (familyDocumentsMongo == null) {
            return null;
        }
        final List<FamilyDocument> familyDocuments = new ArrayList<>();
        for (final FamilyDocument familyDocument : familyDocumentsMongo) {
            final Family family = (Family) toObjConverter.createGedObject(
                    null, familyDocument);
            familyDocument.setGedObject(family);
            familyDocuments.add(familyDocument);
        }
        return familyDocuments;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, FamilyDocumentMongo.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }
}
