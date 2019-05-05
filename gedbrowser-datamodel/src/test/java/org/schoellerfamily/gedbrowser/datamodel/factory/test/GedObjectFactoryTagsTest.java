package org.schoellerfamily.gedbrowser.datamodel.factory.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedobject.datamodel.factory.AbstractGedObjectFactory.GedObjectFactory;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class GedObjectFactoryTagsTest {
    /** */
    private final GedObjectFactory factory;
    /** */
    private final GedObject parent;
    /** */
    private final String xref;
    /** */
    private final String tag;
    /** */
    private final String tail;
    /** */
    private final String expected;
    /** */
    private static final Root ROOT = new GedObjectBuilder().getRoot();
    /** */
    private static final Object[][] PARAMETERS = new Object[][] {
        {ROOT, "ATTRIBUTE", null, "Attribute", null},
        {ROOT, "ABBR", null, "Abbreviation", null},
        {ROOT, "ABSTRACTOR", null, "Abstractor", null},
        {ROOT, "ABT", null, "About", null},
        {ROOT, "ACTIVE", null, "Active", null},
        {ROOT, "ADDR", null, "Address", null},
        {ROOT, "ADOP", null, "Adopted", null},
        {ROOT, "ADBAP", null, "Adult Baptism", null},
        {ROOT, "AFN", null, "Ancestral File Number", null},
        {ROOT, "AFT", null, "After", null},
        {ROOT, "AGE", null, "Age", null},
        {ROOT, "ALIA", null, "Alias", null},
        {ROOT, "ANCE", null, "Generations of ancestors", null},
        {ROOT, "ANUL", null, "Anullment", null},
        {ROOT, "AUDIO", null, "Audio", null},
        {ROOT, "AUTH", null, "Author", null},
        {ROOT, "AUTHOR", null, "Author", null},
        {ROOT, "BAPM", null, "Baptism", null},
        {ROOT, "BARM", null, "Bar Mitzvah", null},
        {ROOT, "BASM", null, "Bat Mitzvah", null},
        {ROOT, "BEF", null, "Before", null},
        {ROOT, "BEGIN", null, "Begin", null},
        {ROOT, "BET", null, "Between", null},
        {ROOT, "BIRT", null, "Birth", null},
        {ROOT, "BOOK", null, "Book", null},
        {ROOT, "BRIS", null, "Bris Milah", null},
        {ROOT, "BURI", null, "Burial", null},
        {ROOT, "BUSINESS", null, "Business", null},
        {ROOT, "CANCELED", null, "Canceled", null},
        {ROOT, "CARD", null, "Card", null},
        {ROOT, "CAST", null, "Caste", null},
        {ROOT, "CAUS", null, "Cause", null},
        {ROOT, "CEME", null, "Cemetery", null},
        {ROOT, "CENS", null, "Census", null},
        {ROOT, "CENSUS", null, "Census", null},
        {ROOT, "CHAN", null, "Changed", null},
        {ROOT, "CHAR", null, "Character Set", null},
        {ROOT, "CHR", null, "Christening", null},
        {ROOT, "CHURCH", null, "Church", null},
        {ROOT, "COMPILER", null, "Compiler", null},
        {ROOT, "COMPLETED", null, "Completed", null},
        {ROOT, "CONF", null, "Confirmation", null},
        {ROOT, "COPY", null, "Copy", null},
        {ROOT, "COURT", null, "Court", null},
        {ROOT, "DEAT", null, "Death", null},
        {ROOT, "DESC", null, "Generations of descendants", null},
        {ROOT, "DEST", null, "Destination", null},
        {ROOT, "DIV", null, "Divorce", null},
        {ROOT, "DIVF", null, "Divorce Final", null},
        {ROOT, "DIVORCED", null, "Divorced", null},
        {ROOT, "DONE", null, "Done", null},
        {ROOT, "EDITOR", null, "Editor", null},
        {ROOT, "EDUC", null, "Education", null},
        {ROOT, "ELECTRONIC", null, "Electronic", null},
        {ROOT, "EMIG", null, "Emigration", null},
        {ROOT, "ENGA", null, "Engaged", null},
        {ROOT, "EVEN", null, "Event", null},
        {ROOT, "EXTRACT", null, "Extract", null},
        {ROOT, "FAMF", null, "Family file", null},
        {ROOT, "FATH", null, "Father", null},
        {ROOT, "FEMALE", null, "Female", null},
        {ROOT, "FICHE", null, "Fiche", null},
        {ROOT, "FILE", null, "File", null},
        {ROOT, "FILM", null, "Film", null},
        {ROOT, "FORM", null, "Format", null},
        {ROOT, "FOUND", null, "Found", null},
        {ROOT, "FROM", null, "From", null},
        {ROOT, "GEDC", null, "GEDCOM", null},
        {ROOT, "GODP", null, "Godparent", null},
        {ROOT, "GOVERNMENT", null, "Government", null},
        {ROOT, "GRAD", null, "Graduation", null},
        {ROOT, "HEIR", null, "Heir", null},
        {ROOT, "HISTORY", null, "History", null},
        {ROOT, "IMMI", null, "Immigration", null},
        {ROOT, "INFANT", null, "Infant", null},
        {ROOT, "INFORMANT", null, "Informant", null},
        {ROOT, "INTERVIEW", null, "Interview", null},
        {ROOT, "INTERVIEWER", null, "Interviewer", null},
        {ROOT, "ISSUE", null, "Issue", null},
        {ROOT, "ITEM", null, "Item", null},
        {ROOT, "JOURNAL", null, "Journal", null},
        {ROOT, "LANG", null, "Language", null},
        {ROOT, "LETTER", null, "Letter", null},
        {ROOT, "LINE", null, "Line", null},
        {ROOT, "LINEAGE", null, "Lineage", null},
        {ROOT, "MAGAZINE", null, "Magazine", null},
        {ROOT, "MALE", null, "Male", null},
        {ROOT, "MANUSCRIPT", null, "Manuscript", null},
        {ROOT, "MAP", null, "Map", null},
        {ROOT, "MARB", null, "Marriage Bans", null},
        {ROOT, "MARL", null, "Marriage License", null},
        {ROOT, "MARR", null, "Marriage", null},
        {ROOT, "MARRIED", null, "Married", null},
        {ROOT, "MEDI", null, "Media", null},
        {ROOT, "MEMBER", null, "Member", null},
        {ROOT, "MILITARY", null, "Military", null},
        {ROOT, "MOTH", null, "Mother", null},
        {ROOT, "NAMR", null, "Name (religious)", null},
        {ROOT, "NAMING", null, "Naming", null},
        {ROOT, "NAMS", null, "NAMS", null},
        {ROOT, "NATU", null, "Naturalized", null},
        {ROOT, "NCHI", null, "Number of Children", null},
        {ROOT, "NEWLINE", null, "Newline", null},
        {ROOT, "NEWSPAPER", null, "Newspaper", null},
        {ROOT, "NMR", null, "Not Married", null},
        {ROOT, "NUMBER", null, "Number", null},
        {ROOT, "OCCU", null, "Occupation", null},
        {ROOT, "ORDI", null, "Ordinance process flag", null},
        {ROOT, "ORDERED", null, "Ordered", null},
        {ROOT, "ORGANIZATION", null, "Organization", null},
        {ROOT, "ORIGINAL", null, "Original", null},
        {ROOT, "OTHER", null, "Other", null},
        {ROOT, "PAGE", null, "Page", null},
        {ROOT, "PERIODICAL", null, "Periodical", null},
        {ROOT, "PERSONAL", null, "Personal", null},
        {ROOT, "PHON", null, "Phone Number", null},
        {ROOT, "PHOTO", null, "Photograph", null},
        {ROOT, "PHOTOCOPY", null, "Photocopy", null},
        {ROOT, "PLANNED", null, "Planned", null},
        {ROOT, "PLOT", null, "Plot", null},
        {ROOT, "PROB", null, "Probate", null},
        {ROOT, "PROVED", null, "Proved", null},
        {ROOT, "PUBL", null, "Published", null},
        {ROOT, "QUAY", null, "Surety", null},
        {ROOT, "RECITED", null, "Recited", null},
        {ROOT, "REFN", null, "Reference Number", null},
        {ROOT, "RELI", null, "Religion", null},
        {ROOT, "REPO", null, "Repository", null},
        {ROOT, "RESI", null, "Residence", null},
        {ROOT, "RESN", null, "Restriction", null},
        {ROOT, "RETI", null, "Retired", null},
        {ROOT, "ROLE", null, "Role", null},
        {ROOT, "SEX", null, "Sex", null},
        {ROOT, "SINGLE", null, "Single", null},
        {ROOT, "SITE", null, "Site", null},
        {ROOT, "SOUND", null, "Sound", null},
        {ROOT, "SPOU", null, "Spouse", null},
        {ROOT, "SSN", null, "Social Security Number", null},
        {ROOT, "STILLBORN", null, "Stillborn", null},
        {ROOT, "SUBMITTED", null, "Submitted", null},
        {ROOT, "TEMP", null, "Temple code", null},
        {ROOT, "TEXT", null, "Text", null},
        {ROOT, "TIME", null, "Time", null},
        {ROOT, "TITL", null, "Title", null},
        {ROOT, "TO", null, "To", null},
        {ROOT, "TOKEN", null, "Token", null},
        {ROOT, "TOMBSTONE", null, "Tombstone", null},
        {ROOT, "TRADITION", null, "Tradition", null},
        {ROOT, "TRANSCRIBER", null, "Transcriber", null},
        {ROOT, "TRANSCRIPT", null, "Transcript", null},
        {ROOT, "TYPE", null, "Type", null},
        {ROOT, "UNDERSCORE", null, "Underscore", null},
        {ROOT, "UNICODE", null, "Unicode", null},
        {ROOT, "UNPUBLISHED", null, "Unpublished", null},
        {ROOT, "UNVEIL", null, "Headstone unveiled", null},
        {ROOT, "VERS", null, "Version", null},
        {ROOT, "VIDEO", null, "Video", null},
        {ROOT, "VITAL", null, "Vital", null},
        {ROOT, "WIDOWED", null, "Widowed", null},
        {ROOT, "WILL", null, "Will", null},
        {ROOT, "WITN", null, "Witness", null},
        {ROOT, "XREF", null, "Cross Reference", null},
        {ROOT, "FOOBAR", null, "FOOBAR", null},
        {ROOT, null, null, "", null},
    };

    /**
     * @param parent the parent of the object being created
     * @param expected the expected built tag
     * @param xref a cross reference string (can be null or empty)
     * @param tag a tag (if null or empty defaults to ATTRIBUTE)
     * @param tail additional text from the line (can be null or empty)
     */
    public GedObjectFactoryTagsTest(final GedObject parent,
            final String tag,
            final String xref, final String expected, final String tail) {
        this.factory = new GedObjectFactory();
        this.expected = expected;
        this.parent = parent;
        this.xref = xref;
        this.tag = tag;
        this.tail = tail;
    }

    /**
     * @return the collection of test parameters
     */
    @Parameters
    public static Object[][] params() {
        return PARAMETERS;
    }

    /** */
    @Test
    public void testFactoryResultTag() {
        final GedObject gob = factory.create(parent, xref, tag, tail);
        assertEquals("Did not get the expected tag", expected, gob.getString());
    }

}
