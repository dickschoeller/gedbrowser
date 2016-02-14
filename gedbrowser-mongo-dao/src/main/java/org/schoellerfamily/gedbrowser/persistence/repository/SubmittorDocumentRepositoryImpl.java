package org.schoellerfamily.gedbrowser.persistence.repository;

import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class SubmittorDocumentRepositoryImpl implements
    FindableDocument<Submittor, SubmittorDocument> {
    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;

    @Override
    public final SubmittorDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final SubmittorDocument submittorDocument =
                mongoTemplate.findOne(searchQuery, SubmittorDocument.class);
        if (submittorDocument == null) {
            return null;
        }
        final Submittor submittor =
                (Submittor) GedDocumentFactory.getInstance().
                createGedObject(null, submittorDocument);
        submittorDocument.setGedObject(submittor);
        return submittorDocument;
    }

    @Override
    public final SubmittorDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final SubmittorDocument submittorDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (submittorDocument == null) {
            return null;
        }
        final Submittor submittor = submittorDocument.getGedObject();
        submittor.setParent(rootDocument.getGedObject());
        return submittorDocument;
    }
}
