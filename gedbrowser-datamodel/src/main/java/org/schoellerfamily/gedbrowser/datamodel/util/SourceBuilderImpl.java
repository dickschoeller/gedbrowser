package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

/**
 * Represents source builder impl in the domain model.
 *
 * @author Richard Schoeller
 */
public final class SourceBuilderImpl implements SourceBuilder {
    /** */
    private final GedObjectBuilder gedObjectBuilder;

    /**
     * Creates a new SourceBuilderImpl.
     *
     * @param gedObjectBuilder the ged object builder
     */
    public SourceBuilderImpl(final GedObjectBuilder gedObjectBuilder) {
        this.gedObjectBuilder = gedObjectBuilder;
    }

    /**
     * Gets the root.
     *
     * @return the root
     */
    @Override
    public Root getRoot() {
        return gedObjectBuilder.getRoot();
    }

    /**
     * Creates the source.
     *
     * @return the resulting source
     */
    @Override
    public Source createSource() {
        return new Source();
    }

    /**
     * Creates the source.
     *
     * @param string the string
     * @return the resulting source
     */
    @Override
    public Source createSource(final String string) {
        final Source source = new Source(getRoot(), new ObjectId(string));
        getRoot().insert(source);
        return source;
    }

    /**
     * Creates source link.
     *
     * @return the resulting source link
     */
    @Override
    public SourceLink createSourceLink() {
        return new SourceLink();
    }

    /**
     * Creates the source link.
     *
     * @param ged the ged
     * @param source the source
     * @return the resulting source link
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
     * Creates the submitter.
     *
     * @return the resulting submitter
     */
    @Override
    public Submitter createSubmitter() {
        return new Submitter(getRoot(), null);
    }

    /**
     * Creates the submitter.
     *
     * @param idString the id string
     * @return the resulting submitter
     */
    @Override
    public Submitter createSubmitter(final String idString) {
        if (idString == null) {
            return new Submitter(getRoot(), null);
        }
        final Submitter submitter =
                new Submitter(getRoot(), new ObjectId(idString));
        getRoot().insert(submitter);
        return submitter;
    }

    /**
     * Creates the submitter.
     *
     * @param idString the id string
     * @param name the name to use
     * @return the resulting submitter
     */
    @Override
    public Submitter createSubmitter(final String idString, final String name) {
        if (idString == null || name == null) {
            return new Submitter(getRoot(), null);
        }
        final Submitter submitter =
                new Submitter(getRoot(), new ObjectId(idString));
        submitter.insert(new Name(submitter, name));
        getRoot().insert(submitter);
        return submitter;
    }

    /**
     * Creates the submitter link.
     *
     * @param ged the ged
     * @param submitter the submitter
     * @return the resulting submitter link
     */
    @Override
    public SubmitterLink createSubmitterLink(final GedObject ged,
            final Submitter submitter) {
        if (ged == null || submitter == null) {
            return new SubmitterLink();
        }
        final SubmitterLink submitterLink = new SubmitterLink(ged, "Submitter",
                new ObjectId(submitter.getString()));
        ged.insert(submitterLink);
        return submitterLink;
    }
}
