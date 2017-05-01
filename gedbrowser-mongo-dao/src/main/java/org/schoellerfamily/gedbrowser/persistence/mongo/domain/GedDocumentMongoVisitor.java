package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

/**
 * @author Dick Schoeller
 */
public interface GedDocumentMongoVisitor
        extends TopLevelGedDocumentMongoVisitor {
    /**
     * Visit and process on an attribute document.
     *
     * @param document the document
     */
    void visit(AttributeDocumentMongo document);

    /**
     * Visit and process on a child document.
     *
     * @param document the document
     */
    void visit(ChildDocumentMongo document);

    /**
     * Visit and process on a date document.
     *
     * @param document the document
     */
    void visit(DateDocumentMongo document);

    /**
     * Visit and process on a multimedia document.
     *
     * @param document the document
     */
    void visit(MultimediaDocumentMongo document);

    /**
     * Visit and process on a name document.
     *
     * @param document the document
     */
    void visit(NameDocumentMongo document);

    /**
     * Visit and process on a FamC document.
     *
     * @param document the document
     */
    void visit(FamCDocumentMongo document);

    /**
     * Visit and process on a FamS document.
     *
     * @param document the document
     */
    void visit(FamSDocumentMongo document);

    /**
     * Visit and process on a FamS document.
     *
     * @param document the document
     */
    void visit(HusbandDocumentMongo document);

    /**
     * Visit and process on a Place document.
     *
     * @param document the document
     */
    void visit(PlaceDocumentMongo document);

    /**
     * Visit and process on a source link document.
     *
     * @param document the document
     */
    void visit(SourceLinkDocumentMongo document);

    /**
     * Visit and process on a submittor link document.
     *
     * @param document the document
     */
    void visit(SubmittorLinkDocumentMongo document);

    /**
     * Visit and process on a wife document.
     *
     * @param document the document
     */
    void visit(WifeDocumentMongo document);

    /**
     * Visit and process on a wife document.
     *
     * @param document the document
     */
    void visit(RootDocumentMongo document);
}
