package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

/**
 * Provides a simplified interface for source builder operations.
 *
 * @author Richard Schoeller
 */
public interface SourceBuilderFacade extends SourceBuilder {
    /**
     * @return the source builder
     */
    SourceBuilder getSourceBuilder();

    @Override
    default Source createSource() {
        return getSourceBuilder().createSource();
    }

    @Override
    default Source createSource(final String string) {
        return getSourceBuilder().createSource(string);
    }

    @Override
    default SourceLink createSourceLink() {
        return getSourceBuilder().createSourceLink();
    }

    @Override
    default SourceLink createSourceLink(final GedObject ged,
            final Source source) {
        return getSourceBuilder().createSourceLink(ged, source);
    }

    @Override
    default Submitter createSubmitter() {
        return getSourceBuilder().createSubmitter();
    }

    @Override
    default Submitter createSubmitter(final String idString) {
        return getSourceBuilder().createSubmitter(idString);
    }

    @Override
    default Submitter createSubmitter(final String idString,
            final String name) {
        return getSourceBuilder().createSubmitter(idString, name);
    }

    @Override
    default SubmitterLink createSubmitterLink(final GedObject ged,
            final Submitter submitter) {
        return getSourceBuilder().createSubmitterLink(ged, submitter);
    }
}
