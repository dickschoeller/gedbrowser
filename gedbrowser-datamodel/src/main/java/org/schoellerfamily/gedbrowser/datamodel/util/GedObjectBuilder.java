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
 * @author Dick Schoeller
 */
public final class GedObjectBuilder implements PersonBuilderFacade,
        FamilyBuilderFacade, SourceBuilderFacade, AttributeBuilderFacade {
    /** */
    private final Root root;

    /** */
    private final PersonBuilder personBuilder;

    /** */
    private final FamilyBuilder familyBuilder;

    /** */
    private final SourceBuilder sourceBuilder;

    /** */
    private final AttributeBuilder attributeBuilder;

    /** */
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

    /** */
    private enum Construction {
        /** */
        attribute,
        /** */
        hasid,
        /** */
        note,
        /** */
        reference,
        /** */
        value
    };

    /** */
    private static final Map<String, Construction> CONSTRUCTION_MAP =
            new HashMap<>();
    static {
        CONSTRUCTION_MAP.put("attribute", Construction.attribute);
        CONSTRUCTION_MAP.put("child", Construction.reference);
        CONSTRUCTION_MAP.put("date", Construction.value);
        CONSTRUCTION_MAP.put("famc", Construction.reference);
        CONSTRUCTION_MAP.put("family", Construction.hasid);
        CONSTRUCTION_MAP.put("fams", Construction.reference);
        CONSTRUCTION_MAP.put("head", Construction.value);
        CONSTRUCTION_MAP.put("header", Construction.value);
        CONSTRUCTION_MAP.put("husband", Construction.reference);
        CONSTRUCTION_MAP.put("link", Construction.reference);
        CONSTRUCTION_MAP.put("multimedia", Construction.value);
        CONSTRUCTION_MAP.put("name", Construction.value);
        CONSTRUCTION_MAP.put("note", Construction.note);
        CONSTRUCTION_MAP.put("person", Construction.hasid);
        CONSTRUCTION_MAP.put("place", Construction.value);
        CONSTRUCTION_MAP.put("root", Construction.value);
        CONSTRUCTION_MAP.put("source", Construction.hasid);
        CONSTRUCTION_MAP.put("sourcelink", Construction.reference);
        CONSTRUCTION_MAP.put("submission", Construction.hasid);
        CONSTRUCTION_MAP.put("submissionlink", Construction.reference);
        CONSTRUCTION_MAP.put("submitter", Construction.hasid);
        CONSTRUCTION_MAP.put("submitterlink", Construction.reference);
        CONSTRUCTION_MAP.put("trailer", Construction.value);
        CONSTRUCTION_MAP.put("wife", Construction.reference);
    }

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
        this.personBuilder = new PersonBuilderImpl(this);
        this.familyBuilder = new FamilyBuilderImpl(this);
        this.sourceBuilder = new SourceBuilderImpl(this);
        this.attributeBuilder = new AttributeBuilderImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Root getRoot() {
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonBuilder getPersonBuilder() {
        return personBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyBuilder getFamilyBuilder() {
        return familyBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SourceBuilder getSourceBuilder() {
        return sourceBuilder;
    }

    /**
     * {@inheritDoc}
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
     * @param string the ID for the submission
     * @return the submission
     */
    public Submission createSubmission(final String string) {
        final Submission submission =
                new Submission(getRoot(), new ObjectId(string));
        getRoot().insert(submission);
        return submission;
    }

    /**
     * Create a link to the submission in the head. If head doesn't already
     * exist create it.
     *
     * @param submission
     *            the submission to link
     * @return the submission link
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
     * @param parent the object that will have this as an attribute
     * @param type the type string
     * @param string a data string
     * @param tail additional data string
     * @return the new object
     */
    public GedObject createEvent(final GedObject parent, final String type,
            final String string, final String tail) {
        String tag = TAGMAP.get(type);
        if (tag == null) {
            tag = "ATTRIBUTE";
        }
        Construction construction = CONSTRUCTION_MAP.get(type);
        if (construction == null) {
            construction = Construction.attribute;
        }
        GedObject gob;
        switch (construction) {
        case attribute:
            gob = factory.create(parent, "", string, tail);
            break;
        case hasid:
            gob = factory.create(parent, string, tag, tail);
            break;
        case reference:
            gob = factory.create(parent, "", tag, "@" + string + "@");
            break;
        case value:
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
     * @param parent the object that will have this as an attribute
     * @param type the type string
     * @param string a data string
     * @return the new object
     */
    public GedObject createEvent(final GedObject parent, final String type,
            final String string) {
        return createEvent(parent, type, string, null);
    }
}
