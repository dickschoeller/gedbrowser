package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;

/**
 * Represents has attributes for persistence operations.
 *
 * @author Richard Schoeller
 * @param <G> the associated GedObject subclass
 */
public abstract class HasAttributes<G extends GedObject>
        implements GedDocument<G> {
    /**
     * Creates a new HasAttributes.
     */
    public HasAttributes() {
    }


    /**
     * The attributes value.
     */
    private final List<GedDocument<? extends GedObject>> attributes =
        new ArrayList<>();

    /**
     * Gets the attributes.
     *
     * @return the attributes
     */
    @Override
    public final List<GedDocument<? extends GedObject>> getAttributes() {
        return attributes;
    }

    /**
     * Sets the attributes.
     *
     * @param attributes the attributes
     */
    @Override
    public final void setAttributes(
            final List<GedDocument<? extends GedObject>> attributes) {
        this.attributes.clear();
        this.attributes.addAll(attributes);
    }

    /**
     * Executes add attribute.
     *
     * @param attribute the attribute
     */
    @Override
    public final void addAttribute(final GedDocument<?> attribute) {
        attributes.add(attribute);
    }

    /**
     * Executes clear attributes.
     */
    @Override
    public final void clearAttributes() {
        attributes.clear();
    }
}
