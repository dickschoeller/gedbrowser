package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.CellRenderer;
import org.schoellerfamily.gedbrowser.renderer.CellRow;
import org.schoellerfamily.gedbrowser.renderer.FamilyRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.user.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.ExcessiveClassLength",
        "PMD.ExcessiveImports" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class PersonRendererTest {
    /** */
    @Autowired
    private transient TestDataReader reader;
    /** */
    @Autowired
    private transient CalendarProvider provider;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

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
        userContext = RenderingContext.user(appInfo);
        final UserImpl user = new UserImpl();
        user.setUsername("dick");
        user.addRole("USER");
        user.addRole("ADMIN");
        adminContext = new RenderingContext(user, appInfo, provider);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final PersonRenderer renderer = createRenderer();
        assertTrue("Wrong renderer type",
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
        assertTrue("Wrong renderer type",
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
        assertTrue("Wrong renderer type",
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
        assertTrue("Wrong renderer type",
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
        assertTrue("Wrong renderer type",
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
                userContext);
    }

    /**
     * @throws IOException because the reader can.
     */
    @Test
    public void testRenderMelissaTree() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person george = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(george,
                new GedRendererFactory(), userContext);
        final CellRow[] cellRows = personRenderer.getTreeRows(5);
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                assertCellMatch(TREE_CELL_CLASSES[i], MELISSA_TREE_CELLS[i],
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
    public void testRenderGeorgeTree() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person george = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(george,
                new GedRendererFactory(), userContext);
        final CellRow[] cellRows = personRenderer.getTreeRows();
        int i = 0;
        for (final CellRow cellRow : cellRows) {
            final CellRenderer[] cellRenderers = cellRow.getCells();
            for (final CellRenderer cellRenderer : cellRenderers) {
                assertCellMatch(TREE_CELL_CLASSES[i], GEORGE_TREE_CELLS[i],
                        cellRenderer.getCellClass(),
                        cellRenderer.getNameHtml());
                i++;
            }
        }
    }

    /**
     * @param expectedCellClass expected class
     * @param expectedCellContent expected content
     * @param cellClass actual class
     * @param cellContent actual content
     */
    private void assertCellMatch(final String expectedCellClass,
            final String expectedCellContent,
            final String cellClass, final String cellContent) {
        assertEquals("Class doesn't match", expectedCellClass, cellClass);
        assertEquals("Content doesn't match", expectedCellContent, cellContent);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderNullFather() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.getParents().renderFather(builder, 2, null);
        assertEquals("Expected empty string", "", builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderFather() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderFather(builder, 2,
                navigator.getFather());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Father:</span> \n"
                + END_PARAG;
        assertEquals("Rendered html doesn't match expectation",
                ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderNullMother() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        personRenderer.getParents().renderMother(builder, 2, null);
        assertEquals("Expected empty string", "", builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFather() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderFather(builder, 2,
                navigator.getFather());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Father:</span> "
                + "<a href=\"person?db=null&amp;id=I2\" "
                + "class=\"name\">Richard John "
                + "<span class=\"surname\">Schoeller</span>"
                + " (1958-) [I2]</a>\n"
                + END_PARAG;
        assertEquals("Rendered html doesn't match expectation",
                ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMother() throws IOException {
        final Root root = reader.readSmallTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderMother(builder, 2,
                navigator.getMother());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Mother:</span> \n"
                + END_PARAG;
        assertEquals("Rendered html doesn't match expectation",
                ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMotherConfidentialAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person sabino = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(sabino,
                new GedRendererFactory(), adminContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(sabino);
        personRenderer.getParents().renderMother(builder, 2,
                navigator.getMother());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Mother:</span> \n"
                + END_PARAG;
        assertEquals("Rendered html doesn't match expectation",
                ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMotherConfidentialUser() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person sabino = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(sabino,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(sabino);
        personRenderer.getParents().renderMother(builder, 2,
                navigator.getMother());
        final String expected = "\n"
                + "  <p class=\"parent\">\n"
                + "   <span class=\"parent label\">Mother:</span> \n"
                + "  </p>";
        final String actual = builder.toString();
        assertEquals("Rendered html doesn't match expectation",
                expected, actual);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMotherUser() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(melissa);
        personRenderer.getParents().renderMother(builder, 2,
                navigator.getMother());
        final String ts1 = "\n"
                + START_PARENT
                + "   <span class=\"parent label\">Mother:</span> "
                + "<a href=\"person?db=null&amp;id=I3\" "
                + "class=\"name\">Lisa Hope "
                + "<span class=\"surname\">Robinson</span>"
                + " (1960-) [I3]</a>\n"
                + END_PARAG;
        assertEquals("Rendered html doesn't match expectation",
                ts1, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderSabinoTitleAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), adminContext);
        assertEquals("Rendered html doesn't match expectation",
                "Sabino <span class=\"surname\">Figliuolo</span>",
                personRenderer.getTitleName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaTitleUser() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "Confidential",
                personRenderer.getTitleName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeTitle() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "George Steven <span class=\"surname\">Sacerdote</span>",
                personRenderer.getTitleName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaWholeName() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "Melissa Robinson Schoeller",
                personRenderer.getWholeName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderSabinoWholeName() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), adminContext);
        assertEquals("Rendered html doesn't match expectation",
                "Sabino Figliuolo", personRenderer.getWholeName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderSabinoWholeNameUser() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "Confidential", personRenderer.getWholeName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeWholeNameUser() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "George Steven Sacerdote", personRenderer.getWholeName());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFatherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "<a href=\"person?db=null&amp;id=I2\" class=\"name\">"
                + "Richard John"
                + " <span class=\"surname\">Schoeller</span>"
                + " (1958-) [I2]</a>",
                personRenderer.getParents().getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciFatherNameHtmlAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(),
                adminContext);
        assertEquals("Rendered html doesn't match expectation",
                "<a href=\"person?db=null&amp;id=I4248\" class=\"name\">"
                + "Sabino"
                + " <span class=\"surname\">Figliuolo</span> [I4248]</a>",
                personRenderer.getParents().getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciFatherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "Confidential",
                personRenderer.getParents().getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianFatherNameHtmlAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(),
                adminContext);
        assertEquals("Rendered html doesn't match expectation",
                "<a href=\"person?db=null&amp;id=I4\" class=\"name\">"
                + "John Vincent"
                + " <span class=\"surname\">Schoeller</span>"
                + " (1934-) [I4]</a>",
                personRenderer.getParents().getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianFatherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Expected empty string",
                "", personRenderer.getParents().getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeFatherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Expected empty string",
                "", personRenderer.getParents().getFatherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMotherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "<a href=\"person?db=null&amp;id=I3\" class=\"name\">"
                + "Lisa Hope"
                + " <span class=\"surname\">Robinson</span>"
                + " (1960-) [I3]</a>",
                personRenderer.getParents().getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciMotherNameHtmlAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(),
                adminContext);
        assertEquals("Rendered html doesn't match expectation",
                "<a href=\"person?db=null&amp;id=I5\" class=\"name\">"
                + "Vivian Grace"
                + " <span class=\"surname\">Schoeller</span>"
                + " (1960-) [I5]</a>",
                personRenderer.getParents().getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciMotherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "Confidential",
                personRenderer.getParents().getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianMotherNameHtmlAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(),
                adminContext);
        assertEquals("Rendered html doesn't match expectation",
                "<a href=\"person?db=null&amp;id=I6\" class=\"name\">"
                + "Patricia Ruth"
                + " <span class=\"surname\">Hayes</span>"
                + " (1937-) [I6]</a>",
                personRenderer.getParents().getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianMotherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Expected empty string",
                "", personRenderer.getParents().getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeMotherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Expected empty string",
                "", personRenderer.getParents().getMotherNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFatherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "\n<p class=\"parent\">\n <span class=\"parent label\">Father:"
                + "</span> <a href=\"person?db=null&amp;id=I2\" class=\"name\">"
                + "Richard John <span class=\"surname\">Schoeller</span>"
                + " (1958-) [I2]</a>\n</p>",
                personRenderer.getParents().getFatherRendition());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeFatherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "\n<p class=\"parent\">\n <span class=\"parent label\">Father:"
                + "</span> \n</p>",
                personRenderer.getParents().getFatherRendition());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMotherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "\n<p class=\"parent\">\n <span class=\"parent label\">Mother:"
                + "</span> <a href=\"person?db=null&amp;id=I3\" class=\"name\">"
                + "Lisa Hope <span class=\"surname\">Robinson</span>"
                + " (1960-) [I3]</a>\n</p>",
                personRenderer.getParents().getMotherRendition());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeMotherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "\n<p class=\"parent\">\n <span class=\"parent label\">Mother:"
                + "</span> \n</p>",
                personRenderer.getParents().getMotherRendition());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "(14 DEC 1958-)",
                personRenderer.getLifeSpanString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered html doesn't match expectation",
                "(-)",
                personRenderer.getLifeSpanString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickFamilies() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final List<FamilyRenderer> families = personRenderer.getFamilies();
        assertEquals("Expected family string F1",
                "F1", families.get(0).getString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFamilies() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Person should have 0 families",
                0, personRenderer.getFamilies().size());
    }


    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final int expect = 8;
        assertEquals("Expected 8 attributes",
                expect, personRenderer.getAttributes().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        final int expect = 4;
        assertEquals("Expected 4 attributes",
                expect, personRenderer.getAttributes().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderDickIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Expected person ID string I2",
                "I2", personRenderer.getIdString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Expected person ID string I1",
                "I1", personRenderer.getIdString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaIndexHref() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered string mismatch",
                "surnames?db=null&letter=S#Schoeller",
                personRenderer.getIndexHref());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameLetterAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered string mismatch",
                "?", personRenderer.getSurnameLetter());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameLetterAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        assertEquals("Rendered string mismatch",
                "S", personRenderer.getSurnameLetter());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals("Rendered string mismatch",
                "?", personRenderer.getSurname());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianSurnameAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        assertEquals("Rendered string mismatch",
                "Schoeller", personRenderer.getSurname());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianLifespanAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals("Expected empty string",
                "", personRenderer.getLifeSpanString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianLifespanAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        assertEquals("Rendered html doesn't match expectation",
                "(16 APR 1960-)", personRenderer.getLifeSpanString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianFamiliesAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals("Expected empty family list",
                0, personRenderer.getFamilies().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianFamiliesAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        assertEquals("Expected 1 family in family list",
                1, personRenderer.getFamilies().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianAttributesAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        assertEquals("Expected 0 attributes",
                0, personRenderer.getAttributes().size());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testVivianAttributesAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(),
                adminContext);
        final int expectedLength = 8;
        assertEquals("Attribute list size mismatch",
                expectedLength, personRenderer.getAttributes().size());
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeaderMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("head href mismatch",
                    "head?db=gl120368", renderer.getHeaderHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            final String expected = "surnames?db=gl120368&letter=";
            final int expectedLength = expected.length();
            assertEquals("index href mismatch",
                    expected,
                    renderer.getIndexHref().substring(0, expectedLength));
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("living href mismatch",
                    "living?db=gl120368", renderer.getLivingHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSourcesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("sources href mismatch",
                    "sources?db=gl120368", renderer.getSourcesHref());
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSubmittorsMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("submittors href mismatch",
                    "submittors?db=gl120368", renderer.getSubmittorsHref());
        }
    }

    /**
     * @param person the person
     * @return the renderer
     */
    private PersonRenderer createRenderer(final Person person) {
        return new PersonRenderer(person,
                new GedRendererFactory(), userContext);
    }
    // TODO test tree rendering for Vivian both Admin and Anon

    // TODO test renderAsSection and renderAsListItem
    // These are are currently null rendered, but shouldn't be.
}
