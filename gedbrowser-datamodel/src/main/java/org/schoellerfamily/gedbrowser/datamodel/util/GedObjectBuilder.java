package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;

/**
 * @author Dick Schoeller
 */
public final class GedObjectBuilder implements PersonBuilderFacade,
        FamilyBuilderFacade, SourceBuilderFacade, AttributeBuilderFacade {
    /** */
    private final Root root;

    /** */
    private final PersonBuilder personBuilder;

    /** */
    private final FamilyBuilder familyBuilder;

    /** */
    private final SourceBuilder sourceBuilder;

    /** */
    private final AttributeBuilder attributeBuilder;

    /**
     * Constructor.
     */
    public GedObjectBuilder() {
        this(new Root());
    }

    /**
     * Constructor.
     *
     * @param root root of the data set we're working with
     */
    public GedObjectBuilder(final Root root) {
        this.root = root;
        this.personBuilder = new PersonBuilderImpl(this);
        this.familyBuilder = new FamilyBuilderImpl(this);
        this.sourceBuilder = new SourceBuilderImpl(this);
        this.attributeBuilder = new AttributeBuilderImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Root getRoot() {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonBuilder getPersonBuilder() {
        return personBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyBuilder getFamilyBuilder() {
        return familyBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SourceBuilder getSourceBuilder() {
        return sourceBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttributeBuilder getAttributeBuilder() {
        return attributeBuilder;
    }

    /**
     * Create a trailer for the data set.
     *
     * @return the created trailer
     */
    public Trailer createTrailer() {
        final Trailer trailer = new Trailer(getRoot(), "Trailer");
        getRoot().insert(trailer);
        return trailer;
    }

    /**
     * Create a head for the data set.
     *
     * @return the created trailer
     */
    public Head createHead() {
        final Head head = new Head(getRoot(), "Head");
        getRoot().insert(head);
        return head;
    }
}
