package org.schoellerfamily.gedobject.datamodel.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dick Schoeller
 */
public class TokenTableInitializer {
    /**
     * The token table.
     */
    private final Map<String, GedToken> tokens =
            new HashMap<String, GedToken>();

    /**
     * Constructor.
     *
     * Populates the map of tokens.
     */
    public TokenTableInitializer() {
        initAttributeTokens0();
        initAttributeTokens1();
        initSpecialFactoryTokens();
    }

    /**
     * Method to initialize the tokens that use something other than the
     * attribute factory.
     */
    private void initSpecialFactoryTokens() {
        tokens.put("CHIL",
                new GedToken("Child", AbstractGedObjectFactory.CHILD_FACTORY));
        tokens.put("CONC", new GedToken("Concatenate",
                AbstractGedObjectFactory.CONCAT_FACTORY));
        tokens.put("CONT", new GedToken("Continuation",
                AbstractGedObjectFactory.CONTIN_FACTORY));
        tokens.put("DATE",
                new GedToken("Date", AbstractGedObjectFactory.DATE_FACTORY));
        tokens.put("FAM", new GedToken("Family",
                AbstractGedObjectFactory.FAMILY_FACTORY));
        tokens.put("FAMC", new GedToken("Child of Family",
                AbstractGedObjectFactory.FAMC_FACTORY));
        tokens.put("FAMS", new GedToken("Spouse of Family",
                AbstractGedObjectFactory.FAMS_FACTORY));
        tokens.put("HEAD",
                new GedToken("Header", AbstractGedObjectFactory.HEAD_FACTORY));
        tokens.put("HUSB", new GedToken("Husband",
                AbstractGedObjectFactory.HUSBAND_FACTORY));
        tokens.put("INDI", new GedToken("Person",
                AbstractGedObjectFactory.PERSON_FACTORY));
        tokens.put("LINK",
                new GedToken("Link", AbstractGedObjectFactory.LINK_FACTORY));
        tokens.put("NAME",
                new GedToken("Name", AbstractGedObjectFactory.NAME_FACTORY));
        tokens.put("NOTE",
                new GedToken("Note", AbstractGedObjectFactory.NOTE_FACTORY));
        tokens.put("OBJE", new GedToken("Multimedia",
                AbstractGedObjectFactory.MULTIMEDIA_FACTORY));
        tokens.put("PLAC",
                new GedToken("Place", AbstractGedObjectFactory.PLACE_FACTORY));
        tokens.put("PLACE",
                new GedToken("Place", AbstractGedObjectFactory.PLACE_FACTORY));
        tokens.put("ROOT",
                new GedToken("Root", AbstractGedObjectFactory.ROOT_FACTORY));
        tokens.put("SOUR", new GedToken("Source",
                AbstractGedObjectFactory.SOURCE_FACTORY));
        tokens.put("SUBM", new GedToken("Submitter",
                AbstractGedObjectFactory.SUBMITTER_FACTORY));
        tokens.put("SUBN", new GedToken("Submission",
                AbstractGedObjectFactory.SUBMISSION_FACTORY));
        tokens.put("TRLR", new GedToken("Trailer",
                AbstractGedObjectFactory.TRAILER_FACTORY));
        tokens.put("WIFE",
                new GedToken("Wife", AbstractGedObjectFactory.WIFE_FACTORY));
    }

    /**
     * Initialize the 1st half of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokens0() {
        tokens.put("ATTRIBUTE", createAttrToken("Attribute"));
        tokens.put("ABBR", createAttrToken("Abbreviation"));
        tokens.put("ABSTRACTOR", createAttrToken("Abstractor"));
        tokens.put("ABT", createAttrToken("About"));
        tokens.put("ACTIVE", createAttrToken("Active"));
        tokens.put("ADDR", createAttrToken("Address"));
        tokens.put("ADOP", createAttrToken("Adopted"));
        tokens.put("ADBAP", createAttrToken("Adult Baptism"));
        tokens.put("AFN", createAttrToken("Ancestral File Number"));
        tokens.put("AFT", createAttrToken("After"));
        tokens.put("AGE", createAttrToken("Age"));
        tokens.put("ALIA", createAttrToken("Alias"));
        tokens.put("ANCE", createAttrToken("Generations of ancestors"));
        tokens.put("ANUL", createAttrToken("Anullment"));
        tokens.put("AUDIO", createAttrToken("Audio"));
        tokens.put("AUTH", createAttrToken("Author"));
        tokens.put("AUTHOR", createAttrToken("Author"));
        tokens.put("BAPM", createAttrToken("Baptism"));
        tokens.put("BARM", createAttrToken("Bar Mitzvah"));
        tokens.put("BASM", createAttrToken("Bat Mitzvah"));
        tokens.put("BEF", createAttrToken("Before"));
        tokens.put("BEGIN", createAttrToken("Begin"));
        tokens.put("BET", createAttrToken("Between"));
        tokens.put("BIRT", createAttrToken("Birth"));
        tokens.put("BOOK", createAttrToken("Book"));
        tokens.put("BRIS", createAttrToken("Bris Milah"));
        tokens.put("BURI", createAttrToken("Burial"));
        tokens.put("BUSINESS", createAttrToken("Business"));
        tokens.put("CANCELED", createAttrToken("Canceled"));
        tokens.put("CARD", createAttrToken("Card"));
        tokens.put("CAST", createAttrToken("Caste"));
        tokens.put("CAUS", createAttrToken("Cause"));
        tokens.put("CEME", createAttrToken("Cemetery"));
        tokens.put("CENS", createAttrToken("Census"));
        tokens.put("CENSUS", createAttrToken("Census"));
        tokens.put("CHAN", createAttrToken("Changed"));
        tokens.put("CHAR", createAttrToken("Character Set"));
        tokens.put("CHR", createAttrToken("Christening"));
        tokens.put("CHURCH", createAttrToken("Church"));
        tokens.put("COMPILER", createAttrToken("Compiler"));
        tokens.put("COMPLETED", createAttrToken("Completed"));
        tokens.put("CONF", createAttrToken("Confirmation"));
        tokens.put("COPY", createAttrToken("Copy"));
        tokens.put("COURT", createAttrToken("Court"));
        tokens.put("DEAT", createAttrToken("Death"));
        tokens.put("DESC", createAttrToken("Generations of descendants"));
        tokens.put("DEST", createAttrToken("Destination"));
        tokens.put("DIV", createAttrToken("Divorce"));
        tokens.put("DIVF", createAttrToken("Divorce Final"));
        tokens.put("DIVORCED", createAttrToken("Divorced"));
        tokens.put("DONE", createAttrToken("Done"));
        tokens.put("EDITOR", createAttrToken("Editor"));
        tokens.put("EDUC", createAttrToken("Education"));
        tokens.put("ELECTRONIC", createAttrToken("Electronic"));
        tokens.put("EMIG", createAttrToken("Emigration"));
        tokens.put("ENGA", createAttrToken("Engaged"));
        tokens.put("EVEN", createAttrToken("Event"));
        tokens.put("EXTRACT", createAttrToken("Extract"));
        tokens.put("FAMF", createAttrToken("Family file"));
        tokens.put("FATH", createAttrToken("Father"));
        tokens.put("FEMALE", createAttrToken("Female"));
        tokens.put("FICHE", createAttrToken("Fiche"));
        tokens.put("FILE", createAttrToken("File"));
        tokens.put("FILM", createAttrToken("Film"));
        tokens.put("FORM", createAttrToken("Format"));
        tokens.put("FOUND", createAttrToken("Found"));
        tokens.put("FROM", createAttrToken("From"));
        tokens.put("GEDC", createAttrToken("GEDCOM"));
        tokens.put("GODP", createAttrToken("Godparent"));
        tokens.put("GOVERNMENT", createAttrToken("Government"));
        tokens.put("GRAD", createAttrToken("Graduation"));
        tokens.put("HEIR", createAttrToken("Heir"));
        tokens.put("HISTORY", createAttrToken("History"));
        tokens.put("IMMI", createAttrToken("Immigration"));
        tokens.put("INFANT", createAttrToken("Infant"));
        tokens.put("INFORMANT", createAttrToken("Informant"));
        tokens.put("INTERVIEW", createAttrToken("Interview"));
        tokens.put("INTERVIEWER", createAttrToken("Interviewer"));
        tokens.put("ISSUE", createAttrToken("Issue"));
        tokens.put("ITEM", createAttrToken("Item"));
        tokens.put("JOURNAL", createAttrToken("Journal"));
        tokens.put("LANG", createAttrToken("Language"));
        tokens.put("LETTER", createAttrToken("Letter"));
        tokens.put("LINE", createAttrToken("Line"));
        tokens.put("LINEAGE", createAttrToken("Lineage"));
        tokens.put("MAGAZINE", createAttrToken("Magazine"));
        tokens.put("MALE", createAttrToken("Male"));
        tokens.put("MANUSCRIPT", createAttrToken("Manuscript"));
        tokens.put("MAP", createAttrToken("Map"));
        tokens.put("MARB", createAttrToken("Marriage Bans"));
        tokens.put("MARL", createAttrToken("Marriage License"));
        tokens.put("MARR", createAttrToken("Marriage"));
        tokens.put("MARRIED", createAttrToken("Married"));
        tokens.put("MEDI", createAttrToken("Media"));
        tokens.put("MEMBER", createAttrToken("Member"));
        tokens.put("MILITARY", createAttrToken("Military"));
        tokens.put("MOTH", createAttrToken("Mother"));
    }

    /**
     * Initialize the 2nd half of the tokens that go to the standard attribute
     * factory.
     */
    private void initAttributeTokens1() {
        tokens.put("NAMR", createAttrToken("Name (religious)"));
        tokens.put("NAMING", createAttrToken("Naming"));
        tokens.put("NAMS", createAttrToken("NAMS"));
        tokens.put("NATU", createAttrToken("Naturalized"));
        tokens.put("NCHI", createAttrToken("Number of Children"));
        tokens.put("NEWLINE", createAttrToken("Newline"));
        tokens.put("NEWSPAPER", createAttrToken("Newspaper"));
        tokens.put("NMR", createAttrToken("Not Married"));
        tokens.put("NUMBER", createAttrToken("Number"));
        tokens.put("OCCU", createAttrToken("Occupation"));
        tokens.put("ORDI", createAttrToken("Ordinance process flag"));
        tokens.put("ORDERED", createAttrToken("Ordered"));
        tokens.put("ORGANIZATION", createAttrToken("Organization"));
        tokens.put("ORIGINAL", createAttrToken("Original"));
        tokens.put("OTHER", createAttrToken("Other"));
        tokens.put("PAGE", createAttrToken("Page"));
        tokens.put("PERIODICAL", createAttrToken("Periodical"));
        tokens.put("PERSONAL", createAttrToken("Personal"));
        tokens.put("PHON", createAttrToken("Phone Number"));
        tokens.put("PHOTO", createAttrToken("Photograph"));
        tokens.put("PHOTOCOPY", createAttrToken("Photocopy"));
        tokens.put("PLANNED", createAttrToken("Planned"));
        tokens.put("PLOT", createAttrToken("Plot"));
        tokens.put("PROB", createAttrToken("Probate"));
        tokens.put("PROVED", createAttrToken("Proved"));
        tokens.put("PUBL", createAttrToken("Published"));
        tokens.put("QUAY", createAttrToken("Surety"));
        tokens.put("RECITED", createAttrToken("Recited"));
        tokens.put("REFN", createAttrToken("Reference Number"));
        tokens.put("RELI", createAttrToken("Religion"));
        tokens.put("REPO", createAttrToken("Repository"));
        tokens.put("RESI", createAttrToken("Residence"));
        tokens.put("RESN", createAttrToken("Restriction"));
        tokens.put("RETI", createAttrToken("Retired"));
        tokens.put("ROLE", createAttrToken("Role"));
        tokens.put("SEX", createAttrToken("Sex"));
        tokens.put("SINGLE", createAttrToken("Single"));
        tokens.put("SITE", createAttrToken("Site"));
        tokens.put("SOUND", createAttrToken("Sound"));
        tokens.put("SPOU", createAttrToken("Spouse"));
        tokens.put("SSN", createAttrToken("Social Security Number"));
        tokens.put("STILLBORN", createAttrToken("Stillborn"));
        tokens.put("SUBMITTED", createAttrToken("Submitted"));
        tokens.put("TEMP", createAttrToken("Temple code"));
        tokens.put("TEXT", createAttrToken("Text"));
        tokens.put("TIME", createAttrToken("Time"));
        tokens.put("TITL", createAttrToken("Title"));
        tokens.put("TO", createAttrToken("To"));
        tokens.put("TOKEN", createAttrToken("Token"));
        tokens.put("TOMBSTONE", createAttrToken("Tombstone"));
        tokens.put("TRADITION", createAttrToken("Tradition"));
        tokens.put("TRANSCRIBER", createAttrToken("Transcriber"));
        tokens.put("TRANSCRIPT", createAttrToken("Transcript"));
        tokens.put("TYPE", createAttrToken("Type"));
        tokens.put("UNDERSCORE", createAttrToken("Underscore"));
        tokens.put("UNICODE", createAttrToken("Unicode"));
        tokens.put("UNPUBLISHED", createAttrToken("Unpublished"));
        tokens.put("UNVEIL", createAttrToken("Headstone unveiled"));
        tokens.put("VERS", createAttrToken("Version"));
        tokens.put("VIDEO", createAttrToken("Video"));
        tokens.put("VITAL", createAttrToken("Vital"));
        tokens.put("WIDOWED", createAttrToken("Widowed"));
        tokens.put("WILL", createAttrToken("Will"));
        tokens.put("WITN", createAttrToken("Witness"));
        tokens.put("XREF", createAttrToken("Cross Reference"));
    }

    /**
     * @param fullstring the token string
     * @return the token object
     */
    private GedToken createAttrToken(final String fullstring) {
        return new GedToken(fullstring, AbstractGedObjectFactory.ATTR_FACTORY);
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
