package org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NoteDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmissionDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmitterDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;

/**
 * @author Dick Schoeller
 */
public interface TopLevelGedDocumentMongoVisitor {
    /**
     * Visit and process on a person document.
     *
     * @param document the document
     */
    void visit(PersonDocumentMongo document);

    /**
     * Visit and process on a family document.
     *
     * @param document the document
     */
    void visit(FamilyDocumentMongo document);

    /**
     * Visit and process on a source document.
     *
     * @param document the document
     */
    void visit(SourceDocumentMongo document);

    /**
     * Visit and process on a head document.
     *
     * @param document the document
     */
    void visit(HeadDocumentMongo document);

    /**
     * Visit and process on a submission document.
     *
     * @param document the document
     */
    void visit(SubmissionDocumentMongo document);

    /**
     * Visit and process on a submitter document.
     *
     * @param document the document
     */
    void visit(SubmitterDocumentMongo document);

    /**
     * Visit and process on a trailer document.
     *
     * @param document the document
     */
    void visit(TrailerDocumentMongo document);

    /**
     * Visit and process on a note document.
     *
     * @param document the document
     */
    void visit(NoteDocumentMongo document);

    /**
     * Visit and process on a generic document.
     *
     * @param document the document
     */
    void visit(GedDocumentMongo<? extends GedObject> document);
}
