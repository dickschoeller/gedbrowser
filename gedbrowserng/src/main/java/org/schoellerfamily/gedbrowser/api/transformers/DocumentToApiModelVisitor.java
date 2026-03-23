package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;

/**
 * Visits document to api model elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
public final class DocumentToApiModelVisitor implements
        RootDocumentVisitor,
        FamilyDocumentVisitor,
        HeadDocumentVisitor,
        NoteDocumentVisitor,
        PersonDocumentVisitor,
        SourceDocumentVisitor,
        SubmissionDocumentVisitor,
        SubmitterDocumentVisitor,
        TrailerDocumentVisitor,
        AttributeDocumentVisitor,
        ChildDocumentVisitor,
        DateDocumentVisitor,
        MultimediaDocumentVisitor,
        NameDocumentVisitor,
        NoteLinkDocumentVisitor,
        PlaceDocumentVisitor,
        SourceLinkDocumentVisitor,
        SubmissionLinkDocumentVisitor,
        SubmitterLinkDocumentVisitor,
        FamCDocumentVisitor,
        FamSDocumentVisitor,
        HusbandDocumentVisitor,
        WifeDocumentVisitor {
    /** The base object created. */
    private ApiObject baseObject;

    /**
     * Returns the base object.
      *
      * @return the base object
     */
    public ApiObject getBaseObject() {
        return baseObject;
    }

    /**
     * Sets the base object.
     *
     * @param baseObject the base object to set
     */
    public void setBaseObject(final ApiObject baseObject) {
        this.baseObject = baseObject;
    }

    @Override
    public DocumentToApiModelVisitor createVisitor() {
        return new DocumentToApiModelVisitor();
    }
}
