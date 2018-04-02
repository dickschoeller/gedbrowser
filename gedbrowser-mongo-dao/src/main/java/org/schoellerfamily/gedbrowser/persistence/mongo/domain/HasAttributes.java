package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;

/**
 * @author Dick Schoeller
 *
 * @param <G> the associated GedObject subclass
 */
public abstract class HasAttributes<G extends GedObject>
        implements GedDocument<G> {

    /** */
    private final List<GedDocument<? extends GedObject>> attributes =
            new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<GedDocument<? extends GedObject>> getAttributes() {
        return attributes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setAttributes(
            final List<GedDocument<? extends GedObject>> attributes) {
        this.attributes.clear();
        this.attributes.addAll(attributes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addAttribute(final GedDocument<?> attribute) {
        attributes.add(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void clearAttributes() {
        attributes.clear();
    }
}
