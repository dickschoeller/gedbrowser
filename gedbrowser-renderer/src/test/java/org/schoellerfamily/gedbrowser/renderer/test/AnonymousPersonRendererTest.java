package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.CellRenderer;
import org.schoellerfamily.gedbrowser.renderer.CellRow;
import org.schoellerfamily.gedbrowser.renderer.FamilyRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.TooManyMethods" })
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class AnonymousPersonRendererTest {
    /** */
    @Autowired
    private transient TestDataReader reader;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /**
     * A piece of boiler plate for starting a parent paragraph.
     */
    private static final String START_PARENT = "  <p class=\"parent\">\n";

    /**
     * A piece of boiler plate for ending a paragraph.
     */
    private static final String END_PARAG = "  </p>";

    /**
     * The way we fill in an empty cell.
     */
    private static final String INVISIBLE_STRING = "&nbsp;&nbsp;&nbsp;";

    /**
     * The expected classes in a tree display.
     */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    private static final String[] TREE_CELL_CLASSES = { "", "", "", "", "", "", "", "u", "", "", "",
        "", "", "", "u", "", "t", "", "", "", "", "", "", "v", "", "d", "", "", "", "", "u", "",
        "t", "", "", "", "", "", "", "v", "", "v", "", "u", "", "", "", "", "v", "", "d", "", "t",
        "", "", "", "", "v", "", "", "", "d", "", "", "u", "", "t", "", "", "", "", "", "", "v", "",
        "v", "", "", "", "u", "", "", "v", "", "v", "", "u", "", "t", "", "", "v", "", "v", "", "v",
        "", "d", "", "", "v", "", "d", "", "t", "", "", "", "", "v", "", "", "", "v", "", "u", "",
        "", "v", "", "", "", "d", "", "t", "", "", "v", "", "", "", "", "", "d", "", "", "t", "",
        "", "", "", "", "", "", "", "v", "", "", "", "", "", "u", "", "", "v", "", "", "", "u", "",
        "t", "", "", "v", "", "", "", "v", "", "d", "", "", "v", "", "u", "", "t", "", "", "", "",
        "v", "", "v", "", "v", "", "u", "", "", "v", "", "v", "", "d", "", "t", "", "", "v", "",
        "v", "", "", "", "d", "", "", "d", "", "t", "", "", "", "", "", "", "", "", "v", "", "", "",
        "u", "", "", "", "", "v", "", "u", "", "t", "", "", "", "", "v", "", "v", "", "d", "", "",
        "", "", "d", "", "t", "", "", "", "", "", "", "", "", "v", "", "u", "", "", "", "", "", "",
        "d", "", "t", "", "", "", "", "", "", "", "", "d", "", };

    /**
     * George's tree in the test database.
     */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    private static final String[] ARNOLD_TREE_CELLS = { INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "<a href=\"person?db=null&amp;id=I7\" class=\"name\">"
            + "Arnold <span class=\"surname\">Robinson</span>"
            + " (1917-1969) [I7]</a></td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
        INVISIBLE_STRING, INVISIBLE_STRING,

        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>", };

    /**
     * George's tree in the test database.
     */
    private static final String[] GEORGE_TREE_CELLS = {
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">" + "Living</td></tr></table>", };

    /**
     * Melissa's tree in the test database.
     */
    private static final String[] MELISSA_TREE_CELLS = {
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">" + "Living</td></tr></table>", };

    /** */
    private transient RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testAttributeListOpenRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof PersonAttributeListOpenRenderer,
            "Mismatched renderer type");
    }

    @Test
    void testListItemRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue(renderer.getListItemRenderer() instanceof NullListItemRenderer,
            "Mismatched renderer type");
    }

    @Test
    void testNameHtmlRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue(renderer.getNameHtmlRenderer() instanceof PersonNameHtmlRenderer,
            "Mismatched renderer type");
    }

    @Test
    void testNameIndexRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue(renderer.getNameIndexRenderer() instanceof PersonNameIndexRenderer,
            "Mismatched renderer type");
    }

    @Test
    void testPhraseRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue(renderer.getPhraseRenderer() instanceof NullPhraseRenderer,
            "Mismatched renderer type");
    }

    /**
     * @return the renderer
     */
    private PersonRenderer createRenderer() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1");
        return new PersonRenderer(person, new GedRendererFactory(), anonymousContext);
    }

    @Test
    void testRenderMelissaTreeAnonymous() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final CellRow[] cellRows = personRenderer.getTreeRows(5);
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                assertCellMatch(MELISSA_TREE_CELLS, i, cellRenderer.getCellClass(),
                    cellRenderer.getNameHtml());
                i++;
            }
        }
    }

    /**
     * Perform a check for a particular cell.
     *
     * @param treeCells the person's cells
     * @param i         the current index
     * @param cc        the current cell class string
     * @param nameHtml  the current name string
     */
    private void assertCellMatch(final String[] treeCells, final int i, final String cc,
        final String nameHtml) {
        assertEquals(TREE_CELL_CLASSES[i], cc, "Cell class mismatch");
        assertEquals(treeCells[i], nameHtml, "cell " + i + " mismatch");
    }

    @Test
    void testRenderGeorgeTree() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person george = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(george, new GedRendererFactory(),
            anonymousContext);
        final CellRow[] cellRows = personRenderer.getTreeRows(5);
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                assertCellMatch(GEORGE_TREE_CELLS, i, cellRenderer.getCellClass(),
                    cellRenderer.getNameHtml());
                i++;
            }
        }
    }

    @Test
    void testRenderArnoldTree() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person arnold = (Person) root.find("I7");
        final PersonRenderer personRenderer = new PersonRenderer(arnold, new GedRendererFactory(),
            anonymousContext);
        final CellRow[] cellRows = personRenderer.getTreeRows(5);
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                assertCellMatch(ARNOLD_TREE_CELLS, i, cellRenderer.getCellClass(),
                    cellRenderer.getNameHtml());
                i++;
            }
        }
    }

    @Test
    void testRenderNullFather() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.getParents().renderFather(builder, 2, null);
        final String actual = builder.toString();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @Test
    void testRenderFather() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderFather(builder, 2, navigator.getFather());
        @SuppressWarnings("java:S6126")
        final String expected = "\n" + START_PARENT
            + "   <span class=\"parent label\">Father:</span> \n" + END_PARAG;
        final String actual = builder.toString();
        assertEquals(expected, actual, "Mismatched rendered string");
    }

    @Test
    void testRenderNullMother() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.getParents().renderMother(builder, 2, null);
        final String actual = builder.toString();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @Test
    void testRenderMelissaFather() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderFather(builder, 2, navigator.getFather());
        final String actual = builder.toString();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @Test
    void testRenderMother() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderMother(builder, 2, navigator.getMother());
        @SuppressWarnings("java:S6126")
        final String expected = "\n" + START_PARENT
            + "   <span class=\"parent label\">Mother:</span> \n" + END_PARAG;
        final String actual = builder.toString();
        assertEquals(expected, actual, "Mismatched rendered string");
    }

    @Test
    void testRenderMotherConfidentialAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person sabino = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(sabino, new GedRendererFactory(),
            anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(sabino);
        personRenderer.getParents().renderMother(builder, 2, navigator.getMother());
        @SuppressWarnings("java:S6126")
        final String expected = "\n" + START_PARENT
            + "   <span class=\"parent label\">Mother:</span> \n" + END_PARAG;
        final String actual = builder.toString();
        assertEquals(expected, actual, "Mismatched rendered string");
    }

    @Test
    void testRenderMelissaMother() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderMother(builder, 2, navigator.getMother());
        final String actual = builder.toString();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @Test
    void testRenderMelissaTitleAnonymous() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getTitleName();
        assertEquals("Confidential", actual, "Mismatched rendered string");
    }

    @Test
    void testRenderGeorgeTitle() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getTitleName();
        assertEquals("Living", actual, "Mismatched rendered string");
    }

    @Test
    void testRenderMelissaWholeName() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getWholeName();
        assertEquals("Living", actual, "Mismatched rendered string");
    }

    @ParameterizedTest
    @MethodSource("wholeNameCases")
    void testRenderWholeName(final String personId, final String expected) throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find(personId);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
                anonymousContext);
        final String actual = personRenderer.getWholeName();
        assertEquals(expected, actual, "Mismatched rendered string for " + personId);
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> wholeNameCases() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of("I4248", "Confidential"),
            org.junit.jupiter.params.provider.Arguments.of("I9", "Living"));
    }
    @Test
    void testRenderMelissaFatherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getParents().getFatherNameHtml();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @ParameterizedTest
    @MethodSource("fatherNameHtmlCases")
    void testRenderFatherNameHtml(final String personId) throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find(personId);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
                anonymousContext);
        final String actual = personRenderer.getParents().getFatherNameHtml();
        assertTrue(actual.isEmpty(), "Expected empty string for " + personId);
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> fatherNameHtmlCases() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of("I5266"),
            org.junit.jupiter.params.provider.Arguments.of("I5"),
            org.junit.jupiter.params.provider.Arguments.of("I9"));
    }

    @Test
    void testRenderMelissaMotherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getParents().getMotherNameHtml();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @ParameterizedTest
    @MethodSource("motherNameHtmlCases")
    void testRenderMotherNameHtml(final String personId) throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find(personId);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
                anonymousContext);
        final String actual = personRenderer.getParents().getMotherNameHtml();
        assertTrue(actual.isEmpty(), "Expected empty string for " + personId);
    }

    private static Stream<Arguments> motherNameHtmlCases() {
        return Stream.of(
            Arguments.of("I5266"),
            Arguments.of("I5"),
            Arguments.of("I9"));
    }

    @Test
    void testRenderMelissaFatherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getParents().getFatherRendition();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @Test
    void testRenderGeorgeFatherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        @SuppressWarnings("java:S6126")
        final String expected = "\n<p class=\"parent\">\n <span class=\"par"
            + "ent label\">Father:</span> \n</p>";
        final String actual = personRenderer.getParents().getFatherRendition();
        assertEquals(expected, actual, "Mismatched rendered string");
    }

    @Test
    void testRenderMelissaMotherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getParents().getMotherRendition();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @Test
    void testRenderGeorgeMotherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person george = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(george, new GedRendererFactory(),
            anonymousContext);
        @SuppressWarnings("java:S6126")
        final String expected = "\n<p class=\"parent\">\n" + " <span class=\"parent label\">Mother:"
            + "</span> \n</p>";
        final String actual = personRenderer.getParents().getMotherRendition();
        assertEquals(expected, actual, "Mismatched rendered string");
    }

    @Test
    void testRenderDickLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person dick = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(dick, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getLifeSpanString();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @Test
    void testRenderArnoldLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person arnold = (Person) root.find("I7");
        final PersonRenderer personRenderer = new PersonRenderer(arnold, new GedRendererFactory(),
            anonymousContext);
        final String expected = "(12 AUG 1917-02 OCT 1969)";
        final String actual = personRenderer.getLifeSpanString();
        assertEquals(expected, actual, "Mismatched rendered string");
    }

    @Test
    void testRenderMelissaLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getLifeSpanString();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @Test
    void testRenderDickFamilies() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final List<FamilyRenderer> families = personRenderer.getFamilies();
        assertTrue(families.isEmpty(), "Expected empty families list");
    }

    @Test
    void testRenderMelissaFamilies() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final List<FamilyRenderer> families = personRenderer.getFamilies();
        assertTrue(families.isEmpty(), "Expected empty families list");
    }

    @Test
    void testRenderDickAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final List<GedRenderer<GedObject>> attributes = personRenderer.getAttributes();
        assertTrue(attributes.isEmpty(), "Expected empty attributes list");
    }

    @ParameterizedTest
    @MethodSource("emptyAttributesCases")
    void testRenderEmptyAttributes(final String personId) throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find(personId);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
                anonymousContext);
        final List<GedRenderer<GedObject>> attributes = personRenderer.getAttributes();
        assertTrue(attributes.isEmpty(), "Expected empty attributes list for " + personId);
    }

    private static Stream<Arguments> emptyAttributesCases() {
        return Stream.of(
            Arguments.of("I4"),
            Arguments.of("I1"),
            Arguments.of("I5"));
    }

    @Test
    void testRenderArnoldAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person arnold = (Person) root.find("I7");
        final PersonRenderer personRenderer = new PersonRenderer(arnold, new GedRendererFactory(),
            anonymousContext);
        final int expect = 8;
        final List<GedRenderer<GedObject>> attributes = personRenderer.getAttributes();
        assertEquals(expect, attributes.size(), "Expected 8 attributes");
    }

    @Test
    void testRenderDickIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getIdString();
        assertEquals("I2", actual, "Mismatched ID string");
    }

    @Test
    void testRenderMelissaIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getIdString();
        assertEquals("I1", actual, "Mismatched ID string");
    }

    @Test
    void testRenderMelissaIndexHref() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            anonymousContext);
        final String expected = "surnames?db=null&letter=?#?";
        final String actual = personRenderer.getIndexHref();
        assertEquals(expected, actual, "Mismatched rendered string");
    }

    @Test
    void testVivianSurnameLetterAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getSurnameLetter();
        assertEquals("?", actual, "Mismatched rendered string");
    }

    @Test
    void testVivianSurnameAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getSurname();
        assertEquals("?", actual, "Mismatched rendered string");
    }

    @Test
    void testVivianLifespanAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            anonymousContext);
        final String actual = personRenderer.getLifeSpanString();
        assertTrue(actual.isEmpty(), "Expected empty string");
    }

    @Test
    void testVivianFamiliesAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            anonymousContext);
        final List<FamilyRenderer> families = personRenderer.getFamilies();
        assertTrue(families.isEmpty(), "Expected empty families list");
    }

    // TODO test renderAsSection and renderAsListItem
    // These are are currently null rendered, but shouldn't be.

}
