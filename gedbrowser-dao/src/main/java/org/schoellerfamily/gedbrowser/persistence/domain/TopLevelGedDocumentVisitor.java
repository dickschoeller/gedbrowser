package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public interface TopLevelGedDocumentVisitor {
    /**
     * Visit and process on a person document.
     *
     * @param document the document
     */
    void visit(PersonDocument document);

    /**
     * Visit and process on a family document.
     *
     * @param document the document
     */
    void visit(FamilyDocument document);

    /**
     * Visit and process on a source document.
     *
     * @param document the document
     */
    void visit(SourceDocument document);

    /**
     * Visit and process on a head document.
     *
     * @param document the document
     */
    void visit(HeadDocument document);

    /**
     * Visit and process on a submission document.
     *
     * @param document the document
     */
    void visit(SubmissionDocument document);

    /**
     * Visit and process on a submitter document.
     *
     * @param document the document
     */
    void visit(SubmitterDocument document);

    /**
     * Visit and process on a trailer document.
     *
     * @param document the document
     */
    void visit(TrailerDocument document);

    /**
     * Visit and process on a note document.
     *
     * @param document the document
     */
    void visit(NoteDocument document);

    /**
     * Visit and process on a generic document.
     *
     * @param document the document
     */
    void visit(GedDocument<? extends GedObject> document);
}
