package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.CellRenderer;
import org.schoellerfamily.gedbrowser.renderer.CellRow;
import org.schoellerfamily.gedbrowser.renderer.FamilyRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullSectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.User;

/**
 * @author Dick Schoeller
 */
public final class PersonRendererTest {
    /** */
    private transient RenderingContext adminContext;

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
    private static final String[] GEORGE_TREE_CELLS = {
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
            + "<a href=\"person?db=null&amp;id=I9\" class=\"name\">"
            + "George Steven <span class=\"surname\">Sacerdote</span> [I9]"
            + "</a></td></tr></table>",
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
     * Melissa's tree in the test database.
     */
    private static final String[] MELISSA_TREE_CELLS = {
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
            + "<a href=\"person?db=null&amp;id=I4\" class=\"name\">"
            + "John Vincent <span class=\"surname\">Schoeller</span>"
            + " (1934-) [I4]"
            + "</a></td></tr></table>",
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
            + "<a href=\"person?db=null&amp;id=I2\" class=\"name\">"
            + "Richard John <span class=\"surname\">Schoeller</span>"
            + " (1958-) [I2]"
            + "</a></td></tr></table>",
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
            + "<a href=\"person?db=null&amp;id=I6\" class=\"name\">"
            + "Patricia Ruth <span class=\"surname\">Hayes</span>"
            + " (1937-) [I6]"
            + "</a></td></tr></table>",
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
            + "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
            + "Melissa Robinson <span class=\"surname\">Schoeller</span> [I1]"
            + "</a></td></tr></table>",
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
            + "<a href=\"person?db=null&amp;id=I7\" class=\"name\">"
            + "Arnold <span class=\"surname\">Robinson</span>"
            + " (1917-1969) [I7]"
            + "</a></td></tr></table>",
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
            + "<a href=\"person?db=null&amp;id=I3\" class=\"name\">"
            + "Lisa Hope <span class=\"surname\">Robinson</span>"
            + " (1960-) [I3]"
            + "</a></td></tr></table>",
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
            + "<a href=\"person?db=null&amp;id=I10\" class=\"name\">"
            + "Estelle <span class=\"surname\">Liberman</span>"
            + " (1925-) [I10]"
            + "</a></td></tr></table>",
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

    /** */
    private transient RenderingContext userContext;

    /** */
    @Before
    public void init() {
        userContext = RenderingContext.user();
        final User user = new User();
        user.setUsername("dick");
        adminContext = new RenderingContext(user, true, true);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final PersonRenderer renderer = new PersonRenderer(new Person(null),
                new GedRendererFactory(), userContext);
        assertTrue(renderer.getAttributeListOpenRenderer()
                instanceof PersonAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final PersonRenderer renderer = new PersonRenderer(new Person(null),
                new GedRendererFactory(), userContext);
        assertTrue(renderer.getListItemRenderer()
                instanceof NullListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final PersonRenderer renderer = new PersonRenderer(new Person(null),
                new GedRendererFactory(), userContext);
        assertTrue(renderer.getNameHtmlRenderer()
                instanceof PersonNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final PersonRenderer renderer = new PersonRenderer(new Person(null),
                new GedRendererFactory(), userContext);
        assertTrue(renderer.getNameIndexRenderer()
                instanceof PersonNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final PersonRenderer renderer = new PersonRenderer(new Person(null),
                new GedRendererFactory(), userContext);
        assertTrue(renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testSectionRenderer() {
        final PersonRenderer renderer = new PersonRenderer(new Person(null),
                new GedRendererFactory(), userContext);
        assertTrue(renderer.getSectionRenderer()
                instanceof NullSectionRenderer);
    }

    /**
     * @throws IOException because the reader can.
     */
    @Test
    public void testRenderMelissaTree() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person george = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(george,
                new GedRendererFactory(), userContext);
        final CellRow[] cellRows = personRenderer.getTreeRows(5);
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                final String cc = cellRenderer.getCellClass();
                final String nameHtml = cellRenderer.getNameHtml();
                assertEquals(TREE_CELL_CLASSES[i], cc);
                assertEquals(MELISSA_TREE_CELLS[i], nameHtml);
                i++;
            }
        }
    }

    /**
     * @throws IOException because the reader can.
     */
    @Test
    public void testRenderGeorgeTree() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person george = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(george,
                new GedRendererFactory(), userContext);
        final CellRow[] cellRows = personRenderer.getTreeRows();
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                final String cc = cellRenderer.getCellClass();
                final String nameHtml = cellRenderer.getNameHtml();
                assertEquals(TREE_CELL_CLASSES[i], cc);
                assertEquals(GEORGE_TREE_CELLS[i], nameHtml);
                i++;
            }
        }
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderNullFather() throws IOException {
        final GedObject root =
                TestDataReader.getInstance().readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.renderFather(builder, 2, null);
        assertEquals("", builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderFather() throws IOException {
        final GedObject root =
                TestDataReader.getInstance().readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.renderFather(builder, 2, melissa.getFather());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Father:</span> \n"
                + END_PARAG;
        assertEquals(ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderNullMother() throws IOException {
        final GedObject root =
                TestDataReader.getInstance().readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.renderMother(builder, 2, null);
        assertEquals("", builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFather() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.renderFather(builder, 2, melissa.getFather());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Father:</span> "
                + "<a href=\"person?db=null&amp;id=I2\" "
                + "class=\"name\">Richard John "
                + "<span class=\"surname\">Schoeller</span>"
                + " (1958-) [I2]</a>\n"
                + END_PARAG;
        assertEquals(ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMother() throws IOException {
        final GedObject root =
                TestDataReader.getInstance().readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.renderMother(builder, 2, melissa.getMother());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Mother:</span> \n"
                + END_PARAG;
        assertEquals(ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMotherConfidentialAdmin() throws IOException {
        final GedObject root =
                TestDataReader.getInstance().readBigTestSource();
        final Person sabino = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(sabino,
                new GedRendererFactory(), adminContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.renderMother(builder, 2, sabino.getMother());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Mother:</span> \n"
                + END_PARAG;
        assertEquals(ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMotherConfidentialUser() throws IOException {
        final GedObject root =
                TestDataReader.getInstance().readBigTestSource();
        final Person sabino = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(sabino,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.renderMother(builder, 2, sabino.getMother());
        final String expected = "\n"
                + "  <p class=\"parent\">\n"
                + "   <span class=\"parent label\">Mother:</span> \n"
                + "  </p>";
        final String actual = builder.toString();
        assertEquals(expected, actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMotherUser() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.renderMother(builder, 2, melissa.getMother());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Mother:</span> "
                + "<a href=\"person?db=null&amp;id=I3\" "
                + "class=\"name\">Lisa Hope "
                + "<span class=\"surname\">Robinson</span>"
                + " (1960-) [I3]</a>\n"
                + END_PARAG;
        assertEquals(ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderSabinoTitleAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), adminContext);
        assertEquals(
                "Sabino <span class=\"surname\">Figliuolo</span>",
                personRenderer.getTitleName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaTitleUser() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "Confidential",
                personRenderer.getTitleName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeTitle() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "George Steven <span class=\"surname\">Sacerdote</span>",
                personRenderer.getTitleName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaWholeName() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Melissa Robinson Schoeller",
                personRenderer.getWholeName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderSabinoWholeName() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), adminContext);
        assertEquals("Sabino Figliuolo", personRenderer.getWholeName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderSabinoWholeNameUser() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "Confidential",
                personRenderer.getWholeName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeWholeNameUser() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("George Steven Sacerdote", personRenderer.getWholeName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFatherNameHtml() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "<a href=\"person?db=null&amp;id=I2\" class=\"name\">"
                + "Richard John"
                + " <span class=\"surname\">Schoeller</span>"
                + " (1958-) [I2]</a>",
                personRenderer.getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciFatherNameHtmlAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(),
                adminContext);
        assertEquals(
                "<a href=\"person?db=null&amp;id=I4248\" class=\"name\">"
                + "Sabino"
                + " <span class=\"surname\">Figliuolo</span> [I4248]</a>",
                personRenderer.getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciFatherNameHtmlAnon() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Confidential", personRenderer.getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianFatherNameHtmlAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(),
                adminContext);
        assertEquals(
                "<a href=\"person?db=null&amp;id=I4\" class=\"name\">"
                + "John Vincent"
                + " <span class=\"surname\">Schoeller</span>"
                + " (1934-) [I4]</a>",
                personRenderer.getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianFatherNameHtmlAnon() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("", personRenderer.getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeFatherNameHtml() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("", personRenderer.getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMotherNameHtml() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "<a href=\"person?db=null&amp;id=I3\" class=\"name\">"
                + "Lisa Hope"
                + " <span class=\"surname\">Robinson</span>"
                + " (1960-) [I3]</a>",
                personRenderer.getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciMotherNameHtmlAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(),
                adminContext);
        assertEquals(
                "<a href=\"person?db=null&amp;id=I5\" class=\"name\">"
                + "Vivian Grace"
                + " <span class=\"surname\">Schoeller</span>"
                + " (1960-) [I5]</a>",
                personRenderer.getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciMotherNameHtmlAnon() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Confidential", personRenderer.getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianMotherNameHtmlAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(),
                adminContext);
        assertEquals(
                "<a href=\"person?db=null&amp;id=I6\" class=\"name\">"
                + "Patricia Ruth"
                + " <span class=\"surname\">Hayes</span>"
                + " (1937-) [I6]</a>",
                personRenderer.getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianMotherNameHtmlAnon() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("", personRenderer.getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeMotherNameHtml() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("", personRenderer.getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFatherRendition() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "\n<p class=\"parent\">\n <span class=\"parent label\">Father:"
                + "</span> <a href=\"person?db=null&amp;id=I2\" class=\"name\">"
                + "Richard John <span class=\"surname\">Schoeller</span>"
                + " (1958-) [I2]</a>\n</p>",
                personRenderer.getFatherRendition());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeFatherRendition() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "\n<p class=\"parent\">\n <span class=\"parent label\">Father:"
                + "</span> \n</p>",
                personRenderer.getFatherRendition());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMotherRendition() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "\n<p class=\"parent\">\n <span class=\"parent label\">Mother:"
                + "</span> <a href=\"person?db=null&amp;id=I3\" class=\"name\">"
                + "Lisa Hope <span class=\"surname\">Robinson</span>"
                + " (1960-) [I3]</a>\n</p>",
                personRenderer.getMotherRendition());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeMotherRendition() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "\n<p class=\"parent\">\n <span class=\"parent label\">Mother:"
                + "</span> \n</p>",
                personRenderer.getMotherRendition());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickLifeSpan() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "(14 DEC 1958-)",
                personRenderer.getLifeSpanString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaLifeSpan() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(
                "(-)",
                personRenderer.getLifeSpanString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickFamilies() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final List<FamilyRenderer> families = personRenderer.getFamilies();
        assertEquals("F1", families.get(0).getString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFamilies() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals(0, personRenderer.getFamilies().size());
    }


    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickAttributes() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final int expect = 8;
        assertEquals(expect, personRenderer.getAttributes().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaAttributes() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final int expect = 4;
        assertEquals(expect, personRenderer.getAttributes().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickIdString() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("I2", personRenderer.getIdString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaIdString() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("I1", personRenderer.getIdString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaIndexHref() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("surnames?db=null&letter=S#Schoeller",
                personRenderer.getIndexHref());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameLetterAnon() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals("?", personRenderer.getSurnameLetter());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameLetterAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        assertEquals("S", personRenderer.getSurnameLetter());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameAnon() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals("?", personRenderer.getSurname());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        assertEquals("Schoeller", personRenderer.getSurname());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianLifespanAnon() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals("", personRenderer.getLifeSpanString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianLifespanAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        assertEquals("(16 APR 1960-)", personRenderer.getLifeSpanString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianFamiliesAnon() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals(0, personRenderer.getFamilies().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianFamiliesAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        assertEquals(1, personRenderer.getFamilies().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianAttributesAnon() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals(0, personRenderer.getAttributes().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianAttributesAdmin() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        final int expectedLength = 8;
        assertEquals(expectedLength, personRenderer.getAttributes().size());
    }
    // TODO test tree rendering for Vivian both Admin and Anon

    // TODO test renderAsSection and renderAsListItem
    // These are are currently null rendered, but shouldn't be.
}
