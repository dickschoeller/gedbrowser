package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

/**
 * @author Dick Schoeller
 */
public interface SourceBuilderFacade extends SourceBuilder {
    /**
     * @return the source builder
     */
    SourceBuilder getSourceBuilder();

    /**
     * {@inheritDoc}
     */
    @Override
    default Source createSource() {
        return getSourceBuilder().createSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Source createSource(final String string) {
        return getSourceBuilder().createSource(string);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default SourceLink createSourceLink() {
        return getSourceBuilder().createSourceLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default SourceLink createSourceLink(final GedObject ged,
            final Source source) {
        return getSourceBuilder().createSourceLink(ged, source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Submitter createSubmitter() {
        return getSourceBuilder().createSubmitter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Submitter createSubmitter(final String idString) {
        return getSourceBuilder().createSubmitter(idString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Submitter createSubmitter(final String idString,
            final String name) {
        return getSourceBuilder().createSubmitter(idString, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default SubmitterLink createSubmitterLink(final GedObject ged,
            final Submitter submitter) {
        return getSourceBuilder().createSubmitterLink(ged, submitter);
    }
}
