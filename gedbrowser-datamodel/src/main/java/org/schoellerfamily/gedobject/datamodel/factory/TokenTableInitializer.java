package org.schoellerfamily.gedobject.datamodel.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Build the token table. Broken up into a bunch of steps to reduce complexity
 * scores.
 *
 * @author Dick Schoeller
 */
public class TokenTableInitializer {
    /**
     * The token table.
     */
    private final Map<String, GedToken> tokens = new HashMap<>();

    /**
     * Constructor.
     *
     * Populates the map of tokens.
     */
    public TokenTableInitializer() {
        initAttributeTokensA();
        initAttributeTokensB();
        initAttributeTokensCD();
        initAttributeTokensEFGH();
        initAttributeTokensIJKL();
        initAttributeTokensMN();
        initAttributeTokensOQ();
        initAttributeTokensRS();
        initAttributeTokensTZ();
        initSpecialFactoryTokens();
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokensA() {
        put("ATTRIBUTE", "Attribute");
        put("ABBR", "Abbreviation");
        put("ABSTRACTOR", "Abstractor");
        put("ABT", "About");
        put("ACTIVE", "Active");
        put("ADDR", "Address");
        put("ADOP", "Adopted");
        put("ADBAP", "Adult Baptism");
        put("AFN", "Ancestral File Number");
        put("AFT", "After");
        put("AGE", "Age");
        put("ALIA", "Alias");
        put("ANCE", "Generations of ancestors");
        put("ANUL", "Anullment");
        put("AUDIO", "Audio");
        put("AUTH", "Author");
        put("AUTHOR", "Author");
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokensB() {
        put("BAPM", "Baptism");
        put("BARM", "Bar Mitzvah");
        put("BASM", "Bat Mitzvah");
        put("BEF", "Before");
        put("BEGIN", "Begin");
        put("BET", "Between");
        put("BIRT", "Birth");
        put("BOOK", "Book");
        put("BRIS", "Bris Milah");
        put("BURI", "Burial");
        put("BUSINESS", "Business");
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokensCD() {
        put("CANCELED", "Canceled");
        put("CARD", "Card");
        put("CAST", "Caste");
        put("CAUS", "Cause");
        put("CEME", "Cemetery");
        put("CENS", "Census");
        put("CENSUS", "Census");
        put("CHAN", "Changed");
        put("CHAR", "Character Set");
        put("CHR", "Christening");
        put("CHURCH", "Church");
        put("COMPILER", "Compiler");
        put("COMPLETED", "Completed");
        put("CONF", "Confirmation");
        put("COPY", "Copy");
        put("COURT", "Court");
        put("DEAT", "Death");
        put("DESC", "Generations of descendants");
        put("DEST", "Destination");
        put("DIV", "Divorce");
        put("DIVF", "Divorce Final");
        put("DIVORCED", "Divorced");
        put("DONE", "Done");
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokensEFGH() {
        put("EDITOR", "Editor");
        put("EDUC", "Education");
        put("ELECTRONIC", "Electronic");
        put("EMIG", "Emigration");
        put("ENGA", "Engaged");
        put("EVEN", "Event");
        put("EXTRACT", "Extract");
        put("FAMF", "Family file");
        put("FATH", "Father");
        put("FEMALE", "Female");
        put("FICHE", "Fiche");
        put("FILE", "File");
        put("FILM", "Film");
        put("FORM", "Format");
        put("FOUND", "Found");
        put("FROM", "From");
        put("GEDC", "GEDCOM");
        put("GODP", "Godparent");
        put("GOVERNMENT", "Government");
        put("GRAD", "Graduation");
        put("HEIR", "Heir");
        put("HISTORY", "History");
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokensIJKL() {
        put("IMMI", "Immigration");
        put("INFANT", "Infant");
        put("INFORMANT", "Informant");
        put("INTERVIEW", "Interview");
        put("INTERVIEWER", "Interviewer");
        put("ISSUE", "Issue");
        put("ITEM", "Item");
        put("JOURNAL", "Journal");
        put("LANG", "Language");
        put("LETTER", "Letter");
        put("LINE", "Line");
        put("LINEAGE", "Lineage");
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokensMN() {
        put("MAGAZINE", "Magazine");
        put("MALE", "Male");
        put("MANUSCRIPT", "Manuscript");
        put("MAP", "Map");
        put("MARB", "Marriage Bans");
        put("MARL", "Marriage License");
        put("MARR", "Marriage");
        put("MARRIED", "Married");
        put("MEDI", "Media");
        put("MEMBER", "Member");
        put("MILITARY", "Military");
        put("MOTH", "Mother");
        put("NAMR", "Name (religious)");
        put("NAMING", "Naming");
        put("NAMS", "NAMS");
        put("NATU", "Naturalized");
        put("NCHI", "Number of Children");
        put("NEWLINE", "Newline");
        put("NEWSPAPER", "Newspaper");
        put("NMR", "Not Married");
        put("NUMBER", "Number");
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokensOQ() {
        put("OCCU", "Occupation");
        put("ORDI", "Ordinance process flag");
        put("ORDERED", "Ordered");
        put("ORGANIZATION", "Organization");
        put("ORIGINAL", "Original");
        put("OTHER", "Other");
        put("PAGE", "Page");
        put("PERIODICAL", "Periodical");
        put("PERSONAL", "Personal");
        put("PHON", "Phone Number");
        put("PHOTO", "Photograph");
        put("PHOTOCOPY", "Photocopy");
        put("PLANNED", "Planned");
        put("PLOT", "Plot");
        put("PROB", "Probate");
        put("PROVED", "Proved");
        put("PUBL", "Published");
        put("QUAY", "Surety");
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokensRS() {
        put("RECITED", "Recited");
        put("REFN", "Reference Number");
        put("RELI", "Religion");
        put("REPO", "Repository");
        put("RESI", "Residence");
        put("RESN", "Restriction");
        put("RETI", "Retired");
        put("ROLE", "Role");
        put("SEX", "Sex");
        put("SINGLE", "Single");
        put("SITE", "Site");
        put("SOUND", "Sound");
        put("SPOU", "Spouse");
        put("SSN", "Social Security Number");
        put("STILLBORN", "Stillborn");
        put("SUBMITTED", "Submitted");
    }

    /**
     * Initialize a chunk of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokensTZ() {
        put("TEMP", "Temple code");
        put("TEXT", "Text");
        put("TIME", "Time");
        put("TITL", "Title");
        put("TO", "To");
        put("TOKEN", "Token");
        put("TOMBSTONE", "Tombstone");
        put("TRADITION", "Tradition");
        put("TRANSCRIBER", "Transcriber");
        put("TRANSCRIPT", "Transcript");
        put("TYPE", "Type");
        put("UNDERSCORE", "Underscore");
        put("UNICODE", "Unicode");
        put("UNPUBLISHED", "Unpublished");
        put("UNVEIL", "Headstone unveiled");
        put("VERS", "Version");
        put("VIDEO", "Video");
        put("VITAL", "Vital");
        put("WIDOWED", "Widowed");
        put("WILL", "Will");
        put("WITN", "Witness");
        put("XREF", "Cross Reference");
    }

    /**
     * @param shortstring the short string in gedcom
     * @param fullstring the long string in the db
     */
    private void put(final String shortstring, final String fullstring) {
        tokens.put(shortstring, createAttrToken(fullstring));
    }

    /**
     * @param fullstring the token string
     * @return the token object
     */
    private GedToken createAttrToken(final String fullstring) {
        return new GedToken(fullstring, AbstractGedObjectFactory.ATTR_FACTORY);
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
        put("FAM", "Family", AbstractGedObjectFactory.FAMILY_FACTORY);
        put("FAMC", "Child of Family", AbstractGedObjectFactory.FAMC_FACTORY);
        put("FAMS", "Spouse of Family", AbstractGedObjectFactory.FAMS_FACTORY);
        put("HEAD", "Header", AbstractGedObjectFactory.HEAD_FACTORY);
        put("HUSB", "Husband", AbstractGedObjectFactory.HUSBAND_FACTORY);
        put("INDI", "Person", AbstractGedObjectFactory.PERSON_FACTORY);
        put("LINK", "Link", AbstractGedObjectFactory.LINK_FACTORY);
        put("NAME", "Name", AbstractGedObjectFactory.NAME_FACTORY);
        put("NOTE", "Note", AbstractGedObjectFactory.NOTE_FACTORY);
        put("OBJE", "Multimedia", AbstractGedObjectFactory.MULTIMEDIA_FACTORY);
        put("PLAC", "Place", AbstractGedObjectFactory.PLACE_FACTORY);
        put("PLACE", "Place", AbstractGedObjectFactory.PLACE_FACTORY);
        put("ROOT", "Root", AbstractGedObjectFactory.ROOT_FACTORY);
        put("SOUR", "Source", AbstractGedObjectFactory.SOURCE_FACTORY);
        put("SUBM", "Submitter", AbstractGedObjectFactory.SUBMITTER_FACTORY);
        put("SUBN", "Submission", AbstractGedObjectFactory.SUBMISSION_FACTORY);
        put("TRLR", "Trailer", AbstractGedObjectFactory.TRAILER_FACTORY);
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
