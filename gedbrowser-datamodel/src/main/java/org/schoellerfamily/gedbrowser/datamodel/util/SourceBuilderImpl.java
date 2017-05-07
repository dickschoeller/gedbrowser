package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;

/**
 * @author Dick Schoeller
 *
 */
public final class SourceBuilderImpl implements SourceBuilder {
    /** */
    private final GedObjectBuilder gedObjectBuilder;

    /**
     * Constructor.
     *
     * @param gedObjectBuilder the containing builder
     */
    public SourceBuilderImpl(final GedObjectBuilder gedObjectBuilder) {
        this.gedObjectBuilder = gedObjectBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Root getRoot() {
        return gedObjectBuilder.getRoot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Source createSource() {
        return new Source();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Source createSource(final String string) {
        final Source source = new Source(getRoot(), new ObjectId(string));
        getRoot().insert(source);
        return source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SourceLink createSourceLink() {
        return new SourceLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SourceLink createSourceLink(final GedObject ged,
            final Source source) {
        if (ged == null || source == null) {
            return new SourceLink();
        }
        final SourceLink sourceLink = new SourceLink(ged, "Source",
                new ObjectId(source.getString()));
        ged.insert(sourceLink);
        return sourceLink;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Submittor createSubmittor() {
        return new Submittor(getRoot(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Submittor createSubmittor(final String idString) {
        if (idString == null) {
            return new Submittor(getRoot(), null);
        }
        final Submittor submittor =
                new Submittor(getRoot(), new ObjectId(idString));
        getRoot().insert(submittor);
        return submittor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Submittor createSubmittor(final String idString, final String name) {
        if (idString == null || name == null) {
            return new Submittor(getRoot(), null);
        }
        final Submittor submittor =
                new Submittor(getRoot(), new ObjectId(idString));
        submittor.insert(new Name(submittor, name));
        getRoot().insert(submittor);
        return submittor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubmittorLink createSubmittorLink(final GedObject ged,
            final Submittor submittor) {
        if (ged == null || submittor == null) {
            return new SubmittorLink();
        }
        final SubmittorLink submittorLink = new SubmittorLink(ged, "Submittor",
                new ObjectId(submittor.getString()));
        ged.insert(submittorLink);
        return submittorLink;
    }
}
