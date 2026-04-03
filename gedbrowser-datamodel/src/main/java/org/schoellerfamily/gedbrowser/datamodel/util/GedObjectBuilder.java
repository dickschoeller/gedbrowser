package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;

/**
 * The methods in this interface are used by the builders to create the various types of
 * objects. The builders are used by the factories to create the various types of objects.
 *
 * @author Richard Schoeller
 */
public interface GedObjectBuilder
    extends PersonBuilder, FamilyBuilder, SourceBuilder, AttributeBuilder {
    /**
     * Create a trailer for the data set.
     *
     * @return the created trailer
     */
    Trailer createTrailer();

    /**
     * Create a head for the data set.
     *
     * @return the created trailer
     */
    Head createHead();

    /**
     * Creates the submission.
     *
     * @param string the string
     * @return the resulting submission
     */
    Submission createSubmission(String string);

    /**
     * Creates the submission link.
     *
     * @param submission the submission
     * @return the resulting submission link
     */
    SubmissionLink createSubmissionLink(Submission submission);

    /**
     * Creates the event.
     *
     * @param parent the parent
     * @param type the type to use
     * @param string the event value or identifier
     * @param tail the trailing value to attach to the event
     * @return the resulting ged object
     */
    GedObject createEvent(GedObject parent, String type, String string, String tail);

    /**
     * Creates the event.
     *
     * @param parent the parent
     * @param type the type to use
     * @param string the event value or identifier
     * @return the resulting ged object
     */
    GedObject createEvent(GedObject parent, String type, String string);
}
