package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.FamilyRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class FamilyRendererTest {
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
    private transient RenderingContext anonymousContext;

    /** */
    private transient RenderingContext userContext;

    /** */
    @Before
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
        userContext = RenderingContext.user(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final FamilyRenderer renderer = new FamilyRenderer(new Family(),
                new GedRendererFactory(), anonymousContext, provider);
        assertTrue("Wrong renderer type",
                renderer.getAttributeListOpenRenderer()
                instanceof SimpleAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final FamilyRenderer renderer = new FamilyRenderer(new Family(),
                new GedRendererFactory(), anonymousContext, provider);
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
        final FamilyRenderer renderer = new FamilyRenderer(new Family(),
                new GedRendererFactory(), anonymousContext, provider);
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof NullNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final FamilyRenderer renderer = new FamilyRenderer(new Family(),
                new GedRendererFactory(), anonymousContext, provider);
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof NullNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final FamilyRenderer renderer = new FamilyRenderer(new Family(),
                new GedRendererFactory(), anonymousContext, provider);
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * @throws IOException because reader can.
     */
    @Test
    public void testSpouseConstructUsedInPersonTemplate()
            throws IOException {
        final Root root = reader.readBigTestSource();
        final Person dick = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(dick,
                new GedRendererFactory(), userContext,
                provider);
        final PersonNavigator navigator = new PersonNavigator(dick);
        final Family family = navigator.getFamilies().get(0);
        final FamilyRenderer familyRenderer = createFamilyRenderer(family,
                userContext);
        assertEquals("Rendered html doesn't match expectation",
                "<a href=\"person?db=null&amp;id=I3\" class=\"name\">"
                + "Lisa Hope <span class=\"surname\">Robinson</span>"
                + " (1960-) [I3]</a>",
                familyRenderer.getSpouse(personRenderer).getNameHtml());
    }

    /**
     * @throws IOException because reader can.
     */
    @Test
    public void testSpouseConstructUsedInPersonTemplateAnonymous()
            throws IOException {
        final Root root = reader.readBigTestSource();
        final Person dick = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(dick,
                new GedRendererFactory(), anonymousContext,
                provider);
        final PersonNavigator navigator = new PersonNavigator(dick);
        final Family family = navigator.getFamilies().get(0);
        final FamilyRenderer familyRenderer = createFamilyRenderer(family,
                anonymousContext);
        assertEquals("Rendered text doesn't match expdectation",
                "Living",
                familyRenderer.getSpouse(personRenderer).getNameHtml());
    }

    /**
     * Create a renderer for the provided family, with a non-default rendering
     * context.
     *
     * @param family the family
     * @param context the renderingContext
     * @return the renderer
     */
    private FamilyRenderer createFamilyRenderer(final Family family,
            final RenderingContext context) {
        return new FamilyRenderer(family, new GedRendererFactory(), context,
                provider);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderF1Attributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Family fam = (Family) root.find("F1");
        final FamilyRenderer familyRenderer = new FamilyRenderer(fam,
                new GedRendererFactory(),
                anonymousContext,
                provider);
        final List<GedRenderer<?>> attributes = familyRenderer.getAttributes();
        final String expected = "<span class=\"label\">Marriage:</span>"
                + " 27 MAY 1984, Temple Emanu-el, Providence, Providence"
                + " County, Rhode Island, USA, The ceremony performed by"
                + " Rabbi Wayne Franklin and Cantor Ivan<br/>\n"
                + "Perlman.  The best man and matron of honor were Dale"
                + " Matcovitch<br/>\n"
                + "and Carol Robinson Sacerdote.,"
                + "  [<a href=\"source?db=null&amp;id=S4\">S4</a>]";
        assertEquals("Rendered html doesn't match expectation",
                expected, attributes.get(0).getListItemContents());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderF1Children() throws IOException {
        final Root root = reader.readBigTestSource();
        final Family fam = (Family) root.find("F1");
        final FamilyRenderer familyRenderer = new FamilyRenderer(fam,
                new GedRendererFactory(), userContext,
                provider);
        final List<PersonRenderer> children = familyRenderer.getChildren();
        final String expected = "<a href=\"person?db=null&amp;id=I1\""
                + " class=\"name\">Melissa Robinson <span class=\"surname\">"
                + "Schoeller</span> [I1]</a>";
        assertEquals("Rendered html doesn't match expectation",
                expected, children.get(0).getNameHtml());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderF1ChildrenAnonymous() throws IOException {
        final Root root = reader.readBigTestSource();
        final Family fam = (Family) root.find("F1");
        final FamilyRenderer familyRenderer = new FamilyRenderer(fam,
                new GedRendererFactory(), anonymousContext,
                provider);
        final List<PersonRenderer> children = familyRenderer.getChildren();
        final String expected = "Living";
        assertEquals("Rendered html doesn't match expectation",
                expected, children.get(0).getNameHtml());
    }

    // TODO could include some tests with empty spouses?
    // the code checks this.
}
