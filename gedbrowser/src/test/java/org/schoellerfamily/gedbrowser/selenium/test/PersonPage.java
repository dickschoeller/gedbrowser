package org.schoellerfamily.gedbrowser.selenium.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CommentSize", "PMD.GodClass" })
public final class PersonPage extends PageBase {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private static final boolean PRINT_NAVIGATION = "true"
            .equals(System.getProperty("printNavigation", "false"));

    /** */
    private static final Map<String, Expectations> EXPECTATIONS_MAP =
            new HashMap<>();
    static {
        EXPECTATIONS_MAP.put("I6",
                new Expectations(
                        "Reginald Amass Williams (25 JAN 1918-2 FEB 1996) - I6 - gl120368",
                        "I9",
                        "Edwin Elijah A Williams (1883-1951) [I9]",
                        "I15",
                        "Ethel Ruth Moore (1893-1968) [I15]",
                        "I8",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I22", "I33"),
                                                new ParentIds("I127", "I27")
                                                ),
                                        new GrandParentIds(
                                                new ParentIds("I384", "I92"),
                                                new ParentIds("I64", "I36")
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I627", "I502"),
                                                new ParentIds("I3591", "I3592")
                                                ),
                                        new GrandParentIds(
                                                new ParentIds("I621", "I622"),
                                                new ParentIds("I617", "I616")
                                                )
                                        )
                                )
                        )
                );
        EXPECTATIONS_MAP.put("I9",
                new Expectations(
                        "Edwin Elijah A Williams (13 DEC 1883-ABT AUG 1951) - I9 - gl120368",
                        "I10",
                        "Thomas Harris Williams (1826-1903) [I10]",
                        "I11",
                        "Mary Amelia Shepperd Amass (1856-1929) [I11]",
                        "I6",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I372", "I373"),
                                                new ParentIds("I259", "I594")
                                                ),
                                        new GrandParentIds(
                                                new ParentIds("I154", "I641"),
                                                new ParentIds("I1479", "I1480")
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I418", "I1258"),
                                                new ParentIds("I23", "I93")
                                                ),
                                        new GrandParentIds(
                                                new ParentIds("I415", "I861"),
                                                new ParentIds("I857", "I856")
                                                )
                                        )
                                )
                        )
                );
        // "I10"
        EXPECTATIONS_MAP.put("I10",
                new Expectations(
                        "Thomas Harris Williams (1826-5 NOV 1903) - I10 - gl120368",
                        "I193",
                        "Thomas Williams (1802-1886) [I193]",
                        "I21",
                        "Sarah Harris (1798-1884) [I21]",
                        null,
                        null
                        ));
        // "I15"
        EXPECTATIONS_MAP.put("I15",
                new Expectations(
                        "Ethel Ruth Moore (7 DEC 1893-9 JUL 1968) - I15 - gl120368",
                        "I538",
                        "William Moore (1842-1915) [I538]",
                        "I539",
                        "Emily Francis Hunt (1860-) [I539]",
                        "I6",
                        null
                        ));
        // "I22"
        EXPECTATIONS_MAP.put("I22",
                new Expectations(
                        "John Williams (ABT 1778-ABT AUG 1840) - I22 - gl120368",
                        "I372",
                        "George Williams (1751-1810) [I372]",
                        "I373",
                        "Elizabeth Ayres (1755-) [I373]",
                        "I164",
                        null
                        ));
        // "I193"
        EXPECTATIONS_MAP.put("I193",
                new Expectations(
                        "Thomas Williams (27 MAR 1802-17 JUN 1886) - I193 - gl120368",
                        "I22",
                        "John Williams (1778-1840) [I22]",
                        "I33",
                        "Elizabeth Tomkins (1776-1851) [I33]",
                        "I10",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds("I376", "I377"),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        )
                                )
                        )
                );
        // "I237"
        EXPECTATIONS_MAP.put("I237",
                new Expectations(
                        "Priscilla COOK (1821-ABT NOV 1886) - I237 - gl120368",
                        "I617",
                        "James Cook (1801-1877) [I617]",
                        "I616",
                        "Priscilla DARKINGS (1799-1856) [I616]",
                        "I3904",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        )
                                )
                        )
                );
        // "I539"
        EXPECTATIONS_MAP.put("I539",
                new Expectations(
                        "Emily Francis Hunt (ABT JUN 1860-) - I539 - gl120368",
                        "I385",
                        "Stephen HUNT (1813-1884) [I385]",
                        "I237",
                        "Priscilla COOK (1821-1886) [I237]",
                        "I480",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        )
                                )
                        )
                );
        // "I616"
        EXPECTATIONS_MAP.put("I616",
                new Expectations(
                        "Priscilla DARKINGS (1799-ABT 1856) - I616 - gl120368",
                        "",
                        "",
                        "",
                        "",
                        "I1409",
                        new GreatGreatGrandParentIds(
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        ),
                                new GreatGrandParentIds(
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                ),
                                        new GrandParentIds(
                                                new ParentIds(null, null),
                                                new ParentIds(null, null)
                                                )
                                        )
                                )
                        )
                );
    }

    /** Database ID associated with this page. */
    private final String id;
    /** Previous page. */
    private final PersonPage previous;
    /** */
    private final String baseUrl;

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
        private final GreatGreatGrandParentIds leaves;

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
                final GreatGreatGrandParentIds leaves) {
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
        private GreatGreatGrandParentIds getLeaves() {
            return leaves;
        }

    }

    /**
     * PageObject pattern for a page representing a person.
     * @param driver this is the basic web driver
     * @param id this is the ID of the person page being tested
     * @param previous where we came from. Can be null
     * @param waiter handles driver specific waits
     * @param baseUrl the base URL from which all others derive
     */
    public PersonPage(final WebDriver driver, final String id,
            final PersonPage previous, final PageWaiter waiter,
            final String baseUrl) {
        super(driver, url(baseUrl, id), waiter);
        this.id = id;
        this.previous = previous;
        this.baseUrl = baseUrl;
    }

    /**
     * Build the URL string for this page.
     *
     * @param baseUrl the base from which all URLs are derived
     * @param id the ID of the person on the page
     * @return the built url string
     */
    private static String url(final String baseUrl, final String id) {
        if (baseUrl == null || baseUrl.isEmpty() || id == null
                || id.isEmpty()) {
            return "";
        }
        return baseUrl + "person?db=gl120368&id=" + id;
    }

    /**
     * Build the URL string for the provided id.
     *
     * @param iD the ID of the person linked
     * @return the built url string
     */
    private String url(final String iD) {
        return url(baseUrl(), iD);
    }

    /**
     * @return the base URL
     */
    private String baseUrl() {
        return baseUrl;
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
        final String newUrl = element.getAttribute("href");
        element.click();
        waitForPageLoaded(newUrl);
        final String url = getCurrentUrl();
        final int index = url.indexOf("id=") + "id=".length();
        final String idText = url.substring(index);
        return new PersonPage(getDriver(), idText, this, getPageWaiter(),
                baseUrl());
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
    public String titleCheck() {
        // Logs pages visited
        final String actual = getTitle();
        final String expected = getExpectedTitleString();
        if (actual.equals(expected)) {
            return "";
        }
        return "Title mismatch, expected: [" + expected + "], actual: ["
                + actual + "]\n";
    }

    /**
     * Check that it is the right father.
     *
     * @return true if the title matches the expected value
     */
    public String fatherCheck() {
        final String actual = getFatherString();
        final String expected = getExpectedFatherString();
        if (actual.equals(expected)) {
            return "";
        }
        return "Father mismatch: expected: [" + expected + "], actual: ["
                + actual + "]\n";
    }

    /**
     * Check that it is the right mother.
     *
     * @return true if the title matches the expected value
     */
    public String motherCheck() {
        final String actual = getMotherString();
        final String expected = getExpectedMotherString();
        if (actual.equals(expected)) {
            return "";
        }
        return "Mother mismatch: expected: [" + expected + "], actual: ["
                + actual + "]\n";
    }

    /**
     * Get the father item and return the string contents.
     *
     * @return the father's name and ID on this page.
     */
    private String getFatherString() {
        try {
            final WebElement father = getFather();
            if (father == null) {
                return "";
            }
            return father.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /**
     * Get the mother item and return the string contents.
     *
     * @return the mother's name and ID on this page.
     */
    private String getMotherString() {
        try {
            final WebElement mother = getMother();
            if (mother == null) {
                return "";
            }
            return mother.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /**
     * Find the a tag inside the father element.
     *
     * @return the web element for the a tag inside the father element
     */
    private WebElement getFatherLink() {
        try {
            final WebElement father = getFather();
            if (father == null) {
                return null;
            }
            return father.findElement(By.tagName("a"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Find the element with the ID father.
     *
     * @return the father element
     */
    private WebElement getFather() {
        try {
            return getWebElement(By.id("father"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Find the a tag inside the mother element.
     *
     * @return the web element for the a tag inside the mother element
     */
    private WebElement getMotherLink() {
        try {
            final WebElement mother = getMother();
            if (mother == null) {
                return null;
            }
            return mother.findElement(By.tagName("a"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Find the element with the ID mother.
     *
     * @return the mother element
     */
    private WebElement getMother() {
        try {
            return getWebElement(By.id("mother"));
        } catch (NoSuchElementException e) {
            return null;
        }
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
        return getWebElement(
                By.id("family-" + familyIndex + "-child-" + childIndex));
    }

    /**
     * Checks whether the father link points to the expected URL.
     *
     * @return true if the father link is correct
     */
    public String fatherLinkCheck() {
        final String fatherUrl = getFatherLinkUrl();
        final String expectedFatherUrl = getExpectedFatherUrl();
        if (fatherUrl.equals(expectedFatherUrl)) {
            return "";
        }
        return "Father link mismatch, expected: [" + expectedFatherUrl
                + "], actual: [" + fatherUrl + "]\n";
    }

    /**
     * @return the father link URL or empty string if not found
     */
    private String getFatherLinkUrl() {
        final WebElement fatherLink = getFatherLink();
        if (fatherLink == null) {
            return "";
        }
        final String fatherUrl = fatherLink.getAttribute("href");
        if (fatherUrl == null) {
            return "";
        }
        return fatherUrl;
    }

    /**
     * Checks whether the mother link points to the expected URL.
     *
     * @return true if the mother link is correct
     */
    public String motherLinkCheck() {
        final String motherUrl = getMotherLinkUrl();
        final String expectedMotherUrl = getExpectedMotherUrl();
        if (motherUrl.equals(expectedMotherUrl)) {
            return "";
        }
        return "Mother link mismatch, expected: [" + expectedMotherUrl
                + "], actual: [" + motherUrl + "]\n";
    }

    /**
     * @return the mother link URL or empty string if not found
     */
    private String getMotherLinkUrl() {
        final WebElement motherLink = getMotherLink();
        if (motherLink == null) {
            return "";
        }
        final String motherUrl = motherLink.getAttribute("href");
        if (motherUrl == null) {
            return "";
        }
        return motherUrl;
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
            return childUrl.equals(url(childIdString));
        } catch (NoSuchElementException e) {
            return childIdString == null;
        }
    }

    /**
     * Check page correctness.
     *
     * @return empty string if person is OK, list of failures otherwise
     */
    @SuppressWarnings("PMD.NPathComplexity")
    public String check() {
        final StringBuilder status = new StringBuilder();
        println("        title check");
        final String titleCheckResult = titleCheck();
        if (!titleCheckResult.isEmpty()) {
            status.append(titleCheckResult);
        }
        println("        father check");
        final String fatherCheckResult = fatherCheck();
        if (!fatherCheckResult.isEmpty()) {
            status.append(fatherCheckResult);
        }
        println("        mother check");
        final String motherCheckResult = motherCheck();
        if (!motherCheckResult.isEmpty()) {
            status.append(motherCheckResult);
        }
        println("        father link check");
        final String fatherLinkCheckResult = fatherLinkCheck();
        if (!fatherLinkCheckResult.isEmpty()) {
            status.append(fatherLinkCheckResult);
        }
        println("        mother link check");
        final String motherLinkCheckResult = motherLinkCheck();
        if (!motherLinkCheckResult.isEmpty()) {
            status.append(motherLinkCheckResult);
        }
        println("        child one check");
        if (!childOneCheck()) {
            status.append("Child one failed\n");
        }
        println("        leaves check");
        if (!leavesCheck()) {
            status.append("Leaf check failed\n");
        }
        println("        final status check");
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
    @SuppressWarnings({ "PMD.CyclomaticComplexity",
            "PMD.ModifiedCyclomaticComplexity", "PMD.NPathComplexity",
            "PMD.StdCyclomaticComplexity" })
    private boolean leavesCheck() {
        if (EXPECTATIONS_MAP.get(id).leaves == null) {
            return true;
        }
        if (!leafCheck("tree-1x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFfff())) {
            println("            failed 1x9");
            return false;
        }
        if (!leafCheck("tree-3x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFffm())) {
            println("            failed 3x9");
            return false;
        }
        if (!leafCheck("tree-5x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFfmf())) {
            println("            failed 5x9");
            return false;
        }
        if (!leafCheck("tree-7x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFfmm())) {
            println("            failed 7x9");
            return false;
        }
        if (!leafCheck("tree-9x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFmff())) {
            println("            failed 9x9");
            return false;
        }
        if (!leafCheck("tree-11x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFmfm())) {
            println("            failed 11x9");
            return false;
        }
        if (!leafCheck("tree-13x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFmmf())) {
            println("            failed 13x9");
            return false;
        }
        if (!leafCheck("tree-15x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getFmmm())) {
            println("            failed 15x9");
            return false;
        }
        if (!leafCheck("tree-17x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMfff())) {
            println("            failed 17x9");
            return false;
        }
        if (!leafCheck("tree-19x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMffm())) {
            println("            failed 19x9");
            return false;
        }
        if (!leafCheck("tree-21x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMfmf())) {
            println("            failed 21x9");
            return false;
        }
        if (!leafCheck("tree-23x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMfmm())) {
            println("            failed 23x9");
            return false;
        }
        if (!leafCheck("tree-25x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMmff())) {
            println("            failed 25x9");
            return false;
        }
        if (!leafCheck("tree-27x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMmfm())) {
            println("            failed 27x9");
            return false;
        }
        if (!leafCheck("tree-29x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMmmf())) {
            println("            failed 29x9");
            return false;
        }
        if (!leafCheck("tree-31x9",
                EXPECTATIONS_MAP.get(id).getLeaves().getMmmm())) {
            println("            failed 31x9");
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

    /**
     * Print the provide string.
     *
     * @param string the string to print
     */
    private void println(final String string) {
        if (PRINT_NAVIGATION) {
            logger.info(string);
        }
    }
}
