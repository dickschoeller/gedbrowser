package org.schoellerfamily.gedbrowser.persistence.domain;


/**
 * @author Dick Schoeller
 */
public interface GedDocumentVisitor extends TopLevelGedDocumentVisitor {
    /**
     * Visit and process on an attribute document.
     *
     * @param document the document
     */
    void visit(AttributeDocument document);

    /**
     * Visit and process on a child document.
     *
     * @param document the document
     */
    void visit(ChildDocument document);

    /**
     * Visit and process on a date document.
     *
     * @param document the document
     */
    void visit(DateDocument document);

    /**
     * Visit and process on a multimedia document.
     *
     * @param document the document
     */
    void visit(MultimediaDocument document);

    /**
     * Visit and process on a name document.
     *
     * @param document the document
     */
    void visit(NameDocument document);

    /**
     * Visit and process on a name document.
     *
     * @param document the document
     */
    void visit(NoteLinkDocument document);

    /**
     * Visit and process on a FamC document.
     *
     * @param document the document
     */
    void visit(FamCDocument document);

    /**
     * Visit and process on a FamS document.
     *
     * @param document the document
     */
    void visit(FamSDocument document);

    /**
     * Visit and process on a FamS document.
     *
     * @param document the document
     */
    void visit(HusbandDocument document);

    /**
     * Visit and process on a Place document.
     *
     * @param document the document
     */
    void visit(PlaceDocument document);

    /**
     * Visit and process on a source link document.
     *
     * @param document the document
     */
    void visit(SourceLinkDocument document);

    /**
     * Visit and process on a submission link document.
     *
     * @param document the document
     */
    void visit(SubmissionLinkDocument document);

    /**
     * Visit and process on a submitter link document.
     *
     * @param document the document
     */
    void visit(SubmitterLinkDocument document);

    /**
     * Visit and process on a wife document.
     *
     * @param document the document
     */
    void visit(WifeDocument document);

    /**
     * Visit and process on a wife document.
     *
     * @param document the document
     */
    void visit(RootDocument document);
}
