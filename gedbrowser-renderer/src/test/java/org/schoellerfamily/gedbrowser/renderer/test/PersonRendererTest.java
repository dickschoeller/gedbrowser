package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    public void testRenderSabinoTitleAdmin() throws IOException {
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
    public void testRenderMelissaTitleUser() throws IOException {
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
    public void testRenderGeorgeTitle() throws IOException {
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
    public void testRenderMelissaWholeName() throws IOException {
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
    @Test
    public void testRenderSabinoWholeName() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            adminContext);
        assertEquals("Sabino Figliuolo", personRenderer.getWholeName(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderSabinoWholeNameUser() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I4248");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("Confidential", personRenderer.getWholeName(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeWholeNameUser() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("George Steven Sacerdote", personRenderer.getWholeName(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFatherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals(
            "<a href=\"person?db=null&amp;id=I2\" class=\"name\">" + "Richard John"
                + " <span class=\"surname\">Schoeller</span>" + " (1958-) [I2]</a>",
            personRenderer.getParents().getFatherNameHtml(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciFatherNameHtmlAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            adminContext);
        assertEquals(
            "<a href=\"person?db=null&amp;id=I4248\" class=\"name\">" + "Sabino"
                + " <span class=\"surname\">Figliuolo</span> [I4248]</a>",
            personRenderer.getParents().getFatherNameHtml(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciFatherNameHtmlAnon() throws IOException {
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
    @Test
    public void testRenderVivianFatherNameHtmlAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            adminContext);
        assertEquals(
            "<a href=\"person?db=null&amp;id=I4\" class=\"name\">" + "John Vincent"
                + " <span class=\"surname\">Schoeller</span>" + " (1934-) [I4]</a>",
            personRenderer.getParents().getFatherNameHtml(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianFatherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("", personRenderer.getParents().getFatherNameHtml(), "Expected empty string");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeFatherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("", personRenderer.getParents().getFatherNameHtml(), "Expected empty string");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaMotherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I1");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals(
            "<a href=\"person?db=null&amp;id=I3\" class=\"name\">" + "Lisa Hope"
                + " <span class=\"surname\">Robinson</span>" + " (1960-) [I3]</a>",
            personRenderer.getParents().getMotherNameHtml(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciMotherNameHtmlAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5266");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            adminContext);
        assertEquals(
            "<a href=\"person?db=null&amp;id=I5\" class=\"name\">" + "Vivian Grace"
                + " <span class=\"surname\">Schoeller</span>" + " (1960-) [I5]</a>",
            personRenderer.getParents().getMotherNameHtml(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderCiciMotherNameHtmlAnon() throws IOException {
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
    public void testRenderVivianMotherNameHtmlAdmin() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            adminContext);
        assertEquals(
            "<a href=\"person?db=null&amp;id=I6\" class=\"name\">" + "Patricia Ruth"
                + " <span class=\"surname\">Hayes</span>" + " (1937-) [I6]</a>",
            personRenderer.getParents().getMotherNameHtml(),
            "Rendered html doesn't match expectation");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderVivianMotherNameHtmlAnon() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I5");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("", personRenderer.getParents().getMotherNameHtml(), "Expected empty string");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderGeorgeMotherNameHtml() throws IOException {
        final Root root = reader.readBigTestSource();
        final Person melissa = (Person) root.find("I9");
        final PersonRenderer personRenderer = new PersonRenderer(melissa, new GedRendererFactory(),
            userContext);
        assertEquals("", personRenderer.getParents().getMotherNameHtml(), "Expected empty string");
    }

    /**
     * @throws IOException when there is a read error.
     */
    @Test
    public void testRenderMelissaFatherRendition() throws IOException {
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
    public void testRenderGeorgeFatherRendition() throws IOException {
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
    public void testRenderMelissaMotherRendition() throws IOException {
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
    public void testRenderGeorgeMotherRendition() throws IOException {
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
    public void testRenderDickLifeSpan() throws IOException {
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
    public void testRenderMelissaLifeSpan() throws IOException {
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
    public void testRenderDickFamilies() throws IOException {
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
    public void testRenderMelissaFamilies() throws IOException {
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
    public void testRenderDickAttributes() throws IOException {
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
    public void testRenderMelissaAttributes() throws IOException {
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
    public void testRenderDickIdString() throws IOException {
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
    public void testRenderMelissaIdString() throws IOException {
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
    public void testRenderMelissaIndexHref() throws IOException {
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
    public void testVivianSurnameLetterAnon() throws IOException {
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
    public void testVivianSurnameLetterAdmin() throws IOException {
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
    public void testVivianSurnameAnon() throws IOException {
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
    public void testVivianSurnameAdmin() throws IOException {
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
    public void testVivianLifespanAnon() throws IOException {
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
    public void testVivianLifespanAdmin() throws IOException {
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
    public void testVivianFamiliesAnon() throws IOException {
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
    public void testVivianFamiliesAdmin() throws IOException {
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
    public void testVivianAttributesAnon() throws IOException {
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
    public void testVivianAttributesAdmin() throws IOException {
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
    public void testHeaderMenuItem() throws IOException {
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
    public void testSaveMenuItem() throws IOException {
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
    public void testSaveFilename() throws IOException {
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
    public void testIndexMenuItem() throws IOException {
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
    public void testLivingMenuItem() throws IOException {
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
    public void testSourcesMenuItem() throws IOException {
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
    public void testSubmittersMenuItem() throws IOException {
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
    public void testPlacesMenuItem() throws IOException {
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
