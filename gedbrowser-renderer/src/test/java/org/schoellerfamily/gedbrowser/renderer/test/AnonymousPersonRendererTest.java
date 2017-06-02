package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.ExcessiveClassLength" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class AnonymousPersonRendererTest {
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
    private static final String[] TREE_CELL_CLASSES = {
            "",  "",  "",  "",  "",  "",  "",  "u", "",
            "",  "",  "",  "",  "",  "u", "",  "t", "",
            "",  "",  "",  "",  "",  "v", "",  "d", "",
            "",  "",  "",  "u", "",  "t", "",  "",  "",
            "",  "",  "",  "v", "",  "v", "",  "u", "",
            "",  "",  "",  "v", "",  "d", "",  "t", "",
            "",  "",  "",  "v", "",  "",  "",  "d", "",
            "",  "u", "",  "t", "",  "",  "",  "",  "",
            "",  "v", "",  "v", "",  "",  "",  "u", "",
            "",  "v", "",  "v", "",  "u", "",  "t", "",
            "",  "v", "",  "v", "",  "v", "",  "d", "",
            "",  "v", "",  "d", "",  "t", "",  "",  "",
            "",  "v", "",  "",  "",  "v", "",  "u", "",
            "",  "v", "",  "",  "",  "d", "",  "t", "",
            "",  "v", "",  "",  "",  "",  "",  "d", "",
            "",  "t", "",  "",  "",  "",  "",  "",  "",
            "",  "v", "",  "",  "",  "",  "",  "u", "",
            "",  "v", "",  "",  "",  "u", "",  "t", "",
            "",  "v", "",  "",  "",  "v", "",  "d", "",
            "",  "v", "",  "u", "",  "t", "",  "",  "",
            "",  "v", "",  "v", "",  "v", "",  "u", "",
            "",  "v", "",  "v", "",  "d", "",  "t", "",
            "",  "v", "",  "v", "",  "",  "",  "d", "",
            "",  "d", "",  "t", "",  "",  "",  "",  "",
            "",  "",  "",  "v", "",  "",  "",  "u", "",
            "",  "",  "",  "v", "",  "u", "",  "t", "",
            "",  "",  "",  "v", "",  "v", "",  "d", "",
            "",  "",  "",  "d", "",  "t", "",  "",  "",
            "",  "",  "",  "",  "",  "v", "",  "u", "",
            "",  "",  "",  "",  "",  "d", "",  "t", "",
            "",  "",  "",  "",  "",  "",  "",  "d", "",
    };

    /**
     * George's tree in the test database.
     */
    private static final String[] ARNOLD_TREE_CELLS = {
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "<a href=\"person?db=null&amp;id=I7\" class=\"name\">"
            + "Arnold <span class=\"surname\">Robinson</span>"
            + " (1917-1969) [I7]</a></td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
            INVISIBLE_STRING, INVISIBLE_STRING,

            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING, INVISIBLE_STRING,
            INVISIBLE_STRING, INVISIBLE_STRING,
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "&nbsp;&nbsp;&nbsp;</td></tr></table>",
    };

    /**
     * George's tree in the test database.
     */
    private static final String[] GEORGE_TREE_CELLS = {
        "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
        + "Living</td></tr></table>",
    };

    /**
     * Melissa's tree in the test database.
     */
    private static final String[] MELISSA_TREE_CELLS = {
            "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
            + "Living</td></tr></table>",
    };

    /** */
    private transient RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue("Mismatched renderer type",
                renderer.getAttributeListOpenRenderer()
                instanceof PersonAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue("Mismatched renderer type",
                renderer.getListItemRenderer()
                instanceof NullListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue("Mismatched renderer type",
                renderer.getNameHtmlRenderer()
                instanceof PersonNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue("Mismatched renderer type",
                renderer.getNameIndexRenderer()
                instanceof PersonNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue("Mismatched renderer type",
                renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * @return the renderer
     */
    private PersonRenderer createRenderer() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson("I1");
        return new PersonRenderer(person, new GedRendererFactory(),
                anonymousContext);
    }

    /**
     * @throws IOException because the reader can.
     */
    @Test
    public void testRenderMelissaTreeAnonymous() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final CellRow[] cellRows = personRenderer.getTreeRows(5);
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                assertCellMatch(MELISSA_TREE_CELLS, i,
                        cellRenderer.getCellClass(),
                        cellRenderer.getNameHtml());
                i++;
            }
        }
    }

    /**
     * Perform a check for a particular cell.
     *
     * @param treeCells the person's cells
     * @param i the current index
     * @param cc the current cell class string
     * @param nameHtml the current name string
     */
    private void assertCellMatch(final String[] treeCells, final int i,
            final String cc, final String nameHtml) {
        assertEquals("Cell class mismatch", TREE_CELL_CLASSES[i], cc);
        assertEquals("cell " + i + " mismatch", treeCells[i], nameHtml);
    }

    /**
     * @throws IOException because the reader can.
     */
    @Test
    public void testRenderGeorgeTree() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person george = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(george,
                new GedRendererFactory(), anonymousContext);
        final CellRow[] cellRows = personRenderer.getTreeRows();
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                assertCellMatch(GEORGE_TREE_CELLS, i,
                        cellRenderer.getCellClass(),
                        cellRenderer.getNameHtml());
                i++;
            }
        }
    }

    /**
     * @throws IOException because the reader can.
     */
    @Test
    public void testRenderArnoldTree() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person arnold = (Person) root.find("I7");
        final PersonRenderer personRenderer = new PersonRenderer(arnold,
                new GedRendererFactory(), anonymousContext);
        final CellRow[] cellRows = personRenderer.getTreeRows();
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                assertCellMatch(ARNOLD_TREE_CELLS, i,
                        cellRenderer.getCellClass(),
                        cellRenderer.getNameHtml());
                i++;
            }
        }
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderNullFather() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.getParents().renderFather(builder, 2, null);
        final String actual = builder.toString();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderFather() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderFather(
                builder, 2, navigator.getFather());
        final String expected = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Father:</span> \n"
                + END_PARAG;
        final String actual = builder.toString();
        assertEquals("Mismatched rendered string", expected, actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderNullMother() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.getParents().renderMother(builder, 2, null);
        final String actual = builder.toString();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFather() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderFather(
                builder, 2, navigator.getFather());
        final String actual = builder.toString();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMother() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderMother(
                builder, 2, navigator.getMother());
        final String expected = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Mother:</span> \n"
                + END_PARAG;
        final String actual = builder.toString();
        assertEquals("Mismatched rendered string", expected, actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMotherConfidentialAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person sabino = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(sabino,
                new GedRendererFactory(), anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(sabino);
        personRenderer.getParents().renderMother(
                builder, 2, navigator.getMother());
        final String expected = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Mother:</span> \n"
                + END_PARAG;
        final String actual = builder.toString();
        assertEquals("Mismatched rendered string", expected, actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMother() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderMother(
                builder, 2, navigator.getMother());
        final String actual = builder.toString();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaTitleAnonymous() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getTitleName();
        assertEquals("Mismatched rendered string", "Confidential", actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeTitle() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getTitleName();
        assertEquals("Mismatched rendered string", "Living", actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaWholeName() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getWholeName();
        assertEquals("Mismatched rendered string", "Living", actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderSabinoWholeNameAnonymous() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getWholeName();
        assertEquals("Mismatched rendered string", "Confidential", actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeWholeName() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getWholeName();
        assertEquals("Mismatched rendered string", "Living", actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFatherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getFatherNameHtml();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciFatherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getFatherNameHtml();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianFatherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getFatherNameHtml();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeFatherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getFatherNameHtml();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMotherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getMotherNameHtml();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciMotherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getMotherNameHtml();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianMotherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getMotherNameHtml();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeMotherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getMotherNameHtml();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFatherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getFatherRendition();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeFatherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String expected = "\n<p class=\"parent\">\n <span class=\"par"
                + "ent label\">Father:</span> \n</p>";
        final String actual = personRenderer.getParents()
                .getFatherRendition();
        assertEquals("Mismatched rendered string",
                expected,
                actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMotherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getParents()
                .getMotherRendition();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeMotherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person george = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(george,
                new GedRendererFactory(), anonymousContext);
        final String expected = "\n<p class=\"parent\">\n"
                + " <span class=\"parent label\">Mother:"
                + "</span> \n</p>";
        final String actual = personRenderer.getParents()
                .getMotherRendition();
        assertEquals("Mismatched rendered string", expected, actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person dick = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(dick,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getLifeSpanString();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderArnoldLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person arnold = (Person) root.find("I7");
        final PersonRenderer personRenderer = new PersonRenderer(arnold,
                new GedRendererFactory(), anonymousContext);
        final String expected = "(12 AUG 1917-02 OCT 1969)";
        final String actual = personRenderer.getLifeSpanString();
        assertEquals("Mismatched rendered string", expected, actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getLifeSpanString();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickFamilies() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final List<FamilyRenderer> families = personRenderer.getFamilies();
        assertTrue("Expected empty families list", families.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFamilies() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final List<FamilyRenderer> families = personRenderer.getFamilies();
        assertTrue("Expected empty families list", families.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final List<GedRenderer<?>> attributes = personRenderer.getAttributes();
        assertTrue("Expected empty attributes list", attributes.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderJohnSchoellerAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final List<GedRenderer<?>> attributes = personRenderer.getAttributes();
        assertTrue("Expected empty attributes list", attributes.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderArnoldAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person arnold = (Person) root.find("I7");
        final PersonRenderer personRenderer = new PersonRenderer(arnold,
                new GedRendererFactory(), anonymousContext);
        final int expect = 8;
        final List<GedRenderer<?>> attributes = personRenderer.getAttributes();
        assertEquals("Expected 8 attributes", expect, attributes.size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final List<GedRenderer<?>> attributes = personRenderer.getAttributes();
        assertTrue("Expected empty attributes list", attributes.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getIdString();
        assertEquals("Mismatched ID string", "I2", actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getIdString();
        assertEquals("Mismatched ID string", "I1", actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaIndexHref() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), anonymousContext);
        final String expected = "surnames?db=null&letter=?#?";
        final String actual = personRenderer.getIndexHref();
        assertEquals("Mismatched rendered string", expected, actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameLetterAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getSurnameLetter();
        assertEquals("Mismatched rendered string", "?", actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getSurname();
        assertEquals("Mismatched rendered string", "?", actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianLifespanAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final String actual = personRenderer.getLifeSpanString();
        assertTrue("Expected empty string", actual.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianFamiliesAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final List<FamilyRenderer> families = personRenderer.getFamilies();
        assertTrue("Expected empty families list", families.isEmpty());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianAttributesAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final List<GedRenderer<?>> attributes = personRenderer.getAttributes();
        assertTrue("Expected empty attributes list", attributes.isEmpty());
    }

    // TODO test renderAsSection and renderAsListItem
    // These are are currently null rendered, but shouldn't be.

}
