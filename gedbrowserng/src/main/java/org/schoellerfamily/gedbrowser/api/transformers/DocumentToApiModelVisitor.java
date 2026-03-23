package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;

import lombok.Getter;
import lombok.Setter;

/**
 * Visits document to api model elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
@Getter
@Setter
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

    @Override
    public DocumentToApiModelVisitor createVisitor() {
        return new DocumentToApiModelVisitor();
    }
}
