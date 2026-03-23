package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.ChildDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.DateDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamCDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamSDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HusbandDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.MultimediaDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.NameDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteLinkDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PlaceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceLinkDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionLinkDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterLinkDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.WifeDocument;

/**
 * Visits document to api model elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
public final class DocumentToApiModelVisitor
        extends TopLevelDocumentToApiModelVisitor
        implements GedDocumentTransformerVisitor {
    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final AttributeDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .tail(document.getTail())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final ChildDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final DateDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final MultimediaDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .tail(document.getTail())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final NameDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final NoteLinkDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final FamCDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final FamSDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final HusbandDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final PlaceDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final SourceLinkDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final SubmissionLinkDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final SubmitterLinkDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final WifeDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .attributes(processAttributes(document))
                .build());
    }

    /**
     * Executes visit.
     *
     * @param document the document
     */
    @Override
    public void visit(final RootDocument document) {
        // Intentionally empty.
    }

    @Override
    public GedDocumentTransformerVisitor createVisitor() {
        return new DocumentToApiModelVisitor();
    }
}
