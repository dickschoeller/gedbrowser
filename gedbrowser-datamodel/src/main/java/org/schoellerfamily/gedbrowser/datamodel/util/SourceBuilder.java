package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

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
     * Create empty submitter.
     *
     * @return the submitter
     */
    Submitter createSubmitter();

    /**
     * Create a submitter with the ID provided.
     *
     * @param idString the ID
     * @return the submitter
     */
    Submitter createSubmitter(String idString);

    /**
     * Create a submitter with the ID and name provided.
     *
     * @param idString the ID
     * @param name the name
     * @return the submitter
     */
    Submitter createSubmitter(String idString, String name);


    /**
     * Create a link from the ged object to the submitter.
     *
     * @param ged link from ged object
     * @param submitter link to submitter
     * @return the new link
     */
    SubmitterLink createSubmitterLink(GedObject ged, Submitter submitter);
}
