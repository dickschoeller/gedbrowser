package org.schoellerfamily.gedbrowser.datamodel.util;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedobject.datamodel.factory.AbstractGedObjectFactory.GedObjectFactory;

/**
 * Builds ged object instances.
 *
 * @author Richard Schoeller
 */
public final class GedObjectBuilder implements PersonBuilderFacade,
        FamilyBuilderFacade, SourceBuilderFacade, AttributeBuilderFacade {
    /**
     * The root value.
     */
    private final Root root;

    /**
     * The person builder value.
     */
    private final PersonBuilder personBuilder;

    /**
     * The family builder value.
     */
    private final FamilyBuilder familyBuilder;

    /**
     * The source builder value.
     */
    private final SourceBuilder sourceBuilder;

    /**
     * The attribute builder value.
     */
    private final AttributeBuilder attributeBuilder;

    /**
     * Performs ged object factory.
     */
    private final GedObjectFactory factory = new GedObjectFactory();

    /**
     * Map of strings in web service to strings of GEDCOM.
     */
    private static final Map<String, String> TAGMAP = new HashMap<>();
    static {
        TAGMAP.put("attribute", "ATTRIBUTE");
        TAGMAP.put("child", "CHIL");
        TAGMAP.put("date", "DATE");
        TAGMAP.put("famc", "FAMC");
        TAGMAP.put("family", "FAM");
        TAGMAP.put("fams", "FAMS");
        TAGMAP.put("head", "HEAD");
        TAGMAP.put("header", "HEAD");
        TAGMAP.put("husband", "HUSB");
        TAGMAP.put("link", "LINK");
        TAGMAP.put("multimedia", "OBJE");
        TAGMAP.put("name", "NAME");
        TAGMAP.put("note", "NOTE");
        TAGMAP.put("notelink", "NOTE");
        TAGMAP.put("person", "INDI");
        TAGMAP.put("place", "PLAC");
        TAGMAP.put("root", "ROOT");
        TAGMAP.put("source", "SOUR");
        TAGMAP.put("sourcelink", "SOUR");
        TAGMAP.put("submission", "SUBN");
        TAGMAP.put("submissionlink", "SUBN");
        TAGMAP.put("submitter", "SUBM");
        TAGMAP.put("submitterlink", "SUBM");
        TAGMAP.put("trailer", "TRLR");
        TAGMAP.put("wife", "WIFE");
    }

    /**
     * Represents the available ged object builder values.
     */
    private enum Construction {
        /**
         * The a t t r i b u t e value.
         */
        ATTRIBUTE,
        /**
         * The h a s i d value.
         */
        HASID,
        /**
         * The n o t e value.
         */
        NOTE,
        /**
         * The r e f e r e n c e value.
         */
        REFERENCE,
        /**
         */
        VALUE
    }

    /**
     * Represents string.
     */
    private static final Map<String, Construction> CONSTRUCTION_MAP =
            new HashMap<>();
    static {
        CONSTRUCTION_MAP.put("attribute", Construction.ATTRIBUTE);
        CONSTRUCTION_MAP.put("child", Construction.REFERENCE);
        CONSTRUCTION_MAP.put("date", Construction.VALUE);
        CONSTRUCTION_MAP.put("famc", Construction.REFERENCE);
        CONSTRUCTION_MAP.put("family", Construction.HASID);
        CONSTRUCTION_MAP.put("fams", Construction.REFERENCE);
        CONSTRUCTION_MAP.put("head", Construction.VALUE);
        CONSTRUCTION_MAP.put("header", Construction.VALUE);
        CONSTRUCTION_MAP.put("husband", Construction.REFERENCE);
        CONSTRUCTION_MAP.put("link", Construction.REFERENCE);
        CONSTRUCTION_MAP.put("multimedia", Construction.VALUE);
        CONSTRUCTION_MAP.put("name", Construction.VALUE);
        CONSTRUCTION_MAP.put("note", Construction.NOTE);
        CONSTRUCTION_MAP.put("notelink", Construction.REFERENCE);
        CONSTRUCTION_MAP.put("person", Construction.HASID);
        CONSTRUCTION_MAP.put("place", Construction.VALUE);
        CONSTRUCTION_MAP.put("root", Construction.VALUE);
        CONSTRUCTION_MAP.put("source", Construction.HASID);
        CONSTRUCTION_MAP.put("sourcelink", Construction.REFERENCE);
        CONSTRUCTION_MAP.put("submission", Construction.HASID);
        CONSTRUCTION_MAP.put("submissionlink", Construction.REFERENCE);
        CONSTRUCTION_MAP.put("submitter", Construction.HASID);
        CONSTRUCTION_MAP.put("submitterlink", Construction.REFERENCE);
        CONSTRUCTION_MAP.put("trailer", Construction.VALUE);
        CONSTRUCTION_MAP.put("wife", Construction.REFERENCE);
    }

    /**
     * Creates a new GedObjectBuilder.
     */
    public GedObjectBuilder() {
        this(new Root());
    }

    /**
     * Creates a new GedObjectBuilder.
     *
     * @param root the root
     */
    public GedObjectBuilder(final Root root) {
        this.root = root;
        this.personBuilder = new PersonBuilderImpl(this);
        this.familyBuilder = new FamilyBuilderImpl(this);
        this.sourceBuilder = new SourceBuilderImpl(this);
        this.attributeBuilder = new AttributeBuilderImpl(this);
    }

    /**
     * Gets the root.
     *
     * @return the root
     */
    @Override
    public Root getRoot() {
        return root;
    }

    /**
     * Gets the person builder.
     *
     * @return the person builder
     */
    @Override
    public PersonBuilder getPersonBuilder() {
        return personBuilder;
    }

    /**
     * Gets the family builder.
     *
     * @return the family builder
     */
    @Override
    public FamilyBuilder getFamilyBuilder() {
        return familyBuilder;
    }

    /**
     * Gets the source builder.
     *
     * @return the source builder
     */
    @Override
    public SourceBuilder getSourceBuilder() {
        return sourceBuilder;
    }

    /**
     * Gets the attribute builder.
     *
     * @return the attribute builder
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

    /**
     * Creates the submission.
     *
     * @param string the string
     * @return the resulting submission
     */
    public Submission createSubmission(final String string) {
        final Submission submission =
                new Submission(getRoot(), new ObjectId(string));
        getRoot().insert(submission);
        return submission;
    }

    /**
     * Creates the submission link.
     *
     * @param submission the submission
     * @return the resulting submission link
     */
    public SubmissionLink createSubmissionLink(final Submission submission) {
        Head head = getRoot().find("Head", Head.class);
        if (head == null) {
            head = createHead();
        }
        final SubmissionLink submissionLink =
                new SubmissionLink(head, "Submission",
                        new ObjectId(submission.getString()));
        head.insert(submissionLink);
        return submissionLink;
    }

    /**
     * Creates the event.
     *
     * @param parent the parent
     * @param type the type to use
     * @param string the event value or identifier
     * @param tail the trailing value to attach to the event
     * @return the resulting ged object
     */
    public GedObject createEvent(final GedObject parent, final String type,
            final String string, final String tail) {
        String tag = TAGMAP.get(type);
        if (tag == null) {
            tag = "ATTRIBUTE";
        }
        Construction construction = CONSTRUCTION_MAP.get(type);
        if (construction == null) {
            construction = Construction.ATTRIBUTE;
        }
        final GedObject gob;
        switch (construction) {
        case ATTRIBUTE:
            gob = factory.create(parent, "", string, tail);
            break;
        case HASID:
            gob = factory.create(parent, string, tag, tail);
            break;
        case REFERENCE:
            gob = factory.create(parent, "", tag, "@" + string + "@");
            break;
        case VALUE:
            gob = factory.create(parent, "", tag, string);
            break;
        default:
            gob = factory.create(parent, "", string, tail);
            break;
        }
        parent.insert(gob);
        return gob;
    }

    /**
     * Creates the event.
     *
     * @param parent the parent
     * @param type the type to use
     * @param string the event value or identifier
     * @return the resulting ged object
     */
    public GedObject createEvent(final GedObject parent, final String type,
            final String string) {
        return createEvent(parent, type, string, null);
    }
}
