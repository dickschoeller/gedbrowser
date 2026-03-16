package org.schoellerfamily.gedbrowser.selenium.pageobjects;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CommentSize", "PMD.GodClass" })
@Slf4j
public final class PersonPage extends PageBase implements MenuPageFacade {
    /** */
    private final MenuPage menuPage = new MenuPageImpl(this);
    /** */
    private static final boolean PRINT_NAVIGATION = "true"
            .equals(System.getProperty("printNavigation", "false"));

    /** */
    private final String id;

    /**
     * PageObject pattern for a page representing a person.
     *
     * @param factory the factory for creating more page objects
     * @param previous where we came from. Can be null
     * @param baseUrl the base URL from which all others derive
     * @param id this is the ID of the person page being tested
     */
    public PersonPage(final PageFactory factory, final PageBase previous,
            final String baseUrl, final String id) {
        super(factory, previous, baseUrl, "person?db=gl120368&id=" + id);
        this.id = id;
    }

    @Override
    protected String getIndexLetter() {
        final String title = getExpectedTitleString();
        final String name = title.substring(0, title.indexOf(" ("));
        final String surname = name.substring(name.lastIndexOf(" ")).trim();
        return surname.substring(0, 1);
    }

    @Override
    public MenuPage getMenuPage() {
        return menuPage;
    }

    private String url(final String baseUrl, final String iD) {
        if (StringUtils.isEmpty(baseUrl) || StringUtils.isEmpty(iD)) {
            return "";
        }
        return getBaseUrl() + "person?db=gl120368&id=" + iD;
    }

    private String url(final String iD) {
        return url(getBaseUrl(), iD);
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

    private PersonPage navigate(final WebElement element) {
        final String newUrl = element.getAttribute("href");
        element.click();
        waitForPageLoaded(newUrl);
        final String url = getCurrentUrl();
        final int index = url.indexOf("id=") + "id=".length();
        final String idText = url.substring(index);
        return getFactory().createPersonPage(this, getBaseUrl(), idText);
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

    private WebElement getFather() {
        try {
            return getWebElement(By.id("father"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

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

    private WebElement getMother() {
        try {
            return getWebElement(By.id("mother"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private WebElement getChildLink(
            final int familyIndex, final int childIndex) {
        return getChild(familyIndex, childIndex).findElement(By.tagName("a"));
    }

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

    @SuppressWarnings({ "PMD.CyclomaticComplexity",
            "PMD.ModifiedCyclomaticComplexity", "PMD.NPathComplexity",
            "PMD.StdCyclomaticComplexity" })
    private boolean leavesCheck() {
        if (getExpectation().getLeaves() == null) {
            return true;
        }
        if (!leafCheck("tree-1x9",
                getExpectation().getLeaves().getFfff())) {
            println("            failed 1x9");
            return false;
        }
        if (!leafCheck("tree-3x9",
                getExpectation().getLeaves().getFffm())) {
            println("            failed 3x9");
            return false;
        }
        if (!leafCheck("tree-5x9",
                getExpectation().getLeaves().getFfmf())) {
            println("            failed 5x9");
            return false;
        }
        if (!leafCheck("tree-7x9",
                getExpectation().getLeaves().getFfmm())) {
            println("            failed 7x9");
            return false;
        }
        if (!leafCheck("tree-9x9",
                getExpectation().getLeaves().getFmff())) {
            println("            failed 9x9");
            return false;
        }
        if (!leafCheck("tree-11x9",
                getExpectation().getLeaves().getFmfm())) {
            println("            failed 11x9");
            return false;
        }
        if (!leafCheck("tree-13x9",
                getExpectation().getLeaves().getFmmf())) {
            println("            failed 13x9");
            return false;
        }
        if (!leafCheck("tree-15x9",
                getExpectation().getLeaves().getFmmm())) {
            println("            failed 15x9");
            return false;
        }
        if (!leafCheck("tree-17x9",
                getExpectation().getLeaves().getMfff())) {
            println("            failed 17x9");
            return false;
        }
        if (!leafCheck("tree-19x9",
                getExpectation().getLeaves().getMffm())) {
            println("            failed 19x9");
            return false;
        }
        if (!leafCheck("tree-21x9",
                getExpectation().getLeaves().getMfmf())) {
            println("            failed 21x9");
            return false;
        }
        if (!leafCheck("tree-23x9",
                getExpectation().getLeaves().getMfmm())) {
            println("            failed 23x9");
            return false;
        }
        if (!leafCheck("tree-25x9",
                getExpectation().getLeaves().getMmff())) {
            println("            failed 25x9");
            return false;
        }
        if (!leafCheck("tree-27x9",
                getExpectation().getLeaves().getMmfm())) {
            println("            failed 27x9");
            return false;
        }
        if (!leafCheck("tree-29x9",
                getExpectation().getLeaves().getMmmf())) {
            println("            failed 29x9");
            return false;
        }
        if (!leafCheck("tree-31x9",
                getExpectation().getLeaves().getMmmm())) {
            println("            failed 31x9");
            return false;
        }
        return true;
    }

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

    private String getExpectedTitleString() {
        return getExpectation().getTitle();
    }

    private String getExpectedFatherString() {
        return getExpectation().getFatherString();
    }

    private String getExpectedMotherString() {
        return getExpectation().getMotherString();
    }

    private String getExpectedFatherUrl() {
        return url(getExpectation().getFatherId());
    }

    private String getExpectedMotherUrl() {
        return url(getExpectation().getMotherId());
    }

    private String getExpectedChildOne() {
        return getExpectation().getChildOneId();
    }

    private Expectations getExpectation() {
        return getExpectationsMap().get(id);
    }

    private Map<String, Expectations> getExpectationsMap() {
        return getFactory().getExpectationsMap();
    }

    /**
     * @return the ID string for this page
     */
    public String getId() {
        return id;
    }

    private void println(final String string) {
        if (PRINT_NAVIGATION) {
            log.info(string);
        }
    }
}
