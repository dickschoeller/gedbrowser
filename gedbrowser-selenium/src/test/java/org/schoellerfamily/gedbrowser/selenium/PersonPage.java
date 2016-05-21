package org.schoellerfamily.gedbrowser.selenium;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
public final class PersonPage extends PageBase {
    // FIXME reimplement this to go to the database for information

    /** */
    private static final Map<String, Expectations> EXPECTATIONS_MAP =
            new HashMap<>();
    static {
        EXPECTATIONS_MAP.put("I11",
                new Expectations(
                    "Person: I11 - Karl Frederick Schoeller"
                            + " (17 FEB 1907-08 APR 1985)",
                    "I32",
                    "Friedrich Schöller (I32) (10 DEC 1870-01 FEB 1958)",
                    "I33",
                    "Clara Savilla Achenbach (I33) (27 JUN 1870-09 DEC 1955)",
                    "I12",
                    null));
        EXPECTATIONS_MAP.put("I32",
                new Expectations(
                    "Person: I32 - Friedrich Schöller"
                            + " (10 DEC 1870-01 FEB 1958)",
                    "I99",
                    "Johannes Schöller (I99) (22 OCT 1847-)",
                    "I104",
                    "Maria Barbara Krimmel (I104) (09 NOV 1853-19 JUL 1888)",
                    "I11",
                    null));
        EXPECTATIONS_MAP.put("I99",
                new Expectations(
                    "Person: I99 - Johannes Schöller"
                            + " (22 OCT 1847-)",
                    "I180",
                    "Matthias Schöller (I180) (15 NOV 1818-)",
                    "I181",
                    "Anna Maria Müller (I181) (26 JUN 1819-)",
                    "I32",
                    null));
        EXPECTATIONS_MAP.put("I180",
            new Expectations(
                    "Person: I180 - Matthias Schöller"
                        + " (15 NOV 1818-)",
                    "I3104",
                    "Johannes Schöller (I3104) (16 JAN 1785-)",
                    "I3105",
                    "Veronika Conzelmann (I3105) (12 JUL 1793-28 FEB 1855)",
                    "I3554",
                    null));
        EXPECTATIONS_MAP.put("I33",
                new Expectations(
                        "Person: I33 - Clara Savilla Achenbach"
                        + " (27 JUN 1870-09 DEC 1955)",
                        "I105",
                        "Israel Achenbach (I105) (24 FEB 1849-19 JAN 1916)",
                        "I117",
                        "Sara Alice Zimmerman (I117) (15 DEC 1853-25 JUL 1929)",
                        "I11",
                        null));
        EXPECTATIONS_MAP.put("I117",
                new Expectations(
                        "Person: I117 - Sara Alice Zimmerman"
                        + " (15 DEC 1853-25 JUL 1929)",
                        "I204",
                        "William Zimmerman (I204) (04 JUL 1829-28 AUG 1903)",
                        "I207",
                        "Susan Derr (I207) (28 OCT 1836-04 APR 1913)",
                        "I33",
                        null));
        EXPECTATIONS_MAP.put("I3554",
                new Expectations(
                    "Person: I3554 - Johann Martin Schöller (07 OCT 1842-)",
                    "I180",
                    "Matthias Schöller (I180) (15 NOV 1818-)",
                    "I181",
                    "Anna Maria Müller (I181) (26 JUN 1819-)",
                    "I3881",
                    null));
        EXPECTATIONS_MAP.put("I3881",
                new Expectations(
                    "Person: I3881 - Anna Maria Schöller (28 JUL 1873-)",
                    "I3554",
                    "Johann Martin Schöller (I3554) (07 OCT 1842-)",
                    "I3555",
                    "Christina Scheirer (I3555) (-)",
                    "I3891",
                    null));
        EXPECTATIONS_MAP.put("I3891",
                new Expectations(
                    "Person: I3891 - Maria Berta Faigle (1908-)",
                    "I3882",
                    "Christian Faigle (I3882) (05 OCT 1870-)",
                    "I3881",
                    "Anna Maria Schöller (I3881) (28 JUL 1873-)",
                    null,
                    null));
        EXPECTATIONS_MAP.put("I1",
            new Expectations(
                    "Person: I1 - Melissa Robinson Schoeller (-)",
                    "I2",
                    "Richard John Schoeller (I2) (14 DEC 1958-)",
                    "I3",
                    "Lisa Hope Robinson (I3) (09 MAY 1960-)",
                    null,
                    null));
        EXPECTATIONS_MAP.put("I2",
            new Expectations(
                    "Person: I2 - Richard John Schoeller (14 DEC 1958-)",
                    "I4",
                    "John Vincent Schoeller (I4) (23 JUN 1934-14 SEP 2005)",
                    "I6",
                    "Patricia Ruth Hayes (I6) (31 JUL 1937-)",
                    "I1",
                    null));
        EXPECTATIONS_MAP.put("I3",
            new Expectations(
                    "Person: I3 - Lisa Hope Robinson (09 MAY 1960-)",
                    "I7",
                    "Arnold Robinson (I7) (12 AUG 1917-02 OCT 1969)",
                    "I10",
                    "Estelle Liberman (I10) (26 JUN 1925-)",
                    "I1",
                    null));
        EXPECTATIONS_MAP.put("I4",
            new Expectations(
                    "Person: I4 - John Vincent Schoeller"
                            + " (23 JUN 1934-14 SEP 2005)",
                    "I11",
                    "Karl Frederick Schoeller (I11) (17 FEB 1907-08 APR 1985)",
                    "I14",
                    "Mary Beer Moyer (I14) (13 FEB 1909-21 NOV 2003)",
                    "I2",
                    new GreatGreatGrandparentIds("I180", "I181",
                            "I182", "I185", "I186", "I203", "I204",
                            "I207", null, null, null, null, "I208",
                            "I225", "I226", "I238")));
        EXPECTATIONS_MAP.put("I10",
            new Expectations(
                    "Person: I10 - Estelle Liberman (26 JUN 1925-)",
                    "I30",
                    "Abraham Liberman (I30) (22 FEB 1894-14 OCT 1962)",
                    "I31",
                    "Minnie Rubin (I31) (02 JAN 1894-12 JUN 1983)",
                    "I8",
                    new GreatGreatGrandparentIds(null, null, null, null,
                            null, null, null, null, null, null,
                            null, null, null, null, null, null)));
    }

    /** Database ID associated with this page. */
    private final String id;
    /** Previous page. */
    private final PersonPage previous;

    /**
     * Class represents the leaves in the tree with their person ID
     * strings.
     *
     * @author Dick Schoeller
     */
    private static class GreatGreatGrandparentIds {
        /** */
        private final String ffff;
        /** */
        private final String fffm;
        /** */
        private final String ffmf;
        /** */
        private final String ffmm;
        /** */
        private final String fmff;
        /** */
        private final String fmfm;
        /** */
        private final String fmmf;
        /** */
        private final String fmmm;
        /** */
        private final String mfff;
        /** */
        private final String mffm;
        /** */
        private final String mfmf;
        /** */
        private final String mfmm;
        /** */
        private final String mmff;
        /** */
        private final String mmfm;
        /** */
        private final String mmmf;
        /** */
        private final String mmmm;

        /**
         * @param ffff father's father's father's father
         * @param fffm father's father's father's mother
         * @param ffmf father's father's mother's father
         * @param ffmm father's father's father's mother
         * @param fmff father's mother's father's father
         * @param fmfm father's mother's father's mother
         * @param fmmf father's mother's mother's father
         * @param fmmm father's mother's father's mother
         * @param mfff mother's father's father's father
         * @param mffm mother's father's father's mother
         * @param mfmf mother's father's mother's father
         * @param mfmm mother's father's father's mother
         * @param mmff mother's mother's father's father
         * @param mmfm mother's mother's father's mother
         * @param mmmf mother's mother's mother's father
         * @param mmmm mother's mother's father's mother
         */
        GreatGreatGrandparentIds(final String ffff, final String fffm,
                final String ffmf, final String ffmm, final String fmff,
                final String fmfm, final String fmmf, final String fmmm,
                final String mfff, final String mffm, final String mfmf,
                final String mfmm, final String mmff, final String mmfm,
                final String mmmf, final String mmmm) {
            this.ffff = ffff;
            this.fffm = fffm;
            this.ffmf = ffmf;
            this.ffmm = ffmm;
            this.fmff = fmff;
            this.fmfm = fmfm;
            this.fmmf = fmmf;
            this.fmmm = fmmm;
            this.mfff = mfff;
            this.mffm = mffm;
            this.mfmf = mfmf;
            this.mfmm = mfmm;
            this.mmff = mmff;
            this.mmfm = mmfm;
            this.mmmf = mmmf;
            this.mmmm = mmmm;
        }

        /**
         * @return the ffff
         */
        public String getFfff() {
            return ffff;
        }

        /**
         * @return the fffm
         */
        public String getFffm() {
            return fffm;
        }

        /**
         * @return the ffmf
         */
        public String getFfmf() {
            return ffmf;
        }

        /**
         * @return the ffmm
         */
        public String getFfmm() {
            return ffmm;
        }

        /**
         * @return the fmff
         */
        public String getFmff() {
            return fmff;
        }

        /**
         * @return the fmfm
         */
        public String getFmfm() {
            return fmfm;
        }

        /**
         * @return the fmmf
         */
        public String getFmmf() {
            return fmmf;
        }

        /**
         * @return the fmmm
         */
        public String getFmmm() {
            return fmmm;
        }

        /**
         * @return the mfff
         */
        public String getMfff() {
            return mfff;
        }

        /**
         * @return the mffm
         */
        public String getMffm() {
            return mffm;
        }

        /**
         * @return the mfmf
         */
        public String getMfmf() {
            return mfmf;
        }

        /**
         * @return the mfmm
         */
        public String getMfmm() {
            return mfmm;
        }

        /**
         * @return the mmff
         */
        public String getMmff() {
            return mmff;
        }

        /**
         * @return the mmfm
         */
        public String getMmfm() {
            return mmfm;
        }

        /**
         * @return the mmmf
         */
        public String getMmmf() {
            return mmmf;
        }

        /**
         * @return the mmmm
         */
        public String getMmmm() {
            return mmmm;
        }
    }

    /**
     * This class holds the expected values for a number of different
     * aspects of a person page.
     *
     * @author Dick Schoeller
     */
    private static class Expectations {
        /** */
        private final String title;
        /** */
        private final String fatherId;
        /** */
        private final String fatherString;
        /** */
        private final String motherId;
        /** */
        private final String motherString;
        /** */
        private final String childOneId;
        /** */
        private final GreatGreatGrandparentIds leaves;

        /**
         * @param title expected title string
         * @param fatherId expected father ID string
         * @param fatherString expected father name and ID string
         * @param motherId expected mother ID string
         * @param motherString expected mother name and ID string
         * @param childOneId expected ID string of first child
         * @param leaves the set of ancestors at the leaf nodes
         */
        Expectations(final String title, final String fatherId,
                final String fatherString, final String motherId,
                final String motherString, final String childOneId,
                final GreatGreatGrandparentIds leaves) {
            this.title = title;
            this.fatherId = fatherId;
            this.fatherString = fatherString;
            this.motherId = motherId;
            this.motherString = motherString;
            this.childOneId = childOneId;
            this.leaves = leaves;
        }

        /**
         * @return the expected title string
         */
        private String getTitle() {
            return title;
        }

        /**
         * @return the expected father ID
         */
        private String getFatherId() {
            return fatherId;
        }

        /**
         * @return the expected father string
         */
        private String getFatherString() {
            return fatherString;
        }

        /**
         * @return the expected mother ID
         */
        private String getMotherId() {
            return motherId;
        }

        /**
         * @return the expected mother string
         */
        private String getMotherString() {
            return motherString;
        }

        /**
         * @return the expected first child ID
         */
        private String getChildOneId() {
            return childOneId;
        }

        /**
         * @return the leaves
         */
        private GreatGreatGrandparentIds getLeaves() {
            return leaves;
        }

    }

    /**
     * PageObject pattern for a page representing a person.
     *
     * @param driver this is the basic web driver
     * @param id this is the ID of the person page being tested
     * @param previous where we came from. Can be null
     * @param waiter handles driver specific waits
     */
    public PersonPage(final WebDriver driver, final String id,
            final PersonPage previous, final PageWaiter waiter) {
        super(driver, url(id), waiter);
        this.id = id;
        this.previous = previous;
    }

    /**
     * Build the URL string for this page.
     *
     * @param id the ID of the person on the page
     * @return the built url string
     */
    private static String url(final String id) {
        return BASE_URL + "person?db=schoeller&id=" + id;
    }

    /**
     * Navigate by clicking on a link and then return the new page object.
     * @param linkText the partial link text
     *
     * @return the newly created page object
     */
    public PersonPage navigate(final String linkText) {
        final WebElement element = getDriver().findElement(By
                .partialLinkText(linkText));
        return navigate(element);
    }

    /**
     * @return newly created page object for this page's father
     */
    public PersonPage navigateFather() {
        return navigate(getFatherLink());
    }

    /**
     * @return newly created page object for this page's mother
     */
    public PersonPage navigateMother() {
        return navigate(getMotherLink());
    }

    /**
     * Navigate to the indexed child.
     * @param familyIndex the index of the family containing the child
     * @param childIndex the index of the child
     *
     * @return newly created page object for this page's child
     */
    public PersonPage navigateChild(final int familyIndex,
            final int childIndex) {
        return navigate(getChildLink(familyIndex, childIndex));
    }

    /**
     * @param element the element to click on
     * @return the newly created page object
     */
    private PersonPage navigate(final WebElement element) {
        element.click();
        waitForPageLoaded();
        final String url = getCurrentUrl();
        final int index = url.indexOf("id=") + "id=".length();
        final String idText = url.substring(index);
        return new PersonPage(getDriver(), idText, this, getPageWaiter());
    }

    /**
     * Go back to the previous person.
     *
     * @return the associated page object.
     */
    public PersonPage back() {
        if (previous != null) {
            navigateBack();
        }
        return previous;
    }

    /**
     * Check that it is the right title.
     *
     * @return true if the title matches the expected value
     */
    public boolean titleCheck() {
        // Logs pages visited
        System.out.println("Page title is: " + getTitle());
        return getTitle().equals(getExpectedTitleString());
    }

    /**
     * Check that it is the right father.
     *
     * @return true if the title matches the expected value
     */
    public boolean fatherCheck() {
        return getFatherString().equals(getExpectedFatherString());
    }

    /**
     * Check that it is the right mother.
     *
     * @return true if the title matches the expected value
     */
    public boolean motherCheck() {
        return getMotherString().equals(getExpectedMotherString());
    }

    /**
     * Get the father item and return the string contents.
     *
     * @return the father's name and ID on this page.
     */
    private String getFatherString() {
        return getFather().getText();
    }

    /**
     * Get the mother item and return the string contents.
     *
     * @return the mother's name and ID on this page.
     */
    private String getMotherString() {
        return getMother().getText();
    }

    /**
     * Find the a tag inside the father element.
     *
     * @return the web element for the a tag inside the father element
     */
    private WebElement getFatherLink() {
        return getFather().findElement(By.tagName("a"));
    }

    /**
     * Find the element with the ID father.
     *
     * @return the father element
     */
    private WebElement getFather() {
        return getWebElement(By.id("father"));
    }

    /**
     * Find the a tag inside the mother element.
     *
     * @return the web element for the a tag inside the mother element
     */
    private WebElement getMotherLink() {
        return getMother().findElement(By.tagName("a"));
    }

    /**
     * Find the element with the ID mother.
     *
     * @return the mother element
     */
    private WebElement getMother() {
        return getWebElement(By.id("mother"));
    }

    /**
     * Find the a tag inside the indexed child element.
     *
     * @param familyIndex the index of the family of the child
     * @param childIndex the index of the desired child
     * @return the web element for the a tag inside the mother element
     */
    private WebElement getChildLink(
            final int familyIndex, final int childIndex) {
        return getChild(familyIndex, childIndex).findElement(By.tagName("a"));
    }

    /**
     * Find the indexed child element.
     *
     * @param familyIndex the index of the family of the child
     * @param childIndex the index of the desired child
     * @return the web element for the a tag inside the mother element
     */
    private WebElement getChild(final int familyIndex, final int childIndex) {
        final WebElement childId = getWebElement(
                By.id("family-" + familyIndex + "-child-" + childIndex));
        return childId;
    }

    /**
     * Checks whether the father link points to the expected URL.
     *
     * @return true if the father link is correct
     */
    public boolean fatherLinkCheck() {
        final String fatherUrl = getFatherLink().getAttribute("href");
        return fatherUrl.equals(getExpectedFatherUrl());
    }

    /**
     * Checks whether the mother link points to the expected URL.
     *
     * @return true if the mother link is correct
     */
    public boolean motherLinkCheck() {
        final String motherUrl = getMotherLink().getAttribute("href");
        return motherUrl.equals(getExpectedMotherUrl());
    }

    /**
     * Check if the first child is as expected. Knows about no first child.
     *
     * @return true if matches false if doesn't
     */
    public boolean childOneCheck() {
        final String childIdString = getExpectedChildOne();
        try {
            final WebElement childLink = getChildLink(1, 1);
            final String childUrl = childLink.getAttribute("href");
            return childUrl.equals(
                PersonPage.BASE_URL + "person?db=schoeller&id="
                        + childIdString);
        } catch (NoSuchElementException e) {
            return childIdString == null;
        }
    }

    /**
     * Check page correctness.
     *
     * @return empty string if person is OK, list of failures otherwise
     */
    public String check() {
        final StringBuilder status = new StringBuilder();
        if (!titleCheck()) {
            status.append("Title failed\n");
        }
        if (!fatherCheck()) {
            status.append("Father failed\n");
        }
        if (!motherCheck()) {
            status.append("Mother failed\n");
        }
        if (!fatherLinkCheck()) {
            status.append("Father link failed\n");
        }
        if (!motherLinkCheck()) {
            status.append("Mother link failed\n");
        }
        if (!childOneCheck()) {
            status.append("Child one failed\n");
        }
        if (!leavesCheck()) {
            status.append("Leaf check failed\n");
        }
        if (status.length() != 0) {
            status.insert(0, "Id: " + id + "\n");
        }
        return status.toString();
    }

    /**
     * Check the leaf nodes in the tree structure. If no leaves are
     * defined, return true. If the leaves are defined, compare the
     * person IDs with expectations.
     *
     * @return true if all of the leaves align with expectations.
     */
    private boolean leavesCheck() {
        if (EXPECTATIONS_MAP.get(id).leaves == null) {
            return true;
        }
        if (!leafCheck("tree-1x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFfff())) {
            System.out.println("failed 1x9");
            return false;
        }
        if (!leafCheck("tree-3x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFffm())) {
            System.out.println("failed 3x9");
            return false;
        }
        if (!leafCheck("tree-5x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFfmf())) {
            System.out.println("failed 5x9");
            return false;
        }
        if (!leafCheck("tree-7x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFfmm())) {
            System.out.println("failed 7x9");
            return false;
        }
        if (!leafCheck("tree-9x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFmff())) {
            System.out.println("failed 9x9");
            return false;
        }
        if (!leafCheck("tree-11x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFmfm())) {
            System.out.println("failed 11x9");
            return false;
        }
        if (!leafCheck("tree-13x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFmmf())) {
            System.out.println("failed 13x9");
            return false;
        }
        if (!leafCheck("tree-15x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFmmm())) {
            System.out.println("failed 15x9");
            return false;
        }
        if (!leafCheck("tree-17x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMfff())) {
            System.out.println("failed 17x9");
            return false;
        }
        if (!leafCheck("tree-19x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMffm())) {
            System.out.println("failed 19x9");
            return false;
        }
        if (!leafCheck("tree-21x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMfmf())) {
            System.out.println("failed 21x9");
            return false;
        }
        if (!leafCheck("tree-23x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMfmm())) {
            System.out.println("failed 23x9");
            return false;
        }
        if (!leafCheck("tree-25x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMmff())) {
            System.out.println("failed 25x9");
            return false;
        }
        if (!leafCheck("tree-27x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMmfm())) {
            System.out.println("failed 27x9");
            return false;
        }
        if (!leafCheck("tree-29x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMmmf())) {
            System.out.println("failed 29x9");
            return false;
        }
        if (!leafCheck("tree-31x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMmmm())) {
            System.out.println("failed 31x9");
            return false;
        }
        return true;
    }

    /**
     * Check a single leaf in the tree.
     *
     * @param elementId the id attribute for the table cell
     * @param personId the person ID expected in that cell.
     * @return true if the cell contains what is expected
     */
    private boolean leafCheck(final String elementId, final String personId) {
        final WebElement leaf = getWebElement(By.id(elementId));
        try {
            final WebElement link = leaf.findElement(By.tagName("a"));
            final String linkUrl = link.getAttribute("href");
            return linkUrl.equals(url(personId));
        } catch (NoSuchElementException e) {
            return personId == null;
        }
    }

    /**
     * @return the expected title string for this page
     */
    private String getExpectedTitleString() {
        return EXPECTATIONS_MAP.get(id).getTitle();
    }

    /**
     * @return the expected father string for this page
     */
    private String getExpectedFatherString() {
        return EXPECTATIONS_MAP.get(id).getFatherString();
    }

    /**
     * @return the expected mother string for this page
     */
    private String getExpectedMotherString() {
        return EXPECTATIONS_MAP.get(id).getMotherString();
    }

    /**
     * @return the URL of the father for the current page
     */
    private String getExpectedFatherUrl() {
        return url(EXPECTATIONS_MAP.get(id).getFatherId());
    }

    /**
     * @return the URL of the mother for the current page
     */
    private String getExpectedMotherUrl() {
        return url(EXPECTATIONS_MAP.get(id).getMotherId());
    }

    /**
     * @return the ID of the first child for the current page
     */
    private String getExpectedChildOne() {
        return EXPECTATIONS_MAP.get(id).getChildOneId();
    }

    /**
     * @return the ID string for this page
     */
    public String getId() {
        return id;
    }
}
