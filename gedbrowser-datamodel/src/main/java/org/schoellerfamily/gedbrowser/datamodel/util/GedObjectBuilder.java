package org.schoellerfamily.gedbrowser.datamodel.util;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;

/**
 * @author Dick Schoeller
 */
public final class GedObjectBuilder {
    /** */
    private final Root root;

    /** */
    private final PersonBuilder personBuilder;

    /** */
    private final FamilyBuilder familyBuilder;

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
        this.personBuilder = new PersonBuilder(this);
        this.familyBuilder = new FamilyBuilder(this);
    }

    /**
     * @return the associated root object
     */
    public Root getRoot() {
        return root;
    }

    /**
     * @return the associated personBuilder
     */
    public PersonBuilder getPersonBuilder() {
        return personBuilder;
    }

    /**
     * @return the associated familyBuilder
     */
    public FamilyBuilder getFamilyBuilder() {
        return familyBuilder;
    }

    /**
     * Create a submittor with the ID and name provided.
     *
     * @param idString the ID
     * @param name the name
     * @return the submittor
     */
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
     * Create a submittor with the ID provided.
     *
     * @param idString the ID
     * @return the submittor
     */
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

    /**
     * Create a place with the given name and add it to the given event.
     *
     * @param event the event
     * @param placeName the place name
     * @return the place
     */
    public Place addPlaceToEvent(final Attribute event,
            final String placeName) {
        final Place place = new Place(event, placeName);
        event.insert(place);
        return place;
    }

    /**
     * @param gob the GedObject to add the name to
     * @param string the date string
     * @return the new date object
     */
    public Date addDateToGedObject(final GedObject gob, final String string) {
        final Date date = new Date(gob, string);
        gob.insert(date);
        return date;
    }

    /**
     * @param string the source string
     * @return the source
     */
    public Source createSource(final String string) {
        final Source source = new Source(getRoot(), new ObjectId(string));
        getRoot().insert(source);
        return source;
    }

    /**
     * @param ged link from ged object
     * @param source link to source
     * @return the new link
     */
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
     * @param ged link from ged object
     * @param submittor link to submittor
     * @return the new link
     */
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

    /**
     * @param ged parent
     * @param string attribute type string
     * @return the attribute
     */
    public Attribute createAttribute(final GedObject ged, final String string) {
        if (ged == null || string == null) {
            return new Attribute();
        }
        final Attribute attribute = new Attribute(ged, string);
        ged.insert(attribute);
        return attribute;
    }

    /**
     * @param ged parent
     * @param string attribute type string
     * @param tail attribute details
     * @return the attribute
     */
    public Attribute createAttribute(final GedObject ged, final String string,
            final String tail) {
        if (ged == null || string == null || tail == null) {
            return new Attribute();
        }
        final Attribute attribute = new Attribute(ged, string, tail);
        ged.insert(attribute);
        return attribute;
    }
}
