package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;

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
    default Submittor createSubmittor() {
        return getSourceBuilder().createSubmittor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Submittor createSubmittor(final String idString) {
        return getSourceBuilder().createSubmittor(idString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Submittor createSubmittor(final String idString,
            final String name) {
        return getSourceBuilder().createSubmittor(idString, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default SubmittorLink createSubmittorLink(final GedObject ged,
            final Submittor submittor) {
        return getSourceBuilder().createSubmittorLink(ged, submittor);
    }
}
