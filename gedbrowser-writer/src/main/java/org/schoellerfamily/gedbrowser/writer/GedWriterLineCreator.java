package org.schoellerfamily.gedbrowser.writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.schoellerfamily.gedbrowser.datamodel.AbstractLink;
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
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Tail;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.GodClass",
        "PMD.TooManyMethods" })
public class GedWriterLineCreator implements GedObjectVisitor {
    /** The maximum length of line content. */
    private static final int MAX_LINE_LENGTH = 200;

    /** */
    private int level;

    /** */
    private final List<GedWriterLine> lines = new ArrayList<>();

    /** */
    private static final Map<String, String> MAP = new HashMap<>();
    static {
        MAP.put("Attribute", "ATTRIBUTE");
        MAP.put("Abbreviation", "ABBR");
        MAP.put("Abstractor", "ABSTRACTOR");
        MAP.put("About", "ABT");
        MAP.put("Active", "ACTIVE");
        MAP.put("Address", "ADDR");
        MAP.put("Adopted", "ADOP");
        MAP.put("Adult Baptism", "ADBAP");
        MAP.put("Ancestral File Number", "AFN");
        MAP.put("After", "AFT");
        MAP.put("Age", "AGE");
        MAP.put("Alias", "ALIA");
        MAP.put("Generations of ancestors", "ANCE");
        MAP.put("Anullment", "ANUL");
        MAP.put("Audio", "AUDIO");
        MAP.put("Author", "AUTH");
        MAP.put("Baptism", "BAPM");
        MAP.put("Bar Mitzvah", "BARM");
        MAP.put("Bat Mitzvah", "BASM");
        MAP.put("Before", "BEF");
        MAP.put("Begin", "BEGIN");
        MAP.put("Between", "BET");
        MAP.put("Birth", "BIRT");
        MAP.put("Book", "BOOK");
        MAP.put("Bris Milah", "BRIS");
        MAP.put("Burial", "BURI");
        MAP.put("Business", "BUSINESS");
        MAP.put("Canceled", "CANCELED");
        MAP.put("Card", "CARD");
        MAP.put("Caste", "CAST");
        MAP.put("Cause", "CAUS");
        MAP.put("Cemetery", "CEME");
        MAP.put("Census", "CENS");
        MAP.put("Changed", "CHAN");
        MAP.put("Character Set", "CHAR");
        MAP.put("Child", "CHIL");
        MAP.put("Christening", "CHR");
        MAP.put("Church", "CHURCH");
        MAP.put("Compiler", "COMPILER");
        MAP.put("Completed", "COMPLETED");
        MAP.put("Concatenate", "CONC");
        MAP.put("Confirmation", "CONF");
        MAP.put("Continuation", "CONT");
        MAP.put("Copy", "COPY");
        MAP.put("Court", "COURT");
        MAP.put("Date", "DATE");
        MAP.put("Death", "DEAT");
        MAP.put("Generations of descendants", "DESC");
        MAP.put("Destination", "DEST");
        MAP.put("Divorce", "DIV");
        MAP.put("Divorce Final", "DIVF");
        MAP.put("Divorced", "DIVORCED");
        MAP.put("Done", "DONE");
        MAP.put("Editor", "EDITOR");
        MAP.put("Education", "EDUC");
        MAP.put("Electronic", "ELECTRONIC");
        MAP.put("Emigration", "EMIG");
        MAP.put("Engaged", "ENGA");
        MAP.put("Event", "EVEN");
        MAP.put("Extract", "EXTRACT");
        MAP.put("Family", "FAM");
        MAP.put("Child of Family", "FAMC");
        MAP.put("Family file", "FAMF");
        MAP.put("Spouse of Family", "FAMS");
        MAP.put("Father", "FATH");
        MAP.put("Female", "FEMALE");
        MAP.put("Fiche", "FICHE");
        MAP.put("File", "FILE");
        MAP.put("Film", "FILM");
        MAP.put("Format", "FORM");
        MAP.put("Found", "FOUND");
        MAP.put("From", "FROM");
        MAP.put("GEDCOM", "GEDC");
        MAP.put("Godparent", "GODP");
        MAP.put("Government", "GOVERNMENT");
        MAP.put("Graduation", "GRAD");
        MAP.put("Header", "HEAD");
        MAP.put("Heir", "HEIR");
        MAP.put("History", "HISTORY");
        MAP.put("Husband", "HUSB");
        MAP.put("Immigration", "IMMI");
        MAP.put("Person", "INDI");
        MAP.put("Infant", "INFANT");
        MAP.put("Informant", "INFORMANT");
        MAP.put("Interview", "INTERVIEW");
        MAP.put("Interviewer", "INTERVIEWER");
        MAP.put("Issue", "ISSUE");
        MAP.put("Item", "ITEM");
        MAP.put("Journal", "JOURNAL");
        MAP.put("Language", "LANG");
        MAP.put("Letter", "LETTER");
        MAP.put("Line", "LINE");
        MAP.put("Lineage", "LINEAGE");
        MAP.put("Link", "LINK");
        MAP.put("Magazine", "MAGAZINE");
        MAP.put("Male", "MALE");
        MAP.put("Manuscript", "MANUSCRIPT");
        MAP.put("Map", "MAP");
        MAP.put("Marriage Bans", "MARB");
        MAP.put("Marriage License", "MARL");
        MAP.put("Marriage", "MARR");
        MAP.put("Married", "MARRIED");
        MAP.put("Media", "MEDI");
        MAP.put("Member", "MEMBER");
        MAP.put("Military", "MILITARY");
        MAP.put("Mother", "MOTH");
        MAP.put("Name", "NAME");
        MAP.put("Name (religious)", "NAMR");
        MAP.put("Naming", "NAMING");
        MAP.put("NAMS", "NAMS");
        MAP.put("Naturalized", "NATU");
        MAP.put("Number of Children", "NCHI");
        MAP.put("Newline", "NEWLINE");
        MAP.put("Newspaper", "NEWSPAPER");
        MAP.put("Not Married", "NMR");
        MAP.put("Note", "NOTE");
        MAP.put("Number", "NUMBER");
        MAP.put("Multimedia", "OBJE");
        MAP.put("Occupation", "OCCU");
        MAP.put("Ordinance process flag", "ORDI");
        MAP.put("Ordered", "ORDERED");
        MAP.put("Organization", "ORGANIZATION");
        MAP.put("Original", "ORIGINAL");
        MAP.put("Other", "OTHER");
        MAP.put("Page", "PAGE");
        MAP.put("Periodical", "PERIODICAL");
        MAP.put("Personal", "PERSONAL");
        MAP.put("Phone Number", "PHON");
        MAP.put("Photograph", "PHOTO");
        MAP.put("Photocopy", "PHOTOCOPY");
        MAP.put("Place", "PLAC");
        MAP.put("Planned", "PLANNED");
        MAP.put("Plot", "PLOT");
        MAP.put("Probate", "PROB");
        MAP.put("Proved", "PROVED");
        MAP.put("Published", "PUBL");
        MAP.put("Surety", "QUAY");
        MAP.put("Recited", "RECITED");
        MAP.put("Reference Number", "REFN");
        MAP.put("Religion", "RELI");
        MAP.put("Repository", "REPO");
        MAP.put("Residence", "RESI");
        MAP.put("Restriction", "RESN");
        MAP.put("Retired", "RETI");
        MAP.put("Role", "ROLE");
        MAP.put("Root", "ROOT");
        MAP.put("Sex", "SEX");
        MAP.put("Single", "SINGLE");
        MAP.put("Site", "SITE");
        MAP.put("Sound", "SOUND");
        MAP.put("Source", "SOUR");
        MAP.put("Spouse", "SPOU");
        MAP.put("Social Security Number", "SSN");
        MAP.put("Stillborn", "STILLBORN");
        MAP.put("Submitter", "SUBM");
        MAP.put("Submitted", "SUBMITTED");
        MAP.put("Submission", "SUBN");
        MAP.put("Temple code", "TEMP");
        MAP.put("Text", "TEXT");
        MAP.put("Time", "TIME");
        MAP.put("Title", "TITL");
        MAP.put("To", "TO");
        MAP.put("Token", "TOKEN");
        MAP.put("Tombstone", "TOMBSTONE");
        MAP.put("Tradition", "TRADITION");
        MAP.put("Transcriber", "TRANSCRIBER");
        MAP.put("Transcript", "TRANSCRIPT");
        MAP.put("Trailer", "TRLR");
        MAP.put("Type", "TYPE");
        MAP.put("Underscore", "UNDERSCORE");
        MAP.put("Unicode", "UNICODE");
        MAP.put("Unpublished", "UNPUBLISHED");
        MAP.put("Headstone unveiled", "UNVEIL");
        MAP.put("Version", "VERS");
        MAP.put("Video", "VIDEO");
        MAP.put("Vital", "VITAL");
        MAP.put("Widowed", "WIDOWED");
        MAP.put("Wife", "WIFE");
        MAP.put("Will", "WILL");
        MAP.put("Witness", "WITN");
        MAP.put("Cross Reference", "XREF");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attribute attribute) {
        final GedWriterLine line = new GedWriterLine(level, attribute,
                level + " " + mapTag(attribute.getString()) + tail(attribute));
        lines.add(line);
        contAndConc(attribute);
        handleChildren(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Child child) {
        createLinkLine(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Date date) {
        final GedWriterLine line = new GedWriterLine(level, date,
                level + " DATE " + date.getDate());
        lines.add(line);
        handleChildren(date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamC famc) {
        createLinkLine(famc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Family family) {
        createIdentifiedRecordLine(family, "FAM");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamS fams) {
        createLinkLine(fams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Head head) {
        final GedWriterLine line = new GedWriterLine(level, head,
                level + " HEAD");
        lines.add(line);
        handleChildren(head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Husband husband) {
        createLinkLine(husband);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Link link) {
        createLinkLine(link);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Multimedia multimedia) {
        final GedWriterLine line = new GedWriterLine(level, multimedia,
                level + " OBJE");
        lines.add(line);
        handleChildren(multimedia);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Name name) {
        final GedWriterLine line = new GedWriterLine(level, name,
                level + " NAME " + name.getString());
        lines.add(line);
        handleChildren(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Note note) {
        if (level == 0) {
            final GedWriterLine line = new GedWriterLine(level, note,
                    level + " @" + note.getString() + "@ NOTE" + tail(note));
            lines.add(line);
        } else {
            final GedWriterLine line = new GedWriterLine(level, note,
                    level + " NOTE" + tail(note));
            lines.add(line);
        }
        contAndConc(note);
        handleChildren(note);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final NoteLink noteLink) {
        createLinkLine(noteLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Person person) {
        createIdentifiedRecordLine(person, "INDI");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Place place) {
        final GedWriterLine line = new GedWriterLine(level, place,
                level + " PLAC " + place.getString());
        lines.add(line);
        handleChildren(place);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Root root) {
        final GedWriterFile file = new GedWriterFile(root);
        lines.add(file);
        level = 0;
        processHead(root);
        processSubmitters(root);
        processPersons(root);
        processFamilies(root);
        processSources(root);
        processNotes(root);
        processSubmissions(root);
        processTrailers(root);
    }

    /**
     * @param root the root of the data set
     */
    private void processHead(final Root root) {
        for (final Head head : find(root, Head.class)) {
            head.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    private void processSubmitters(final Root root) {
        final Collection<Submitter> submitters = find(root, Submitter.class);
        for (final Submitter submitter : submitters) {
            submitter.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    private void processPersons(final Root root) {
        final Collection<Person> persons = find(root, Person.class);
        for (final Person person : persons) {
            person.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    private void processFamilies(final Root root) {
        final Collection<Family> families = find(root, Family.class);
        for (final Family family : families) {
            family.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    private void processSources(final Root root) {
        final Collection<Source> sources = find(root, Source.class);
        for (final Source source : sources) {
            source.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    private void processNotes(final Root root) {
        final Collection<Note> notes = find(root, Note.class);
        for (final Note note : notes) {
            note.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    private void processSubmissions(final Root root) {
        final Collection<Submission> submissions =
                find(root, Submission.class);
        for (final Submission submission : submissions) {
            submission.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    private void processTrailers(final Root root) {
        final Collection<Trailer> trailers = find(root, Trailer.class);
        for (final Trailer trailer : trailers) {
            trailer.accept(this);
        }
    }

    /**
     * Find all of the items of a particular type in this data set and sort by
     * ID.
     * @param root the root that identifies the data set
     * @param clazz the class
     * @param <T> the data type to be compared
     * @return the collection of matching items
     */
    private <T extends GedObject> Collection<T> find(final Root root,
            final Class<T> clazz) {
        final Collection<T> collection = root.find(clazz);
        final Set<T> set = new TreeSet<>(new IdComparator<>());
        set.addAll(collection);
        return set;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Source source) {
        createIdentifiedRecordLine(source, "SOUR");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLink sourceLink) {
        createLinkLine(sourceLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Submission submission) {
        createIdentifiedRecordLine(submission, "SUBN");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmissionLink submissionLink) {
        createLinkLine(submissionLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Submitter submitter) {
        createIdentifiedRecordLine(submitter, "SUBM");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmitterLink submitterLink) {
        createLinkLine(submitterLink);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Trailer trailer) {
        final GedWriterLine line = new GedWriterLine(level, trailer,
                level + " TRLR");
        lines.add(line);
        handleChildren(trailer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Wife wife) {
        createLinkLine(wife);
    }

    /**
     * @return the collection of gedlines created by visiting the datamodel
     */
    public List<GedWriterLine> getLines() {
        return lines;
    }

    /**
     * Create line representing the base line of a record.
     *
     * @param gedObject the ged object
     * @param tag its tag
     */
    private void createIdentifiedRecordLine(final GedObject gedObject,
            final String tag) {
        final GedWriterLine line = new GedWriterLine(level, gedObject,
                level + " @" + gedObject.getString() + "@ " + tag);
        lines.add(line);
        handleChildren(gedObject);
    }

    /**
     * Create a line representing a link to another object.
     *
     * @param link the object defining in the link
     */
    private void createLinkLine(final AbstractLink link) {
        final GedWriterLine line = new GedWriterLine(level, link,
                level + " " + mapTag(link.getString())
                + " @" + link.getToString() + "@");
        lines.add(line);
        handleChildren(link);
    }

    /**
     * Get the tag that goes with this long string. Sometimes a tag is used
     * that isn't in the list. Then we just return the tag.
     *
     * @param tag the long string
     * @return the tag
     */
    private String mapTag(final String tag) {
        final String mappedTag = MAP.get(tag);
        if (mappedTag != null) {
            return mappedTag;
        }
        return tag;
    }

    /**
     * Break up the input into continuations and concatenations.
     *
     * @param tail the tail item we are processing
     */
    private void contAndConc(final Tail tail) {
        final String tailString = tail.getTail();
        level++;
        final String[] continuations = tailString.split("\n");
        concatenation(tail, continuations[0]);
        for (int i = 1; i < continuations.length; i++) {
            final GedWriterLine line =
                    createContinuationLine(tail, continuations, i);
            lines.add(line);
            concatenation(tail, continuations[i]);
        }
        level--;
    }

    /**
     * @param tail the tail object being processed
     * @param continuations the array of continuations
     * @param i the index
     * @return the line
     */
    private GedWriterLine createContinuationLine(final Tail tail,
            final String[] continuations, final int i) {
        return new GedWriterLine(level, (GedObject) tail,
                level
                + " CONT "
                + trimToMaxLength(continuations[i]));
    }

    /**
     * Break up with content at or less than 80 characters.
     *
     * @param tail the tail item we are processing
     * @param instring the input string
     */
    private void concatenation(final Tail tail, final String instring) {
        String string = instring.substring(trimToMaxLength(instring).length());
        while (!string.isEmpty()) {
            final String firstStringSegment = trimToMaxLength(string);
            final GedWriterLine line =
                    createConcatenationLine(tail, firstStringSegment);
            lines.add(line);
            string = string.substring(firstStringSegment.length());
        }
    }

    /**
     * @param tail the tail we are processing
     * @param firstStringSegment the line segment for this CONT line
     * @return the writer line
     */
    private GedWriterLine createConcatenationLine(final Tail tail,
            final String firstStringSegment) {
        return new GedWriterLine(level, (GedObject) tail,
                level + " CONC " + firstStringSegment);
    }

    /**
     * Avoid trailing space.
     *
     * @param tail the object that has a tail of
     * @return the tail string, prepended with space if not empty
     */
    private String tail(final Tail tail) {
        final String tailString = tail.getTail();
        if (tailString.isEmpty()) {
            return "";
        }
        final int lineBreakIndex = tailString.indexOf("\n");
        if (lineBreakIndex >= 0) {
            return " "
                    + trimToMaxLength(tailString.substring(0, lineBreakIndex));
        }
        return " " + trimToMaxLength(tailString);
    }

    /**
     * Return a string trimmed to the max length. Trimmed string may not end
     * space. Remainder may not start with space.
     *
     * @param string the string to trim
     * @return the trimmed string
     */
    private String trimToMaxLength(final String string) {
        if (string.length() > MAX_LINE_LENGTH) {
            for (int length = MAX_LINE_LENGTH; length > 0; length--) {
                if (string.charAt(length) != ' '
                        && string.charAt(length - 1) != ' ') {
                    return string.substring(0, length);
                }
            }
            return "";
        }
        return string;
    }

    /**
     * @param gob the current object
     */
    private void handleChildren(final GedObject gob) {
        level++;
        for (final GedObject child : gob.getAttributes()) {
            child.accept(this);
        }
        level--;
    }
}
