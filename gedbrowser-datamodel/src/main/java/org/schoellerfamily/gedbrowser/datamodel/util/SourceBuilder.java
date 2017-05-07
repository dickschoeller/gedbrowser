package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;

/**
 * @author Dick Schoeller
 */
public interface SourceBuilder {
    /**
     * Get the root object of the data set.
     *
     * @return the root object
     */
    Root getRoot();

    /**
     * Create an empty source.
     *
     * @return the source
     */
    Source createSource();

    /**
     * Create a source with the provided ID.
     *
     * @param string the source string
     * @return the source
     */
    Source createSource(String string);

    /**
     * Create an empty link to a source.
     *
     * @return the new link
     */
    SourceLink createSourceLink();

    /**
     * Create a link from the ged object to the provided source.
     *
     * @param ged link from ged object
     * @param source link to source
     * @return the new link
     */
    SourceLink createSourceLink(GedObject ged, Source source);

    /**
     * Create empty submittor.
     *
     * @return the submittor
     */
    Submittor createSubmittor();

    /**
     * Create a submittor with the ID provided.
     *
     * @param idString the ID
     * @return the submittor
     */
    Submittor createSubmittor(String idString);

    /**
     * Create a submittor with the ID and name provided.
     *
     * @param idString the ID
     * @param name the name
     * @return the submittor
     */
    Submittor createSubmittor(String idString, String name);


    /**
     * Create a link from the ged object to the submittor.
     *
     * @param ged link from ged object
     * @param submittor link to submittor
     * @return the new link
     */
    SubmittorLink createSubmittorLink(GedObject ged, Submittor submittor);
}
