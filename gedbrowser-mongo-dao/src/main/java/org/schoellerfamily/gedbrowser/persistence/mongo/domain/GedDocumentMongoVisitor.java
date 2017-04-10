package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public interface GedDocumentMongoVisitor {
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
     * Visit and process on a submittor document.
     *
     * @param document the document
     */
    void visit(SubmittorDocumentMongo document);

    /**
     * Visit and process on a trailer document.
     *
     * @param document the document
     */
    void visit(TrailerDocumentMongo document);

    /**
     * Visit and process on a generic document.
     *
     * @param document the document
     */
    void visit(GedDocumentMongo<? extends GedObject> document);
}
