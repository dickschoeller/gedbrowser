package org.schoellerfamily.gedbrowser.writer.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;

/**
 * @author Dick Schoeller
 */
public class GedWriterLineCreator implements AttributeLineVisitor,
        RootLineVisitor, LinkLineVisitor, DateLineVisitor,
        IdentifiedRecordLineVisitor {

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
    public int initLevel() {
        level = 0;
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int incrementLevel() {
        level++;
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int decrementLevel() {
        level--;
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GedWriterLine> getLines() {
        return lines;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String mapTag(final String longString) {
        final String mappedTag = MAP.get(longString);
        if (mappedTag != null) {
            return mappedTag;
        }
        return longString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Head head) {
        final GedWriterLine line = new GedWriterLine(getLevel(), head,
                getLevel() + " HEAD");
        getLines().add(line);
        handleChildren(head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Multimedia multimedia) {
        final GedWriterLine line = new GedWriterLine(getLevel(), multimedia,
                getLevel() + " OBJE");
        getLines().add(line);
        handleChildren(multimedia);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Note note) {
        if (level == 0) {
            final GedWriterLine line = new GedWriterLine(getLevel(), note,
                    getLevel() + " @" + note.getString() + "@ NOTE" + tail(note));
            getLines().add(line);
        } else {
            final GedWriterLine line = new GedWriterLine(getLevel(), note,
                    getLevel() + " NOTE" + tail(note));
            getLines().add(line);
        }
        contAndConc(note);
        handleChildren(note);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Trailer trailer) {
        final GedWriterLine line = new GedWriterLine(getLevel(), trailer,
                getLevel() + " TRLR");
        getLines().add(line);
        handleChildren(trailer);
    }
}
