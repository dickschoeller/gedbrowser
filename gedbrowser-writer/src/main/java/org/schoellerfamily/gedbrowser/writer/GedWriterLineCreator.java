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
 *
 */
public class GedWriterLineCreator implements GedObjectVisitor {
    /** */
    private int level;

    /** */
    private final List<GedWriterLine> lines = new ArrayList<>();

    /** */
    private static final Map<String, String> map = new HashMap<>();
    static {
        map.put("Attribute", "ATTRIBUTE");
        map.put("Abbreviation", "ABBR");
        map.put("Abstractor", "ABSTRACTOR");
        map.put("About", "ABT");
        map.put("Active", "ACTIVE");
        map.put("Address", "ADDR");
        map.put("Adopted", "ADOP");
        map.put("Adult Baptism", "ADBAP");
        map.put("Ancestral File Number", "AFN");
        map.put("After", "AFT");
        map.put("Age", "AGE");
        map.put("Alias", "ALIA");
        map.put("Generations of ancestors", "ANCE");
        map.put("Anullment", "ANUL");
        map.put("Audio", "AUDIO");
        map.put("Author", "AUTH");
        map.put("Author", "AUTHOR");
        map.put("Baptism", "BAPM");
        map.put("Bar Mitzvah", "BARM");
        map.put("Bat Mitzvah", "BASM");
        map.put("Before", "BEF");
        map.put("Begin", "BEGIN");
        map.put("Between", "BET");
        map.put("Birth", "BIRT");
        map.put("Book", "BOOK");
        map.put("Bris Milah", "BRIS");
        map.put("Burial", "BURI");
        map.put("Business", "BUSINESS");
        map.put("Canceled", "CANCELED");
        map.put("Card", "CARD");
        map.put("Caste", "CAST");
        map.put("Cause", "CAUS");
        map.put("Cemetery", "CEME");
        map.put("Census", "CENS");
        map.put("Census", "CENSUS");
        map.put("Changed", "CHAN");
        map.put("Character Set", "CHAR");
        map.put("Child", "CHIL");
        map.put("Christening", "CHR");
        map.put("Church", "CHURCH");
        map.put("Compiler", "COMPILER");
        map.put("Completed", "COMPLETED");
        map.put("Concatenate", "CONC");
        map.put("Confirmation", "CONF");
        map.put("Continuation", "CONT");
        map.put("Copy", "COPY");
        map.put("Court", "COURT");
        map.put("Date", "DATE");
        map.put("Death", "DEAT");
        map.put("Generations of descendants", "DESC");
        map.put("Destination", "DEST");
        map.put("Divorce", "DIV");
        map.put("Divorce Final", "DIVF");
        map.put("Divorced", "DIVORCED");
        map.put("Done", "DONE");
        map.put("Editor", "EDITOR");
        map.put("Education", "EDUC");
        map.put("Electronic", "ELECTRONIC");
        map.put("Emigration", "EMIG");
        map.put("Engaged", "ENGA");
        map.put("Event", "EVEN");
        map.put("Extract", "EXTRACT");
        map.put("Family", "FAM");
        map.put("Child of Family", "FAMC");
        map.put("Family file", "FAMF");
        map.put("Spouse of Family", "FAMS");
        map.put("Father", "FATH");
        map.put("Female", "FEMALE");
        map.put("Fiche", "FICHE");
        map.put("File", "FILE");
        map.put("Film", "FILM");
        map.put("Format", "FORM");
        map.put("Found", "FOUND");
        map.put("From", "FROM");
        map.put("GEDCOM", "GEDC");
        map.put("Godparent", "GODP");
        map.put("Government", "GOVERNMENT");
        map.put("Graduation", "GRAD");
        map.put("Header", "HEAD");
        map.put("Heir", "HEIR");
        map.put("History", "HISTORY");
        map.put("Husband", "HUSB");
        map.put("Immigration", "IMMI");
        map.put("Person", "INDI");
        map.put("Infant", "INFANT");
        map.put("Informant", "INFORMANT");
        map.put("Interview", "INTERVIEW");
        map.put("Interviewer", "INTERVIEWER");
        map.put("Issue", "ISSUE");
        map.put("Item", "ITEM");
        map.put("Journal", "JOURNAL");
        map.put("Language", "LANG");
        map.put("Letter", "LETTER");
        map.put("Line", "LINE");
        map.put("Lineage", "LINEAGE");
        map.put("Link", "LINK");
        map.put("Magazine", "MAGAZINE");
        map.put("Male", "MALE");
        map.put("Manuscript", "MANUSCRIPT");
        map.put("Map", "MAP");
        map.put("Marriage Bans", "MARB");
        map.put("Marriage License", "MARL");
        map.put("Marriage", "MARR");
        map.put("Married", "MARRIED");
        map.put("Media", "MEDI");
        map.put("Member", "MEMBER");
        map.put("Military", "MILITARY");
        map.put("Mother", "MOTH");
        map.put("Name", "NAME");
        map.put("Name (religious)", "NAMR");
        map.put("Naming", "NAMING");
        map.put("NAMS", "NAMS");
        map.put("Naturalized", "NATU");
        map.put("Number of Children", "NCHI");
        map.put("Newline", "NEWLINE");
        map.put("Newspaper", "NEWSPAPER");
        map.put("Not Married", "NMR");
        map.put("Note", "NOTE");
        map.put("Number", "NUMBER");
        map.put("Multimedia", "OBJE");
        map.put("Occupation", "OCCU");
        map.put("Ordinance process flag", "ORDI");
        map.put("Ordered", "ORDERED");
        map.put("Organization", "ORGANIZATION");
        map.put("Original", "ORIGINAL");
        map.put("Other", "OTHER");
        map.put("Page", "PAGE");
        map.put("Periodical", "PERIODICAL");
        map.put("Personal", "PERSONAL");
        map.put("Phone Number", "PHON");
        map.put("Photograph", "PHOTO");
        map.put("Photocopy", "PHOTOCOPY");
        map.put("Place", "PLAC");
        map.put("Planned", "PLANNED");
        map.put("Plot", "PLOT");
        map.put("Probate", "PROB");
        map.put("Proved", "PROVED");
        map.put("Published", "PUBL");
        map.put("Surety", "QUAY");
        map.put("Recited", "RECITED");
        map.put("Reference Number", "REFN");
        map.put("Religion", "RELI");
        map.put("Repository", "REPO");
        map.put("Residence", "RESI");
        map.put("Restriction", "RESN");
        map.put("Retired", "RETI");
        map.put("Role", "ROLE");
        map.put("Root", "ROOT");
        map.put("Sex", "SEX");
        map.put("Single", "SINGLE");
        map.put("Site", "SITE");
        map.put("Sound", "SOUND");
        map.put("Source", "SOUR");
        map.put("Spouse", "SPOU");
        map.put("Social Security Number", "SSN");
        map.put("Stillborn", "STILLBORN");
        map.put("Submitter", "SUBM");
        map.put("Submitted", "SUBMITTED");
        map.put("Submission", "SUBN");
        map.put("Temple code", "TEMP");
        map.put("Text", "TEXT");
        map.put("Time", "TIME");
        map.put("Title", "TITL");
        map.put("To", "TO");
        map.put("Token", "TOKEN");
        map.put("Tombstone", "TOMBSTONE");
        map.put("Tradition", "TRADITION");
        map.put("Transcriber", "TRANSCRIBER");
        map.put("Transcript", "TRANSCRIPT");
        map.put("Trailer", "TRLR");
        map.put("Type", "TYPE");
        map.put("Underscore", "UNDERSCORE");
        map.put("Unicode", "UNICODE");
        map.put("Unpublished", "UNPUBLISHED");
        map.put("Headstone unveiled", "UNVEIL");
        map.put("Version", "VERS");
        map.put("Video", "VIDEO");
        map.put("Vital", "VITAL");
        map.put("Widowed", "WIDOWED");
        map.put("Wife", "WIFE");
        map.put("Will", "WILL");
        map.put("Witness", "WITN");
        map.put("Cross Reference", "XREF");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attribute attribute) {
        final GedWriterLine line = new GedWriterLine(level, attribute,
                level + " " + mapTag(attribute.getString()) + tail(attribute));
        lines.add(line);
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
        final GedWriterLine line = new GedWriterLine(level, note,
                level + " @" + note.getString() + "@ NOTE" + tail(note));
        lines.add(line);
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
        for (final Head head : find(root, Head.class)) {
            head.accept(this);
        }
        final Collection<Person> persons = find(root, Person.class);
        for (final Person person : persons) {
            person.accept(this);
        }
        final Collection<Family> families = find(root, Family.class);
        for (final Family family : families) {
            family.accept(this);
        }
        final Collection<Source> sources = find(root, Source.class);
        for (final Source source : sources) {
            source.accept(this);
        }
        final Collection<Note> notes = find(root, Note.class);
        for (final Note note : notes) {
            note.accept(this);
        }
        final Collection<Submitter> submitters = find(root, Submitter.class);
        for (final Submitter submitter : submitters) {
            submitter.accept(this);
        }
        final Collection<Submission> submissions = find(root, Submission.class);
        for (final Submission submission : submissions) {
            submission.accept(this);
        }
        final Collection<Trailer> trailers = find(root, Trailer.class);
        for (final Trailer trailer : trailers) {
            trailer.accept(this);
        }
    }

    private <T extends GedObject> Collection<T> find(final Root root, final Class<T> clazz) {
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
    private void createIdentifiedRecordLine(final GedObject gedObject, final String tag) {
        final GedWriterLine line = new GedWriterLine(level, gedObject,
                level + " @" + gedObject.getString() + "@ " + tag);
        lines.add(line);
        handleChildren(gedObject);
    }

    /**
     * Create a line representing a link to another object.
     *
     * @param link the object defining in the link
     * @return the line
     */
    private void createLinkLine(final AbstractLink link) {
        final GedWriterLine line = new GedWriterLine(level, link,
                level + " " + mapTag(link.getString()) + " @" + link.getToString() + "@");
        lines.add(line);
        handleChildren(link);
    }

    /**
     * Sometimes a tag is used that isn't in the list.
     *
     * @param tag
     * @return
     */
    private String mapTag(final String tag) {
        final String mappedTag = map.get(tag);
        if (mappedTag != null) {
            return mappedTag;
        }
        return tag;
    }

    /**
     * Avoid trailing space.
     *
     * @param tail the object that has a tail of
     * @return the tail string, prepended with space if not empty
     */
    private String tail(final Tail tail) {
        if (tail.getTail().isEmpty()) {
            return "";
        }
        return " " + tail.getTail();
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
