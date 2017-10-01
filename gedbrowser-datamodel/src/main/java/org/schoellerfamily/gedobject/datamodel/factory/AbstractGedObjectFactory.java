package org.schoellerfamily.gedobject.datamodel.factory;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * Factory that creates the right kind of GedObject based on the strings found
 * in GedLines.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize" })
public abstract class AbstractGedObjectFactory {
    /** */
    private static final AttributeFactory ATTR_FACTORY = new AttributeFactory();
    /** */
    private static final ChildFactory CHILD_FACTORY = new ChildFactory();
    /** */
    private static final ConcatenationFactory CONCAT_FACTORY =
            new ConcatenationFactory();
    /** */
    private static final ContinuationFactory CONTIN_FACTORY =
            new ContinuationFactory();
    /** */
    private static final DateFactory DATE_FACTORY = new DateFactory();
    /** */
    private static final FamCFactory FAMC_FACTORY = new FamCFactory();
    /** */
    private static final FamilyFactory FAMILY_FACTORY = new FamilyFactory();
    /** */
    private static final FamSFactory FAMS_FACTORY = new FamSFactory();
    /** */
    private static final HeadFactory HEAD_FACTORY = new HeadFactory();
    /** */
    private static final HusbandFactory HUSBAND_FACTORY = new HusbandFactory();
    /** */
    private static final LinkFactory LINK_FACTORY = new LinkFactory();
    /** */
    private static final MultimediaFactory MULTIMEDIA_FACTORY =
            new MultimediaFactory();
    /** */
    private static final NameFactory NAME_FACTORY = new NameFactory();
    /** */
    private static final NoteFactory NOTE_FACTORY = new NoteFactory();
    /** */
    private static final NoteLinkFactory NOTELINK_FACTORY =
            new NoteLinkFactory();
    /** */
    private static final PersonFactory PERSON_FACTORY = new PersonFactory();
    /** */
    private static final PlaceFactory PLACE_FACTORY = new PlaceFactory();
    /** */
    private static final RootFactory ROOT_FACTORY = new RootFactory();
    /** */
    private static final SourceFactory SOURCE_FACTORY = new SourceFactory();
    /** */
    private static final SourceLinkFactory SOURLINK_FACTORY =
            new SourceLinkFactory();
    /** */
    private static final SubmissionFactory SUBMISSION_FACTORY =
            new SubmissionFactory();
    /** */
    private static final SubmissionLinkFactory SUBNLINK_FACTORY =
            new SubmissionLinkFactory();
    /** */
    private static final SubmitterFactory SUBMITTER_FACTORY =
            new SubmitterFactory();
    /** */
    private static final SubmitterLinkFactory SUBMLINK_FACTORY =
            new SubmitterLinkFactory();
    /** */
    private static final TrailerFactory TRAILER_FACTORY = new TrailerFactory();
    /** */
    private static final WifeFactory WIFE_FACTORY = new WifeFactory();


    /**
     * The set of known GEDCOM tokens.
     */
    private static Map<String, GedToken> tokens =
            new HashMap<String, GedToken>();
    static {
        tokens.put("ATTRIBUTE", new GedToken("Attribute", ATTR_FACTORY));
        tokens.put("ABBR", new GedToken("Abbreviation", ATTR_FACTORY));
        tokens.put("ABSTRACTOR", new GedToken("Abstractor", ATTR_FACTORY));
        tokens.put("ABT", new GedToken("About", ATTR_FACTORY));
        tokens.put("ACTIVE", new GedToken("Active", ATTR_FACTORY));
        tokens.put("ADDR", new GedToken("Address", ATTR_FACTORY));
        tokens.put("ADOP", new GedToken("Adopted", ATTR_FACTORY));
        tokens.put("ADBAP", new GedToken("Adult Baptism", ATTR_FACTORY));
        tokens.put("AFN", new GedToken("Ancestral File Number", ATTR_FACTORY));
        tokens.put("AFT", new GedToken("After", ATTR_FACTORY));
        tokens.put("AGE", new GedToken("Age", ATTR_FACTORY));
        tokens.put("ALIA", new GedToken("Alias", ATTR_FACTORY));
        tokens.put("ANCE",
                new GedToken("Generations of ancestors", ATTR_FACTORY));
        tokens.put("ANUL", new GedToken("Anullment", ATTR_FACTORY));
        tokens.put("AUDIO", new GedToken("Audio", ATTR_FACTORY));
        tokens.put("AUTH", new GedToken("Author", ATTR_FACTORY));
        tokens.put("AUTHOR", new GedToken("Author", ATTR_FACTORY));
        tokens.put("BAPM", new GedToken("Baptism", ATTR_FACTORY));
        tokens.put("BARM", new GedToken("Bar Mitzvah", ATTR_FACTORY));
        tokens.put("BASM", new GedToken("Bat Mitzvah", ATTR_FACTORY));
        tokens.put("BEF", new GedToken("Before", ATTR_FACTORY));
        tokens.put("BEGIN", new GedToken("Begin", ATTR_FACTORY));
        tokens.put("BET", new GedToken("Between", ATTR_FACTORY));
        tokens.put("BIRT", new GedToken("Birth", ATTR_FACTORY));
        tokens.put("BOOK", new GedToken("Book", ATTR_FACTORY));
        tokens.put("BRIS", new GedToken("Bris Milah", ATTR_FACTORY));
        tokens.put("BURI", new GedToken("Burial", ATTR_FACTORY));
        tokens.put("BUSINESS", new GedToken("Business", ATTR_FACTORY));
        tokens.put("CANCELED", new GedToken("Canceled", ATTR_FACTORY));
        tokens.put("CARD", new GedToken("Card", ATTR_FACTORY));
        tokens.put("CAST", new GedToken("Caste", ATTR_FACTORY));
        tokens.put("CAUS", new GedToken("Cause", ATTR_FACTORY));
        tokens.put("CEME", new GedToken("Cemetery", ATTR_FACTORY));
        tokens.put("CENS", new GedToken("Census", ATTR_FACTORY));
        tokens.put("CENSUS", new GedToken("Census", ATTR_FACTORY));
        tokens.put("CHAN", new GedToken("Changed", ATTR_FACTORY));
        tokens.put("CHAR", new GedToken("Character Set", ATTR_FACTORY));
        tokens.put("CHIL", new GedToken("Child", CHILD_FACTORY));
        tokens.put("CHR", new GedToken("Christening", ATTR_FACTORY));
        tokens.put("CHURCH", new GedToken("Church", ATTR_FACTORY));
        tokens.put("COMPILER", new GedToken("Compiler", ATTR_FACTORY));
        tokens.put("COMPLETED", new GedToken("Completed", ATTR_FACTORY));
        tokens.put("CONC", new GedToken("Concatenate", CONCAT_FACTORY));
        tokens.put("CONF", new GedToken("Confirmation", ATTR_FACTORY));
        tokens.put("CONT", new GedToken("Continuation", CONTIN_FACTORY));
        tokens.put("COPY", new GedToken("Copy", ATTR_FACTORY));
        tokens.put("COURT", new GedToken("Court", ATTR_FACTORY));
        tokens.put("DATE", new GedToken("Date", DATE_FACTORY));
        tokens.put("DEAT", new GedToken("Death", ATTR_FACTORY));
        tokens.put("DESC",
                new GedToken("Generations of descendants", ATTR_FACTORY));
        tokens.put("DEST", new GedToken("Destination", ATTR_FACTORY));
        tokens.put("DIV", new GedToken("Divorce", ATTR_FACTORY));
        tokens.put("DIVF", new GedToken("Divorce Final", ATTR_FACTORY));
        tokens.put("DIVORCED", new GedToken("Divorced", ATTR_FACTORY));
        tokens.put("DONE", new GedToken("Done", ATTR_FACTORY));
        tokens.put("EDITOR", new GedToken("Editor", ATTR_FACTORY));
        tokens.put("EDUC", new GedToken("Education", ATTR_FACTORY));
        tokens.put("ELECTRONIC", new GedToken("Electronic", ATTR_FACTORY));
        tokens.put("EMIG", new GedToken("Emigration", ATTR_FACTORY));
        tokens.put("ENGA", new GedToken("Engaged", ATTR_FACTORY));
        tokens.put("EVEN", new GedToken("Event", ATTR_FACTORY));
        tokens.put("EXTRACT", new GedToken("Extract", ATTR_FACTORY));
        tokens.put("FAM", new GedToken("Family", FAMILY_FACTORY));
        tokens.put("FAMC", new GedToken("Child of Family", FAMC_FACTORY));
        tokens.put("FAMF", new GedToken("Family file", ATTR_FACTORY));
        tokens.put("FAMS", new GedToken("Spouse of Family", FAMS_FACTORY));
        tokens.put("FATH", new GedToken("Father", ATTR_FACTORY));
        tokens.put("FEMALE", new GedToken("Female", ATTR_FACTORY));
        tokens.put("FICHE", new GedToken("Fiche", ATTR_FACTORY));
        tokens.put("FILE", new GedToken("File", ATTR_FACTORY));
        tokens.put("FILM", new GedToken("Film", ATTR_FACTORY));
        tokens.put("FORM", new GedToken("Format", ATTR_FACTORY));
        tokens.put("FOUND", new GedToken("Found", ATTR_FACTORY));
        tokens.put("FROM", new GedToken("From", ATTR_FACTORY));
        tokens.put("GEDC", new GedToken("GEDCOM", ATTR_FACTORY));
        tokens.put("GODP", new GedToken("Godparent", ATTR_FACTORY));
        tokens.put("GOVERNMENT", new GedToken("Government", ATTR_FACTORY));
        tokens.put("GRAD", new GedToken("Graduation", ATTR_FACTORY));
        tokens.put("HEAD", new GedToken("Header", HEAD_FACTORY));
        tokens.put("HEIR", new GedToken("Heir", ATTR_FACTORY));
        tokens.put("HISTORY", new GedToken("History", ATTR_FACTORY));
        tokens.put("HUSB", new GedToken("Husband", HUSBAND_FACTORY));
        tokens.put("IMMI", new GedToken("Immigration", ATTR_FACTORY));
        tokens.put("INDI", new GedToken("Person", PERSON_FACTORY));
        tokens.put("INFANT", new GedToken("Infant", ATTR_FACTORY));
        tokens.put("INFORMANT", new GedToken("Informant", ATTR_FACTORY));
        tokens.put("INTERVIEW", new GedToken("Interview", ATTR_FACTORY));
        tokens.put("INTERVIEWER", new GedToken("Interviewer", ATTR_FACTORY));
        tokens.put("ISSUE", new GedToken("Issue", ATTR_FACTORY));
        tokens.put("ITEM", new GedToken("Item", ATTR_FACTORY));
        tokens.put("JOURNAL", new GedToken("Journal", ATTR_FACTORY));
        tokens.put("LANG", new GedToken("Language", ATTR_FACTORY));
        tokens.put("LETTER", new GedToken("Letter", ATTR_FACTORY));
        tokens.put("LINE", new GedToken("Line", ATTR_FACTORY));
        tokens.put("LINEAGE", new GedToken("Lineage", ATTR_FACTORY));
        tokens.put("LINK", new GedToken("Link", LINK_FACTORY));
        tokens.put("MAGAZINE", new GedToken("Magazine", ATTR_FACTORY));
        tokens.put("MALE", new GedToken("Male", ATTR_FACTORY));
        tokens.put("MANUSCRIPT", new GedToken("Manuscript", ATTR_FACTORY));
        tokens.put("MAP", new GedToken("Map", ATTR_FACTORY));
        tokens.put("MARB", new GedToken("Marriage Bans", ATTR_FACTORY));
        tokens.put("MARL", new GedToken("Marriage License", ATTR_FACTORY));
        tokens.put("MARR", new GedToken("Marriage", ATTR_FACTORY));
        tokens.put("MARRIED", new GedToken("Married", ATTR_FACTORY));
        tokens.put("MEDI", new GedToken("Media", ATTR_FACTORY));
        tokens.put("MEMBER", new GedToken("Member", ATTR_FACTORY));
        tokens.put("MILITARY", new GedToken("Military", ATTR_FACTORY));
        tokens.put("MOTH", new GedToken("Mother", ATTR_FACTORY));
        tokens.put("NAME", new GedToken("Name", NAME_FACTORY));
        tokens.put("NAMR", new GedToken("Name (religious)", ATTR_FACTORY));
        tokens.put("NAMING", new GedToken("Naming", ATTR_FACTORY));
        tokens.put("NAMS", new GedToken("NAMS", ATTR_FACTORY));
        tokens.put("NATU", new GedToken("Naturalized", ATTR_FACTORY));
        tokens.put("NCHI", new GedToken("Number of Children", ATTR_FACTORY));
        tokens.put("NEWLINE", new GedToken("Newline", ATTR_FACTORY));
        tokens.put("NEWSPAPER", new GedToken("Newspaper", ATTR_FACTORY));
        tokens.put("NMR", new GedToken("Not Married", ATTR_FACTORY));
        tokens.put("NOTE", new GedToken("Note", NOTE_FACTORY));
        tokens.put("NUMBER", new GedToken("Number", ATTR_FACTORY));
        tokens.put("OBJE", new GedToken("Multimedia", MULTIMEDIA_FACTORY));
        tokens.put("OCCU", new GedToken("Occupation", ATTR_FACTORY));
        tokens.put("ORDI",
                new GedToken("Ordinance process flag", ATTR_FACTORY));
        tokens.put("ORDERED", new GedToken("Ordered", ATTR_FACTORY));
        tokens.put("ORGANIZATION", new GedToken("Organization", ATTR_FACTORY));
        tokens.put("ORIGINAL", new GedToken("Original", ATTR_FACTORY));
        tokens.put("OTHER", new GedToken("Other", ATTR_FACTORY));
        tokens.put("PAGE", new GedToken("Page", ATTR_FACTORY));
        tokens.put("PERIODICAL", new GedToken("Periodical", ATTR_FACTORY));
        tokens.put("PERSONAL", new GedToken("Personal", ATTR_FACTORY));
        tokens.put("PHON", new GedToken("Phone Number", ATTR_FACTORY));
        tokens.put("PHOTO", new GedToken("Photograph", ATTR_FACTORY));
        tokens.put("PHOTOCOPY", new GedToken("Photocopy", ATTR_FACTORY));
        tokens.put("PLAC", new GedToken("Place", PLACE_FACTORY));
        tokens.put("PLACE", new GedToken("Place", PLACE_FACTORY));
        tokens.put("PLANNED", new GedToken("Planned", ATTR_FACTORY));
        tokens.put("PLOT", new GedToken("Plot", ATTR_FACTORY));
        tokens.put("PROB", new GedToken("Probate", ATTR_FACTORY));
        tokens.put("PROVED", new GedToken("Proved", ATTR_FACTORY));
        tokens.put("PUBL", new GedToken("Published", ATTR_FACTORY));
        tokens.put("QUAY", new GedToken("Surety", ATTR_FACTORY));
        tokens.put("RECITED", new GedToken("Recited", ATTR_FACTORY));
        tokens.put("REFN", new GedToken("Reference Number", ATTR_FACTORY));
        tokens.put("RELI", new GedToken("Religion", ATTR_FACTORY));
        tokens.put("REPO", new GedToken("Repository", ATTR_FACTORY));
        tokens.put("RESI", new GedToken("Residence", ATTR_FACTORY));
        tokens.put("RESN", new GedToken("Restriction", ATTR_FACTORY));
        tokens.put("RETI", new GedToken("Retired", ATTR_FACTORY));
        tokens.put("ROLE", new GedToken("Role", ATTR_FACTORY));
        tokens.put("ROOT", new GedToken("Root", ROOT_FACTORY));
        tokens.put("SEX", new GedToken("Sex", ATTR_FACTORY));
        tokens.put("SINGLE", new GedToken("Single", ATTR_FACTORY));
        tokens.put("SITE", new GedToken("Site", ATTR_FACTORY));
        tokens.put("SOUND", new GedToken("Sound", ATTR_FACTORY));
        tokens.put("SOUR", new GedToken("Source", SOURCE_FACTORY));
        tokens.put("SPOU", new GedToken("Spouse", ATTR_FACTORY));
        tokens.put("SSN", new GedToken("Social Security Number", ATTR_FACTORY));
        tokens.put("STILLBORN", new GedToken("Stillborn", ATTR_FACTORY));
        tokens.put("SUBM", new GedToken("Submitter", SUBMITTER_FACTORY));
        tokens.put("SUBMITTED", new GedToken("Submitted", ATTR_FACTORY));
        tokens.put("SUBN", new GedToken("Submission", SUBMISSION_FACTORY));
        tokens.put("TEMP", new GedToken("Temple code", ATTR_FACTORY));
        tokens.put("TEXT", new GedToken("Text", ATTR_FACTORY));
        tokens.put("TIME", new GedToken("Time", ATTR_FACTORY));
        tokens.put("TITL", new GedToken("Title", ATTR_FACTORY));
        tokens.put("TO", new GedToken("To", ATTR_FACTORY));
        tokens.put("TOKEN", new GedToken("Token", ATTR_FACTORY));
        tokens.put("TOMBSTONE", new GedToken("Tombstone", ATTR_FACTORY));
        tokens.put("TRADITION", new GedToken("Tradition", ATTR_FACTORY));
        tokens.put("TRANSCRIBER", new GedToken("Transcriber", ATTR_FACTORY));
        tokens.put("TRANSCRIPT", new GedToken("Transcript", ATTR_FACTORY));
        tokens.put("TRLR", new GedToken("Trailer", TRAILER_FACTORY));
        tokens.put("TYPE", new GedToken("Type", ATTR_FACTORY));
        tokens.put("UNDERSCORE", new GedToken("Underscore", ATTR_FACTORY));
        tokens.put("UNICODE", new GedToken("Unicode", ATTR_FACTORY));
        tokens.put("UNPUBLISHED", new GedToken("Unpublished", ATTR_FACTORY));
        tokens.put("UNVEIL", new GedToken("Headstone unveiled", ATTR_FACTORY));
        tokens.put("VERS", new GedToken("Version", ATTR_FACTORY));
        tokens.put("VIDEO", new GedToken("Video", ATTR_FACTORY));
        tokens.put("VITAL", new GedToken("Vital", ATTR_FACTORY));
        tokens.put("WIDOWED", new GedToken("Widowed", ATTR_FACTORY));
        tokens.put("WIFE", new GedToken("Wife", WIFE_FACTORY));
        tokens.put("WILL", new GedToken("Will", ATTR_FACTORY));
        tokens.put("WITN", new GedToken("Witness", ATTR_FACTORY));
        tokens.put("XREF", new GedToken("Cross Reference", ATTR_FACTORY));
    }

    /**
     * Associates a GEDCOM token with a full string and a factory for GedObject
     * creation.
     *
     * @author Dick Schoeller
     */
    private static class GedToken {
        /** */
        private final transient String fullString;
        /** */
        private final transient AbstractGedObjectFactory factory;

        /**
         * Token processing for GEDCOM lines.
         *
         * @param fullstring
         *            the input string.
         * @param factory
         *            the factory for GedObject creation.
         */
        GedToken(final String fullstring,
                final AbstractGedObjectFactory factory) {
            this.fullString = fullstring;
            this.factory = factory;
        }

        /**
         * @return the full string of the line.
         */
        String getFullString() {
            return fullString;
        }

        /**
         * @return the GedObject factory.
         */
        AbstractGedObjectFactory getFactory() {
            return factory;
        }
    }
    /**
     * Factory for creating Attribute.
     *
     * @author Dick Schoeller
     */
    private static class AttributeFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Attribute(parent, tag, tail);
        }
    }

    /**
     * Factory for creating Child.
     *
     * @author Dick Schoeller
     */
    private static class ChildFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Child(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for creating Date.
     *
     * @author Dick Schoeller
     */
    private static class DateFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Date(parent, tail);
        }
    }

    /**
     * Factory for creating FamC.
     *
     * @author Dick Schoeller
     */
    private static class FamCFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new FamC(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for creating Family.
     *
     * @author Dick Schoeller
     */
    private static class FamilyFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Family(parent, xref);
        }
    }

    /**
     * Factory for creating FamS.
     *
     * @author Dick Schoeller
     */
    private static class FamSFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new FamS(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for creating Head.
     *
     * @author Dick Schoeller
     */
    private static class HeadFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Head(parent, tag, tail);
        }
    }

    /**
     * Factory for creating Husband.
     *
     * @author Dick Schoeller
     */
    private static class HusbandFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Husband(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for creating Link.
     *
     * @author Dick Schoeller
     */
    private static class LinkFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Link(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for creating Multimedia reference object.
     *
     * @author Dick Schoeller
     */
    private static class MultimediaFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Multimedia(parent, tag, tail);
        }
    }

    /**
     * Factory for creating Name.
     *
     * @author Dick Schoeller
     */
    private static class NameFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Name(parent, tail);
        }
    }

    /**
     * Factory for creating Note.
     *
     * @author Dick Schoeller
     */
    private static class NoteFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            if (parent.getParent() == null) {
                return new Note(parent, xref, tail);
            } else {
                if (tail.contains("@")) {
                    return NOTELINK_FACTORY.create(parent, xref, tag, tail);
                } else {
                    return ATTR_FACTORY.create(parent, xref, tag, tail);
                }
            }
        }
    }

    /**
     * Factory for creating Note.
     *
     * @author Dick Schoeller
     */
    private static class NoteLinkFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new NoteLink(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for creating Person.
     *
     * @author Dick Schoeller
     */
    private static class PersonFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Person(parent, xref);
        }
    }

    /**
     * Factory for creating Place.
     *
     * @author Dick Schoeller
     */
    private static class PlaceFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Place(parent, tail);
        }
    }

    /**
     * Factory for creating Root.
     *
     * @author Dick Schoeller
     */
    private static class RootFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            // Root is a special case. Always has null parent.
            return new Root(tag);
        }
    }

    /**
     * Factory for creating Source.
     *
     * @author Dick Schoeller
     */
    private static class SourceFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            if (parent.getParent() == null) {
                return new Source(parent, xref);
            } else {
                if (tail.contains("@")) {
                    return SOURLINK_FACTORY.create(parent, xref, tag, tail);
                } else {
                    return ATTR_FACTORY.create(parent, xref, tag, tail);
                }
            }
        }
    }

    /**
     * Factory for creating SourceLink.
     *
     * @author Dick Schoeller
     */
    private static class SourceLinkFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new SourceLink(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for creating Submission.
     *
     * @author Dick Schoeller
     */
    private static class SubmissionFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            if (parent.getParent() == null) {
                return new Submission(parent, xref);
            } else {
                return SUBNLINK_FACTORY.create(parent, xref, tag, tail);
            }
        }
    }

    /**
     * Factory for creating SubmissionLink.
     *
     * @author Dick Schoeller
     */
    private static class SubmissionLinkFactory
            extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new SubmissionLink(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for creating Submitter.
     *
     * @author Dick Schoeller
     */
    private static class SubmitterFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            if (parent.getParent() == null) {
                return new Submitter(parent, xref);
            } else {
                return SUBMLINK_FACTORY.create(parent, xref, tag, tail);
            }
        }
    }

    /**
     * Factory for creating SubmitterLink.
     *
     * @author Dick Schoeller
     */
    private static class SubmitterLinkFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new SubmitterLink(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for creating Trailer.
     *
     * @author Dick Schoeller
     */
    private static class TrailerFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Trailer(parent, tag);
        }
    }

    /**
     * Factory for creating Wife.
     *
     * @author Dick Schoeller
     */
    private static class WifeFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            return new Wife(parent, tag, new ObjectId(tail));
        }
    }

    /**
     * Factory for concatenation lines.
     *
     * @author Dick Schoeller
     */
    private static class ConcatenationFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            parent.appendString(tail);
            return parent;
        }
    }

    /**
     * Factory for continuation lines.
     *
     * @author Dick Schoeller
     */
    private static class ContinuationFactory extends AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public final GedObject create(final GedObject parent,
                final ObjectId xref, final String tag, final String tail) {
            parent.appendString("\n" + tail);
            return parent;
        }
    }

    /**
     * Factory method creates the appropriate GedObject from the provided
     * strings.
     *
     * @param parent the parent GedObject
     * @param xref an optional ID string
     * @param tag the GEDCOM tag
     * @param tail the rest of the line
     * @return the GedObject
     */
    public final GedObject create(final GedObject parent, final String xref,
            final String tag, final String tail) {
        return getFactory(tag).create(parent, new ObjectId(xref),
                fullstring(tag), tail);
    }

    /**
     * Factory method creates the appropriate GedObject from the provided
     * strings.
     *
     * @param parent the parent GedObject
     * @param objectId an objectId
     * @param tag the GEDCOM tag
     * @param tail the rest of the line
     * @return the GedObject
     */
    public abstract GedObject create(GedObject parent, ObjectId objectId,
            String tag, String tail);

    /**
     * Get the factory for this GEDCOM tag.
     *
     * @param tag the tag.
     * @return the factory.
     */
    private AbstractGedObjectFactory getFactory(final String tag) {
        return getToken(tag).getFactory();
    }

    /**
     * Find the token processor for this tag. Defaults to attribute.
     *
     * @param tag the tag.
     * @return the token processor.
     */
    private GedToken getToken(final String tag) {
        GedToken gedToken = tokens.get(tag);
        if (gedToken == null) {
            // Any unknown token is an attribute, retaining its tag.
            gedToken = new GedToken(tag, ATTR_FACTORY);
        }
        return gedToken;
    }

    /**
     * Get the full string for this tag. If not found, return the tag.
     *
     * @param tag
     *            the tag.
     * @return the full string.
     */
    public final String fullstring(final String tag) {
        String fullstring = getToken(tag).getFullString();
        if (fullstring == null) {
            fullstring = tag;
        }
        return fullstring;
    }

    /**
     * Instantiate this factory to get at the specific factories.
     *
     * @author Dick Schoeller
     */
    public static final class GedObjectFactory extends
    AbstractGedObjectFactory {
        /**
         * {@inheritDoc}
         */
        @Override
        public GedObject create(final GedObject parent, final ObjectId xref,
                final String tag, final String tail) {
            return null;
        }
    }
}
