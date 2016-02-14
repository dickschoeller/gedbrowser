package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class FamilyDocumentRepositoryImpl implements
    FindableDocument<Family, FamilyDocument> {
    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;

    @Override
    public final FamilyDocument findByFileAndString(final String filename,
            final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final FamilyDocument familyDocument =
                mongoTemplate.findOne(searchQuery, FamilyDocument.class);
        if (familyDocument == null) {
            return null;
        }
        final Family family = (Family) GedDocumentFactory.getInstance().
                createGedObject(null, familyDocument);
        familyDocument.setGedObject(family);
        return familyDocument;
    }

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
}
