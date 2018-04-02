package org.schoellerfamily.gedobject.datamodel.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Build the token table. Broken up into a bunch of steps to reduce complexity
 * scores.
 *
 * @author Dick Schoeller
 */
public final class TokenTableInitializer {
    /**
     * The token table.
     */
    private final Map<String, GedToken> tokens = new HashMap<>();

    /**
     * The list of short and long strings for standard attributes.
     */
    private static final String[][] ATTRIBUTE_PAIRS = {
            {"ATTRIBUTE", "Attribute"},
            {"ABBR", "Abbreviation"},
            {"ABSTRACTOR", "Abstractor"},
            {"ABT", "About"},
            {"ACTIVE", "Active"},
            {"ADDR", "Address"},
            {"ADOP", "Adopted"},
            {"ADBAP", "Adult Baptism"},
            {"AFN", "Ancestral File Number"},
            {"AFT", "After"},
            {"AGE", "Age"},
            {"ALIA", "Alias"},
            {"ANCE", "Generations of ancestors"},
            {"ANUL", "Anullment"},
            {"AUDIO", "Audio"},
            {"AUTH", "Author"},
            {"AUTHOR", "Author"},
            {"BAPM", "Baptism"},
            {"BARM", "Bar Mitzvah"},
            {"BASM", "Bat Mitzvah"},
            {"BEF", "Before"},
            {"BEGIN", "Begin"},
            {"BET", "Between"},
            {"BIRT", "Birth"},
            {"BOOK", "Book"},
            {"BRIS", "Bris Milah"},
            {"BURI", "Burial"},
            {"BUSINESS", "Business"},
            {"CANCELED", "Canceled"},
            {"CARD", "Card"},
            {"CAST", "Caste"},
            {"CAUS", "Cause"},
            {"CEME", "Cemetery"},
            {"CENS", "Census"},
            {"CENSUS", "Census"},
            {"CHAN", "Changed"},
            {"CHAR", "Character Set"},
            {"CHR", "Christening"},
            {"CHURCH", "Church"},
            {"COMPILER", "Compiler"},
            {"COMPLETED", "Completed"},
            {"CONF", "Confirmation"},
            {"COPY", "Copy"},
            {"COURT", "Court"},
            {"DEAT", "Death"},
            {"DESC", "Generations of descendants"},
            {"DEST", "Destination"},
            {"DIV", "Divorce"},
            {"DIVF", "Divorce Final"},
            {"DIVORCED", "Divorced"},
            {"DONE", "Done"},
            {"EDITOR", "Editor"},
            {"EDUC", "Education"},
            {"ELECTRONIC", "Electronic"},
            {"EMIG", "Emigration"},
            {"ENGA", "Engaged"},
            {"EVEN", "Event"},
            {"EXTRACT", "Extract"},
            {"FAMF", "Family file"},
            {"FATH", "Father"},
            {"FEMALE", "Female"},
            {"FICHE", "Fiche"},
            {"FILE", "File"},
            {"FILM", "Film"},
            {"FORM", "Format"},
            {"FOUND", "Found"},
            {"FROM", "From"},
            {"GEDC", "GEDCOM"},
            {"GODP", "Godparent"},
            {"GOVERNMENT", "Government"},
            {"GRAD", "Graduation"},
            {"HEIR", "Heir"},
            {"HISTORY", "History"},
            {"IMMI", "Immigration"},
            {"INFANT", "Infant"},
            {"INFORMANT", "Informant"},
            {"INTERVIEW", "Interview"},
            {"INTERVIEWER", "Interviewer"},
            {"ISSUE", "Issue"},
            {"ITEM", "Item"},
            {"JOURNAL", "Journal"},
            {"LANG", "Language"},
            {"LETTER", "Letter"},
            {"LINE", "Line"},
            {"LINEAGE", "Lineage"},
            {"MAGAZINE", "Magazine"},
            {"MALE", "Male"},
            {"MANUSCRIPT", "Manuscript"},
            {"MAP", "Map"},
            {"MARB", "Marriage Bans"},
            {"MARL", "Marriage License"},
            {"MARR", "Marriage"},
            {"MARRIED", "Married"},
            {"MEDI", "Media"},
            {"MEMBER", "Member"},
            {"MILITARY", "Military"},
            {"MOTH", "Mother"},
            {"NAMR", "Name (religious)"},
            {"NAMING", "Naming"},
            {"NAMS", "NAMS"},
            {"NATU", "Naturalized"},
            {"NCHI", "Number of Children"},
            {"NEWLINE", "Newline"},
            {"NEWSPAPER", "Newspaper"},
            {"NMR", "Not Married"},
            {"NUMBER", "Number"},
            {"OCCU", "Occupation"},
            {"ORDI", "Ordinance process flag"},
            {"ORDERED", "Ordered"},
            {"ORGANIZATION", "Organization"},
            {"ORIGINAL", "Original"},
            {"OTHER", "Other"},
            {"PAGE", "Page"},
            {"PERIODICAL", "Periodical"},
            {"PERSONAL", "Personal"},
            {"PHON", "Phone Number"},
            {"PHOTO", "Photograph"},
            {"PHOTOCOPY", "Photocopy"},
            {"PLANNED", "Planned"},
            {"PLOT", "Plot"},
            {"PROB", "Probate"},
            {"PROVED", "Proved"},
            {"PUBL", "Published"},
            {"QUAY", "Surety"},
            {"RECITED", "Recited"},
            {"REFN", "Reference Number"},
            {"RELI", "Religion"},
            {"REPO", "Repository"},
            {"RESI", "Residence"},
            {"RESN", "Restriction"},
            {"RETI", "Retired"},
            {"ROLE", "Role"},
            {"SEX", "Sex"},
            {"SINGLE", "Single"},
            {"SITE", "Site"},
            {"SOUND", "Sound"},
            {"SPOU", "Spouse"},
            {"SSN", "Social Security Number"},
            {"STILLBORN", "Stillborn"},
            {"SUBMITTED", "Submitted"},
            {"TEMP", "Temple code"},
            {"TEXT", "Text"},
            {"TIME", "Time"},
            {"TITL", "Title"},
            {"TO", "To"},
            {"TOKEN", "Token"},
            {"TOMBSTONE", "Tombstone"},
            {"TRADITION", "Tradition"},
            {"TRANSCRIBER", "Transcriber"},
            {"TRANSCRIPT", "Transcript"},
            {"TYPE", "Type"},
            {"UNDERSCORE", "Underscore"},
            {"UNICODE", "Unicode"},
            {"UNPUBLISHED", "Unpublished"},
            {"UNVEIL", "Headstone unveiled"},
            {"VERS", "Version"},
            {"VIDEO", "Video"},
            {"VITAL", "Vital"},
            {"WIDOWED", "Widowed"},
            {"WILL", "Will"},
            {"WITN", "Witness"},
            {"XREF", "Cross Reference"},
    };

    /**
     * Constructor.
     *
     * Populates the map of tokens.
     */
    public TokenTableInitializer() {
        initAttributeTokens();
        initLevel0FactoryTokens();
        initSpecialFactoryTokens();
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokens() {
        for (final String[] pair : ATTRIBUTE_PAIRS) {
            put(pair[0], pair[1], AbstractGedObjectFactory.ATTR_FACTORY);
        }
    }

    /**
     * Method to initialize the tokens for top level items
     */
    private void initLevel0FactoryTokens() {
        put("ROOT", "Root", AbstractGedObjectFactory.ROOT_FACTORY);
        put("HEAD", "Header", AbstractGedObjectFactory.HEAD_FACTORY);
        put("FAM", "Family", AbstractGedObjectFactory.FAMILY_FACTORY);
        put("INDI", "Person", AbstractGedObjectFactory.PERSON_FACTORY);
        put("NOTE", "Note", AbstractGedObjectFactory.NOTE_FACTORY);
        put("OBJE", "Multimedia", AbstractGedObjectFactory.MULTIMEDIA_FACTORY);
        put("SOUR", "Source", AbstractGedObjectFactory.SOURCE_FACTORY);
        put("SUBM", "Submitter", AbstractGedObjectFactory.SUBMITTER_FACTORY);
        put("SUBN", "Submission", AbstractGedObjectFactory.SUBMISSION_FACTORY);
        put("TRLR", "Trailer", AbstractGedObjectFactory.TRAILER_FACTORY);
    }

    /**
     * Method to initialize the tokens that use something other than the
     * attribute factory.
     */
    private void initSpecialFactoryTokens() {
        put("CHIL", "Child", AbstractGedObjectFactory.CHILD_FACTORY);
        put("CONC", "Concatenate", AbstractGedObjectFactory.CONCAT_FACTORY);
        put("CONT", "Continuation", AbstractGedObjectFactory.CONTIN_FACTORY);
        put("DATE", "Date", AbstractGedObjectFactory.DATE_FACTORY);
        put("FAMC", "Child of Family", AbstractGedObjectFactory.FAMC_FACTORY);
        put("FAMS", "Spouse of Family", AbstractGedObjectFactory.FAMS_FACTORY);
        put("HUSB", "Husband", AbstractGedObjectFactory.HUSBAND_FACTORY);
        put("LINK", "Link", AbstractGedObjectFactory.LINK_FACTORY);
        put("NAME", "Name", AbstractGedObjectFactory.NAME_FACTORY);
        put("PLAC", "Place", AbstractGedObjectFactory.PLACE_FACTORY);
        put("PLACE", "Place", AbstractGedObjectFactory.PLACE_FACTORY);
        put("WIFE", "Wife", AbstractGedObjectFactory.WIFE_FACTORY);
    }

    /**
     * @param shortstring the short string in gedcom
     * @param fullstring the long string in the db
     * @param factory the associated concrete factory
     */
    private void put(final String shortstring, final String fullstring,
            final AbstractGedObjectFactory factory) {
        tokens.put(shortstring, new GedToken(fullstring, factory));
    }

    /**
     * Return the token table.
     *
     * @return the token table.
     */
    /* default */ Map<String, GedToken> getTokens() {
        return tokens;
    }
}
