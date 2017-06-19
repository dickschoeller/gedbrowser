package org.schoellerfamily.gedbrowser.reader.testreader;

import java.io.IOException;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLine;
import org.schoellerfamily.gedbrowser.reader.GedObjectCreator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public final class TestDataReader {
    /** */
    @Autowired
    private transient GedObjectCreator g2g;

    /**
     * Embed some GEDCOM right here in the source. That allows us to test
     * without reading a file.
     */
    private static final String[] A_SOURCE = {
            "0 HEAD",
            "1 SOUR TMG",
            "2 VERS 4.0",
            "1 SUBM @SUB1@",
            "1 GEDC",
            "2 VERS 5.5",
            "2 FORM LINEAGE-LINKED",
            "1 DEST GED55",
            "1 DATE 16 FEB 2001",
            "2 TIME 22:04",
            "1 CHAR ANSI",
            "0 @SUB1@ SUBM",
            "1 NAME Richard/Schoeller/",
            "1 ADDR 242 Marked Tree Road",
            "2 CONT Needham, MA 02492",
            "1 PHON 781.449.5476",
            "0 @I1@ INDI",
            "1 REFN 1",
            "1 NAME Melissa Robinson/Schoeller/",
            "2 SOUR @S2@",
            "1 SEX F",
            "1 CHAN",
            "2 DATE 29 DEC 1996",
            "1 FAMC @F1@",
            "0 @I2@ INDI",
            "1 REFN 2",
            "1 NAME Richard John/Schoeller/",
            "1 SEX M",
            "1 CHAN",
            "2 DATE 29 DEC 1996",
            "1 OCCU Software engineer",
            "2 DATE 06 JUN 1980",
            "1 EDUC BSEE",
            "2 DATE 17 MAY 1980",
            "2 PLAC Rensselaer Polytechnic Institute, Troy, New York, USA",
            "2 SOUR @S4@",
            "1 BIRT",
            "2 DATE 14 DEC 1958",
            "2 PLAC Womack Army Hospital, Fort Bragg, "
                    + "Manchester, Cumberland County, North Carolina, USA",
            "2 SOUR @S3@",
            "1 OCCU a part-time ski instructor.  Full certified PSIA-E",
            "2 DATE BETWEEN 1977 AND 1988",
            "2 SOUR @S28@",
            "1 FAMS @F1@",
            "1 FAMC @F2@",
            "0 @I3@ INDI",
            "1 REFN 3",
            "1 NAME Lisa Hope/Robinson/",
            "1 SEX F",
            "1 CHAN",
            "2 DATE 29 DEC 1996",
            "1 BIRT",
            "2 DATE 09 MAY 1960",
            "2 PLAC Providence Lying-In, Providence, "
                    + "Providence County, Rhode Island, USA",
            "1 OCCU Software engineer & later marketing manager",
            "1 EDUC BSEE",
            "2 DATE 1982",
            "2 PLAC Boston University, Boston, "
                    + "Suffolk County, Massachusetts, USA",
            "1 FAMS @F1@",
            "1 FAMC @F3@",
            "0 @I4@ INDI",
            "1 REFN 4",
            "1 NAME John Vincent/Schoeller/",
            "1 SEX M",
            "1 CHAN",
            "2 DATE 16 JAN 2001",
            "1 OCCU Engineer, salesman & part-time ski instructor",
            "1 EDUC BSEE Lafayette College",
            "2 PLAC Lafayette College, Easton, "
                    + "Northampton County, Pennsylvania, USA",
            "1 NOTE Get birth certificates and "
                    + "marriage license from Mom and Dad",
            "1 BIRT",
            "2 DATE 23 JUN 1934",
            "2 PLAC Doylestown, Bucks County, Pennsylvania, USA",
            "1 EDUC MS ?",
            "2 PLAC Lehigh University, Bethlehem, "
                    + "Northampton County, Pennsylvania, USA",
            "1 FAMS @F2@",
            "0 @I5@ INDI",
            "1 REFN 5",
            "1 NAME Vivian Grace/Schoeller/",
            "1 RESN confidential",
            "1 SEX F",
            "1 CHAN",
            "2 DATE 05 JUL 1997",
            "1 BIRT",
            "2 DATE 16 APR 1960",
            "2 PLAC Easton, Northampton County, Pennsylvania, USA",
            "1 OCCU Systems analyst",
            "1 EDUC BSCS",
            "2 DATE 1982",
            "2 PLAC Bloomsberg State College, Bloomsberg, Pennsylvania, USA",
            "2 SOUR @S28@",
            "1 FAMS @F5@",
            "1 FAMC @F2@",
            "0 @I6@ INDI",
            "1 REFN 6",
            "1 NAME Patricia Ruth/Hayes/",
            "1 SEX F",
            "1 CHAN",
            "2 DATE 14 JAN 2001",
            "1 BIRT",
            "2 DATE 31 JUL 1937",
            "2 PLAC Mineola, Nassau County, New York, USA",
            "1 OCCU Teacher & computer curriculum specialist",
            "1 EDUC BA, Moravian College & MEd Lehigh University",
            "1 FAMS @F2@",
            "0 @I7@ INDI",
            "1 REFN 7",
            "1 NAME Arnold/Robinson/",
            "1 SEX M",
            "1 CHAN",
            "2 DATE 01 OCT 1994",
            "1 BIRT",
            "2 DATE 12 AUG 1917",
            "2 PLAC Providence, Providence County, Rhode Island, USA",
            "2 SOUR @S5@",
            "1 DEAT",
            "2 DATE 02 OCT 1969",
            "2 PLAC Providence, Providence County, Rhode Island, USA",
            "2 NOTE Cause of death: Ante Myocardial Infarction",
            "3 CONT Source: yahrzeit calendar from funeral home",
            "1 BURI",
            "2 DATE 03 OCT 1969",
            "2 PLAC Lincoln Park Cemetery, Warwick, Kent County, "
                    + "Rhode Island, USA",
            "1 NOTE Estelle is looking for birth, death and marriage "
                    + "papers.  She",
            "2 CONT gave me",
            "2 CONT some, more needed.  We supposedly have a copy "
                    + "of the death",
            "2 CONT certificate",
            "2 CONT somewhere.",
            "1 FAMS @F3@",
            "0 @I8@ INDI",
            "1 REFN 8",
            "1 NAME Carol Ann/Robinson/",
            "1 SEX F",
            "1 CHAN",
            "2 DATE 08 JAN 2001",
            "1 FAMS @F8@",
            "1 FAMC @F3@",
            "0 @I9@ INDI",
            "1 REFN 9",
            "1 NAME George Steven/Sacerdote/",
            "1 SEX M",
            "1 CHAN",
            "2 DATE 08 JAN 2001",
            "1 FAMS @F8@",
            "0 @I10@ INDI",
            "1 REFN 10",
            "1 NAME Estelle/Liberman/",
            "1 SEX F",
            "1 CHAN",
            "2 DATE 14 JAN 2001",
            "1 BIRT",
            "2 DATE 26 JUN 1925",
            "2 PLAC Quincy, Norfolk County, Massachusetts, USA",
            "1 FAMS @F3@",
            "1 FAMS @F10@",
            "0 @I752@ INDI",
            "1 REFN 752",
            "1 NAME Ciara Jean/Figliuolo/",
            "2 SOUR @S229@",
            "2 SOUR @S207@",
            "1 SEX F",
            "1 CHAN",
            "2 DATE 05 JUL 1997",
            "1 FAMC @F5@",
            "0 @I753@ INDI",
            "1 REFN 753",
            "1 NAME David Andrew/Sacerdote/",
            "1 SEX M",
            "1 CHAN",
            "2 DATE 08 JAN 2001",
            "1 FAMC @F8@",
            "0 @I754@ INDI",
            "1 REFN 754",
            "1 NAME Michael George/Sacerdote/",
            "1 SEX M",
            "1 CHAN",
            "2 DATE 08 JAN 2001",
            "1 FAMC @F8@",
            "0 @I755@ INDI",
            "1 REFN 755",
            "1 NAME Bernard Harold/Klemer/",
            "1 SEX M",
            "1 CHAN",
            "2 DATE 08 JAN 2001",
            "1 BIRT",
            "2 DATE 31 MAR 1926",
            "2 PLAC Rhode Island, USA",
            "1 FAMS @F10@",
            "0 @I4248@ INDI",
            "1 REFN 4248",
            "1 NAME Sabino/Figliuolo/",
            "1 RESN confidential",
            "2 SOUR @S229@",
            "1 SEX M",
            "1 CHAN",
            "2 DATE 05 JUL 1997",
            "1 FAMS @F5@",
            "0 @I5266@ INDI",
            "1 REFN 5266",
            "1 NAME Cecilia Caterina/Figliuolo/",
            "2 SOUR @S206@",
            "1 SEX F",
            "1 CHAN",
            "2 DATE 05 JUL 1997",
            "1 FAMC @F5@",
            "0 @F1@ FAM",
            "1 HUSB @I2@",
            "1 WIFE @I3@",
            "1 CHIL @I1@",
            "1 CHIL @I5188@",
            "1 MARR",
            "2 DATE 27 MAY 1984",
            "2 PLAC Temple Emanu-el, Providence, "
                    + "Providence County, Rhode Island, USA",
            "2 NOTE The ceremony performed by Rabbi Wayne Franklin "
                    + "and Cantor Ivan",
            "3 CONT Perlman.  The best man and matron of honor "
                    + "were Dale Matcovitch",
            "3 CONT and Carol Robinson Sacerdote.",
            "2 SOUR @S4@",
            "0 @F2@ FAM",
            "1 HUSB @I4@",
            "1 WIFE @I6@",
            "1 CHIL @I2@",
            "1 CHIL @I5@",
            "1 MARR",
            "2 DATE 18 JAN 1958",
            "2 PLAC Rockville Center, Nassau County, New York, USA",
            "0 @F3@ FAM",
            "1 HUSB @I7@",
            "1 WIFE @I10@",
            "1 CHIL @I8@",
            "1 CHIL @I3@",
            "1 MARR",
            "2 DATE 21 JUN 1945",
            "2 PLAC Beacon House, Boston, Suffolk County, Massachusetts, USA",
            "2 SOUR @S12@",
            "0 @F5@ FAM",
            "1 HUSB @I4248@",
            "1 WIFE @I5@",
            "1 CHIL @I752@",
            "1 CHIL @I5266@",
            "0 @F8@ FAM",
            "1 HUSB @I9@",
            "1 WIFE @I8@",
            "1 CHIL @I753@",
            "1 CHIL @I754@",
            "0 @F10@ FAM",
            "1 HUSB @I755@",
            "1 WIFE @I10@",
            "1 MARR",
            "2 DATE 08 JAN 1977",
            "2 PLAC Providence, Providence County, Rhode Island, USA",
            "0 @S2@ SOUR",
            "1 TITL Schoeller, Melissa Robinson, birth certificate",
            "1 ABBR SchoellerMelissaBirthCert",
            "1 NOTE We have the original of this document",
            "0 @S3@ SOUR",
            "1 TITL Schoeller, Richard John, birth certificate",
            "1 ABBR SchoellerRichardBirthCert",
            "1 NOTE I have a certified copy of this document",
            "0 @S4@ SOUR",
            "1 TITL Robinson, Lisa Hope and Schoeller, "
                    + "Richard John, certificate of",
            "2 CONT marriage",
            "1 ABBR RobinsonSchoellerMarCer",
            "1 NOTE We have the original of this document.  "
                    + "The witnesses were Mark",
            "2 CONT A. Friedman, fraternity brother of the groom "
                    + "and Donald S.",
            "2 CONT Friedman, a friend of bride and groom", "0 @S5@ SOUR",
            "1 TITL birth certificate", "1 ABBR birth certificate",
            "0 @S12@ SOUR",
            "1 TITL Interviews with Estelle (Liberman Robinson) Klemer",
            "1 ABBR KlemerEstelleInterviews", "1 AUTH Estelle LIBERMAN",
            "0 @S28@ SOUR",
            "1 TITL Schoeller, Richard, first hand information",
            "1 ABBR SchoellerRichardInterviews",
            "1 AUTH Richard John SCHOELLER", "0 @S206@ SOUR",
            "1 TITL Figliuolo, Cecilia Katerina, birth announcement",
            "1 ABBR FigliuoloCeciliaBirthAnn", "1 AUTH Vivian Grace SCHOELLER",
            "0 @S207@ SOUR",
            "1 TITL Figliuolo, Vivian (Schoeller), telephone interviews",
            "1 ABBR FigliuoloVivianInterviews", "0 @S229@ SOUR",
            "1 TITL Figliuolo Sabino, interview",
            "1 ABBR FigliuoloSabinoInterview", "0 TRLR" };

    /**
     * Smaller test set.
     */
    private static final String[] A_SOURCE_SHORT = {
            "0 HEAD",
            "1 SOUR TMG",
            "2 VERS 4.0",
            "1 SUBM @SUB1@",
            "1 GEDC",
            "2 VERS 5.5",
            "2 FORM LINEAGE-LINKED",
            "1 DEST GED55",
            "1 DATE 16 FEB 2001",
            "2 TIME 22:04",
            "1 CHAR ANSI",
            "0 @SUB1@ SUBM",
            "1 NAME Richard Schoeller",
            "1 ADDR 242 Marked Tree Road",
            "0 @I1@ INDI",
            "1 REFN 1",
            "1 NAME Melissa Robinson/Schoeller/",
            "2 SOUR @S2@",
            "1 SEX F",
            "1 CHAN",
            "2 DATE 29 DEC 1996",
            "1 FAMC @F1@",
            "0 @I2@ INDI",
            "1 REFN 2",
            "1 NAME Richard John/Schoeller/",
            "1 SEX M",
            "1 CHAN",
            "2 DATE 29 DEC 1996",
            "1 OCCU Software engineer",
            "2 DATE 06 JUN 1980",
            "1 EDUC BSEE",
            "2 DATE 17 MAY 1980",
            "2 PLAC Rensselaer Polytechnic Institute, Troy, New York, USA",
            "2 SOUR @S4@",
            "1 BIRT",
            "2 DATE 14 DEC 1958",
            "2 PLAC Womack Army Hospital, Fort Bragg, "
                    + "Manchester, Cumberland County, North Carolina, USA",
            "2 SOUR @S3@",
            "1 OCCU a part-time ski instructor.  Full certified PSIA-E",
            "2 DATE BETWEEN 1977 AND 1988", "2 SOUR @S28@", "1 FAMS @F1@",
            "1 FAMC @F2@", "0 TRLR" };

    /**
     * Private default constructor to prevent instantiation.
     */
    public TestDataReader() {
        // Empty
    }

    /**
     * Prepare data for tests with a biggish data set.
     *
     * @return a populated GedObject tree.
     * @throws IOException because reader might throw.
     */
    public Root readBigTestSource() throws IOException {
        final AbstractGedLine top = new GedLine(A_SOURCE);
        top.readToNext();
        return g2g.create(top);
    }

    /**
     * Prepare data for tests with a smallish data set.
     *
     * @return a populated GedObject tree.
     * @throws IOException because reader might throw.
     */
    public Root readSmallTestSource() throws IOException {
        final AbstractGedLine top = new GedLine(A_SOURCE_SHORT);
        top.readToNext();
        return g2g.create(top);
    }

    /**
     * Read gl120368.ged which is a pretty big file.
     *
     * @return the GedLine hierarchy from parsing this file.
     * @throws IOException because the file reader might throw.
     */
    public Root readFileTestSource() throws IOException {
        final AbstractGedLine top =
                TestResourceReader.readFileTestSource(this, "gl120368.ged");
        top.readToNext();
        return g2g.create(top);
    }
}
