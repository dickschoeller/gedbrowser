package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class HeadDocumentRepositoryImpl implements
    FindableDocument<Head, HeadDocument> {
    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;

    @Override
    public final HeadDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final HeadDocument headDocument =
                mongoTemplate.findOne(searchQuery, HeadDocument.class);
        if (headDocument == null) {
            return null;
        }
        final Head head = (Head) GedDocumentFactory.getInstance().
                createGedObject(null, headDocument);
        headDocument.setGedObject(head);
        return headDocument;
    }

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
}
