package org.schoellerfamily.gedobject.datamodel.factory;

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
public abstract class AbstractGedObjectFactory {
    /** */
    /* default */ static final AttributeFactory ATTR_FACTORY =
            new AttributeFactory();
    /** */
    /* default */ static final ChildFactory CHILD_FACTORY =
            new ChildFactory();
    /** */
    /* default */ static final ConcatenationFactory CONCAT_FACTORY =
            new ConcatenationFactory();
    /** */
    /* default */ static final ContinuationFactory CONTIN_FACTORY =
            new ContinuationFactory();
    /** */
    /* default */ static final DateFactory DATE_FACTORY =
            new DateFactory();
    /** */
    /* default */ static final FamCFactory FAMC_FACTORY =
            new FamCFactory();
    /** */
    /* default */ static final FamilyFactory FAMILY_FACTORY =
            new FamilyFactory();
    /** */
    /* default */ static final FamSFactory FAMS_FACTORY =
            new FamSFactory();
    /** */
    /* default */ static final HeadFactory HEAD_FACTORY =
            new HeadFactory();
    /** */
    /* default */ static final HusbandFactory HUSBAND_FACTORY =
            new HusbandFactory();
    /** */
    /* default */ static final LinkFactory LINK_FACTORY =
            new LinkFactory();
    /** */
    /* default */ static final MultimediaFactory MULTIMEDIA_FACTORY =
            new MultimediaFactory();
    /** */
    /* default */ static final NameFactory NAME_FACTORY =
            new NameFactory();
    /** */
    /* default */ static final NoteFactory NOTE_FACTORY =
            new NoteFactory();
    /** */
    /* default */ static final NoteLinkFactory NOTELINK_FACTORY =
            new NoteLinkFactory();
    /** */
    /* default */ static final PersonFactory PERSON_FACTORY =
            new PersonFactory();
    /** */
    /* default */ static final PlaceFactory PLACE_FACTORY =
            new PlaceFactory();
    /** */
    /* default */ static final RootFactory ROOT_FACTORY =
            new RootFactory();
    /** */
    /* default */ static final SourceFactory SOURCE_FACTORY =
            new SourceFactory();
    /** */
    /* default */ static final SourceLinkFactory SOURLINK_FACTORY =
            new SourceLinkFactory();
    /** */
    /* default */ static final SubmissionFactory SUBMISSION_FACTORY =
            new SubmissionFactory();
    /** */
    /* default */ static final SubmissionLinkFactory SUBNLINK_FACTORY =
            new SubmissionLinkFactory();
    /** */
    /* default */ static final SubmitterFactory SUBMITTER_FACTORY =
            new SubmitterFactory();
    /** */
    /* default */ static final SubmitterLinkFactory SUBMLINK_FACTORY =
            new SubmitterLinkFactory();
    /** */
    /* default */ static final TrailerFactory TRAILER_FACTORY =
            new TrailerFactory();
    /** */
    /* default */ static final WifeFactory WIFE_FACTORY =
            new WifeFactory();


    /**
     * The set of known GEDCOM tokens.
     */
    private static Map<String, GedToken> tokens =
            new TokenTableInitializer().getTokens();

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
            return null;
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
            return null;
        }
    }

    /**
     * Factory method creates the appropriate GedObject from provided strings.
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
     * Factory method creates the appropriate GedObject from provided strings.
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
