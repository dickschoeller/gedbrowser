package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.FamilyRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.ExcessiveClassLength",
    "PMD.ExcessiveImports" })
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class PersonRendererTest {
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
    /** */
    private transient RenderingContext userContext;

    @BeforeEach
    void setUp() {
        // userContext: non-admin user
        userContext = RenderingContext.user(appInfo);

        // adminContext: grant ADMIN and USER roles and use the injected provider
        final UserImpl adminUser = new UserImpl();
        adminUser.setUsername("dick");
        adminUser.clearRoles();
        adminUser.addRole("USER");
        adminUser.addRole("ADMIN");
        adminContext = new RenderingContext(adminUser, appInfo, provider);
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderSabinoTitleAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            adminContext);
        assertEquals("Sabino <span class=\"surname\">Figliuolo</span>",
            personRenderer.getTitleName(), "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderMelissaTitleUser() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("Confidential", personRenderer.getTitleName(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderGeorgeTitle() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("George Steven <span class=\"surname\">Sacerdote</span>",
            personRenderer.getTitleName(), "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderMelissaWholeName() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("Melissa Robinson Schoeller", personRenderer.getWholeName(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @ParameterizedTest
    @MethodSource("wholeNameCases")
    void testRenderWholeName(final String personId, final boolean isAdmin,
            final String expected) throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find(personId);
        final RenderingContext context = isAdmin ? adminContext : userContext;
        final PersonRenderer personRenderer = new PersonRenderer(person,
            new GedRendererFactory(), context);
        assertEquals(expected, personRenderer.getWholeName(),
            "Rendered html doesn't match expectation");
    }

    /** */
    private static Stream<Arguments> wholeNameCases() {
        return Stream.of(
                Arguments.of("I4248", true, "Sabino Figliuolo"),
                Arguments.of("I4248", false, "Confidential"),
                Arguments.of("I9", false, "George Steven Sacerdote"));
    }

    /**
     * @throws IOException when there is a read error.
     */
    @ParameterizedTest
    @MethodSource("fatherNameHtmlCases")
    void testRenderFatherNameHtml(final String personId, final boolean isAdmin,
            final String expected, final String message) throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find(personId);
        final RenderingContext context = isAdmin ? adminContext : userContext;
        final PersonRenderer personRenderer = new PersonRenderer(person,
            new GedRendererFactory(), context);
        assertEquals(expected, personRenderer.getParents().getFatherNameHtml(),
            message);
    }

    /** */
    private static Stream<Arguments> fatherNameHtmlCases() {
        return Stream.of(
                Arguments.of("I1", false,
                        "<a href=\"person?db=null&amp;id=I2\" class=\"name\">"
                                + "Richard John"
                                + " <span class=\"surname\">Schoeller</span>"
                                + " (1958-) [I2]</a>",
                        "Rendered html doesn't match expectation"),
                Arguments.of("I5266", true,
                        "<a href=\"person?db=null&amp;id=I4248\" class=\"name\">"
                                + "Sabino"
                                + " <span class=\"surname\">Figliuolo</span>"
                                + " [I4248]</a>",
                        "Rendered html doesn't match expectation"),
                Arguments.of("I5", true,
                        "<a href=\"person?db=null&amp;id=I4\" class=\"name\">"
                                + "John Vincent"
                                + " <span class=\"surname\">Schoeller</span>"
                                + " (1934-) [I4]</a>",
                        "Rendered html doesn't match expectation"),
                Arguments.of("I5", false, "", "Expected empty string"),
                Arguments.of("I9", false, "", "Expected empty string"));
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderCiciFatherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("Confidential", personRenderer.getParents().getFatherNameHtml(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @ParameterizedTest
    @MethodSource("motherNameHtmlCases")
    void testRenderMotherNameHtml(final String personId, final boolean isAdmin,
            final String expected, final String message) throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find(personId);
        final RenderingContext context = isAdmin ? adminContext : userContext;
        final PersonRenderer personRenderer = new PersonRenderer(person,
            new GedRendererFactory(), context);
        assertEquals(expected, personRenderer.getParents().getMotherNameHtml(),
            message);
    }

    /** */
    private static Stream<Arguments> motherNameHtmlCases() {
        return Stream.of(
                Arguments.of("I1", false,
                        "<a href=\"person?db=null&amp;id=I3\" class=\"name\">"
                                + "Lisa Hope"
                                + " <span class=\"surname\">Robinson</span>"
                                + " (1960-) [I3]</a>",
                        "Rendered html doesn't match expectation"),
                Arguments.of("I5266", true,
                        "<a href=\"person?db=null&amp;id=I5\" class=\"name\">"
                                + "Vivian Grace"
                                + " <span class=\"surname\">Schoeller</span>"
                                + " (1960-) [I5]</a>",
                        "Rendered html doesn't match expectation"),
                Arguments.of("I5", true,
                        "<a href=\"person?db=null&amp;id=I6\" class=\"name\">"
                                + "Patricia Ruth"
                                + " <span class=\"surname\">Hayes</span>"
                                + " (1937-) [I6]</a>",
                        "Rendered html doesn't match expectation"),
                Arguments.of("I5", false, "", "Expected empty string"),
                Arguments.of("I9", false, "", "Expected empty string"));
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderCiciMotherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("Confidential", personRenderer.getParents().getMotherNameHtml(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderMelissaFatherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("\n<p class=\"parent\">\n <span class=\"parent label\">Father:"
            + "</span> <a href=\"person?db=null&amp;id=I2\" class=\"name\">"
            + "Richard John <span class=\"surname\">Schoeller</span>" + " (1958-) [I2]</a>\n</p>",
            personRenderer.getParents().getFatherRendition(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderGeorgeFatherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals(
            "\n<p class=\"parent\">\n <span class=\"parent label\">Father:" + "</span> \n</p>",
            personRenderer.getParents().getFatherRendition(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderMelissaMotherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals(
            "\n<p class=\"parent\">\n <span class=\"parent label\">Mother:"
                + "</span> <a href=\"person?db=null&amp;id=I3\" class=\"name\">"
                + "Lisa Hope <span class=\"surname\">Robinson</span>" + " (1960-) [I3]</a>\n</p>",
            personRenderer.getParents().getMotherRendition(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderGeorgeMotherRendition() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals(
            "\n<p class=\"parent\">\n <span class=\"parent label\">Mother:" + "</span> \n</p>",
            personRenderer.getParents().getMotherRendition(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderDickLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("(14 DEC 1958-)", personRenderer.getLifeSpanString(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderMelissaLifeSpan() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("(-)", personRenderer.getLifeSpanString(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderDickFamilies() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        final List<FamilyRenderer> families = personRenderer.getFamilies();
        assertEquals("F1", families.get(0).getString(), "Expected family string F1");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderMelissaFamilies() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals(0, personRenderer.getFamilies().size(), "Person should have 0 families");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderDickAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        final int expect = 8;
        assertEquals(expect, personRenderer.getAttributes().size(), "Expected 8 attributes");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderMelissaAttributes() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        final int expect = 7;
        assertEquals(expect, personRenderer.getAttributes().size(), "Expected 7 attributes");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderDickIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I2");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("I2", personRenderer.getIdString(), "Expected person ID string I2");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderMelissaIdString() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("I1", personRenderer.getIdString(), "Expected person ID string I1");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testRenderMelissaIndexHref() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("surnames?db=null&letter=S#Schoeller", personRenderer.getIndexHref(),
            "Rendered string mismatch");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianSurnameLetterAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            userContext);
        assertEquals("?", personRenderer.getSurnameLetter(), "Rendered string mismatch");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianSurnameLetterAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            adminContext);
        assertEquals("S", personRenderer.getSurnameLetter(), "Rendered string mismatch");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianSurnameAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            userContext);
        assertEquals("?", personRenderer.getSurname(), "Rendered string mismatch");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianSurnameAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            adminContext);
        assertEquals("Schoeller", personRenderer.getSurname(), "Rendered string mismatch");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianLifespanAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            userContext);
        assertEquals("", personRenderer.getLifeSpanString(), "Expected empty string");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianLifespanAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            adminContext);
        assertEquals("(16 APR 1960-)", personRenderer.getLifeSpanString(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianFamiliesAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            userContext);
        assertEquals(0, personRenderer.getFamilies().size(), "Expected empty family list");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianFamiliesAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            adminContext);
        assertEquals(1, personRenderer.getFamilies().size(), "Expected 1 family in family list");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianAttributesAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            userContext);
        assertEquals(0, personRenderer.getAttributes().size(), "Expected 0 attributes");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    void testVivianAttributesAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person person = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            adminContext);
        final int expectedLength = 8;
        assertEquals(expectedLength, personRenderer.getAttributes().size(),
            "Attribute list size mismatch");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testHeaderMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("head?db=gl120368", renderer.getHeaderHref(), "head href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testSaveMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("save?db=gl120368", renderer.getSaveHref(), "save href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testSaveFilename() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("gl120368.ged", renderer.getSaveFilename(), "save href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            final String expected = "surnames?db=gl120368&letter=";
            final int expectedLength = expected.length();
            assertEquals(expected, renderer.getIndexHref().substring(0, expectedLength),
                "index href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("living?db=gl120368", renderer.getLivingHref(), "living href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testSourcesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("sources?db=gl120368", renderer.getSourcesHref(), "sources href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testSubmittersMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("submitters?db=gl120368", renderer.getSubmittersHref(),
                "submitters href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    void testPlacesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Person> persons = root.find(Person.class);
        for (final Person person : persons) {
            final PersonRenderer renderer = createRenderer(person);
            assertEquals("places?db=gl120368", renderer.getPlacesHref(), "places href mismatch");
        }
    }

    /**
     * @param person the person
     * @return the renderer
     */
    private PersonRenderer createRenderer(final Person person) {
        return new PersonRenderer(person, new GedRendererFactory(), userContext);
    }
    // TODO test tree rendering for Vivian both Admin and Anon

    // TODO test renderAsSection and renderAsListItem
    // These are are currently null rendered, but shouldn't be.
}
