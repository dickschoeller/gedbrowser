package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.ChildDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.DateDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamCDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamSDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentVisitor;
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
 * @author Dick Schoeller
 */
public final class DocumentToApiModelVisitor
        extends TopLevelDocumentToApiModelVisitor
        implements GedDocumentVisitor {
    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final AttributeDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString(), document.getTail()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ChildDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final DateDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final MultimediaDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString(), document.getTail()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final NameDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final NoteLinkDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamCDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamSDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final HusbandDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final PlaceDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLinkDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmissionLinkDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmitterLinkDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final WifeDocument document) {
        setBaseObject(new ApiAttribute(document.getType(),
                document.getString()));
        addAttributes(document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final RootDocument document) {
        // Intentionally empty.
    }
}
