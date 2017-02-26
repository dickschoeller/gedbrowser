package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.navigator.PersonNavigator;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.FamilyRenderer;
import org.schoellerfamily.gedbrowser.renderer.FamilySectionRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.User;

/**
 * @author Dick Schoeller
 */
public final class FamilyRendererTest {
    /**
     * Boiler plate for a horizontal line before a family.
     */
    private static final String HR_CLASS_FAMILY =
            "  <hr class=\"family\"/>\n";

    /**
     * Boiler plate for the header for Family 1.
     */
    private static final String FAMILY_ONE_HEADER =
            "  <h3 class=\"family\">Family 1</h3>\n";

    /**
     * Boiler plate for the start of a spouse paragraph.
     */
    private static final String START_PARAG = "  <p class=\"spouse\">\n";

    /**
     * Boiler plat for the end of a paragraph.
     */
    private static final String END_PARAG = "  </p>\n";

    /** */
    private transient CalendarProvider provider;

    /** */
    private transient ApplicationInfo appInfo;

    /** */
    private transient RenderingContext anonymousContext;

    /** */
    private transient RenderingContext userContext;

    /** */
    @Before
    public void init() {
        appInfo = new ApplicationInfoStub();
        provider = new CalendarProviderStub();
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
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testSectionRenderer() {
        final FamilyRenderer renderer = new FamilyRenderer(new Family(),
                new GedRendererFactory(), anonymousContext, provider);
        assertTrue("Wrong renderer type",
                renderer.getSectionRenderer()
                instanceof FamilySectionRenderer);
    }

    /**
     * @throws IOException because reader can.
     */
    @Test
    public void testRenderDickSpouses() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person dick = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(dick,
                new GedRendererFactory(), userContext,
                provider);
        final PersonNavigator navigator = new PersonNavigator(dick);
        final Family family = navigator.getFamilies().get(0);
        final FamilyRenderer familyRenderer = createFamilyRenderer(family,
                userContext);
        final StringBuilder builder = new StringBuilder();
        familyRenderer.renderSpouses(builder, personRenderer, 1);
        assertEquals("Rendered html doesn't match expectation",
                "\n"
                + HR_CLASS_FAMILY
                + FAMILY_ONE_HEADER
                + START_PARAG
                + "    <span class=\"spouse label\">Spouse:</span> "
                + "<a href=\"person?db=null&amp;id=I3\" "
                + "class=\"name\">Lisa Hope "
                + "<span class=\"surname\">Robinson</span>"
                + " (1960-) [I3]</a>\n"
                + END_PARAG, builder.toString());
    }

    /**
     * @throws IOException because reader can.
     */
    @Test
    public void testRenderDickSpousesAnonymous() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person dick = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(dick,
                new GedRendererFactory(), anonymousContext,
                provider);
        final PersonNavigator navigator = new PersonNavigator(dick);
        final Family family = navigator.getFamilies().get(0);
        final FamilyRenderer familyRenderer = createFamilyRenderer(family,
                anonymousContext);
        final StringBuilder builder = new StringBuilder();
        familyRenderer.renderSpouses(builder, personRenderer, 1);
        assertEquals("Rendered html doesn't match expectation",
                "\n"
                + HR_CLASS_FAMILY
                + FAMILY_ONE_HEADER
                + START_PARAG
                + "    <span class=\"spouse label\">Spouse:</span> Living\n"
                + END_PARAG, builder.toString());
    }

    /**
     * @throws IOException because reader can.
     */
    @Test
    public void testSpouseConstructUsedInPersonTemplate()
            throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
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
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
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
     * @throws IOException because reader can.
     */
    @Test
    public void testRenderVivianSpouses() throws IOException {
        final User user = new User();
        user.setUsername("dick");
        final RenderingContext adminContext =
                new RenderingContext(user, true, true, appInfo);
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
        final Person vivian = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(vivian,
                new GedRendererFactory(), adminContext,
                provider);
        final String[] outstrings = {
                "\n"
                        + HR_CLASS_FAMILY
                        + FAMILY_ONE_HEADER
                        + START_PARAG
                        + "    <span class=\"spouse label\">Spouse:</span> "
                        + "<a href=\"person?db=null&amp;id=I4248\" "
                        + "class=\"name\">Sabino "
                        + "<span class=\"surname\">Figliuolo</span> "
                        + "[I4248]</a>\n"
                        + END_PARAG,
                ""
        };
        int index = 0;
        final StringBuilder builder = new StringBuilder();
        final PersonNavigator navigator = new PersonNavigator(vivian);
        for (final Family family : navigator.getFamilies()) {
            builder.setLength(0);
            final FamilyRenderer familyRenderer = createFamilyRenderer(family,
                    adminContext);
            familyRenderer.renderSpouses(builder, personRenderer, 1);
            assertEquals("Rendered html doesn't match expectation",
                    outstrings[index++], builder.toString());
        }
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

    /** */
    @Test
    public void testMinimalFamilySpouses() {
        final Root root = new Root();
        final Family fam = new Family(root, new ObjectId("F1"));
        root.insert(fam);
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        final FamS fams = new FamS(person, "FAMS", new ObjectId("F1"));
        person.insert(fams);
        final Husband husband = new Husband(fam, "HUSB", new ObjectId("I1"));
        fam.insert(husband);

        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext,
                provider);
        final FamilyRenderer familyRenderer = createFamilyRenderer(fam,
                userContext);
        final StringBuilder builder = new StringBuilder();
        familyRenderer.renderSpouses(builder, personRenderer, 1);
        assertEquals("Rendered html doesn't match expectation",
                "\n"
                + HR_CLASS_FAMILY
                + FAMILY_ONE_HEADER
                + START_PARAG
                + "    <span class=\"spouse label\">Spouse:</span> \n"
                + END_PARAG, builder.toString());
    }

    /** */
    @Test
    public void testMinimalFamilySpousesAnonymous() {
        final Root root = new Root();
        final Family fam = new Family(root, new ObjectId("F1"));
        root.insert(fam);
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        final FamS fams = new FamS(person, "FAMS", new ObjectId("F1"));
        person.insert(fams);
        final Husband husband = new Husband(fam, "HUSB", new ObjectId("I1"));
        fam.insert(husband);

        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext,
                provider);
        final FamilyRenderer familyRenderer = createFamilyRenderer(fam,
                anonymousContext);
        final StringBuilder builder = new StringBuilder();
        familyRenderer.renderSpouses(builder, personRenderer, 1);
        final String expected = "\n"
                + HR_CLASS_FAMILY
                + FAMILY_ONE_HEADER
                + START_PARAG
                + "    <span class=\"spouse label\">Spouse:</span> \n"
                + END_PARAG;
        final String actual = builder.toString();
        assertEquals("Rendered html doesn't match expectation",
                expected, actual);
    }

    /** */
    @Test
    public void testMinimalFamilySpouses2() {
        final Root root = new Root();
        final Family fam = new Family(root, new ObjectId("F1"));
        root.insert(fam);
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        final FamS fams = new FamS(person, "FAMS", new ObjectId("F1"));
        person.insert(fams);
        final Husband husband = new Husband(fam, "HUSB", new ObjectId("I1"));
        fam.insert(husband);

        final FamilyRenderer familyRenderer = createFamilyRenderer(fam,
                userContext);
        final StringBuilder builder = new StringBuilder();
        familyRenderer.renderSpouses(builder, familyRenderer, 1);
        assertEquals("Rendered html doesn't match expectation",
                "\n"
                + HR_CLASS_FAMILY
                + FAMILY_ONE_HEADER
                + START_PARAG
                + "    <span class=\"spouse label\">Spouse:</span> "
                + "<a href=\"person?db=null&amp;id=I1\" class=\"name\"> "
                + "<span class=\"surname\">?</span> [I1]</a>\n"
                + END_PARAG, builder.toString());
    }

    /** */
    @Test
    public void testMinimalFamilySpouses2Anonymous() {
        final Root root = new Root();
        final Family fam = new Family(root, new ObjectId("F1"));
        root.insert(fam);
        final Person person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        final FamS fams = new FamS(person, "FAMS", new ObjectId("F1"));
        person.insert(fams);
        final Husband husband = new Husband(fam, "HUSB", new ObjectId("I1"));
        fam.insert(husband);

        final FamilyRenderer familyRenderer = createFamilyRenderer(fam,
                anonymousContext);
        final StringBuilder builder = new StringBuilder();
        familyRenderer.renderSpouses(builder, familyRenderer, 1);
        assertEquals("Rendered html doesn't match expectation",
                "\n"
                + HR_CLASS_FAMILY
                + FAMILY_ONE_HEADER
                + START_PARAG
                + "    <span class=\"spouse label\">Spouse:</span> Living\n"
                + END_PARAG, builder.toString());
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderF1Attributes() throws IOException {
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
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
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
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
        final GedObject root = TestDataReader.getInstance().readBigTestSource();
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
