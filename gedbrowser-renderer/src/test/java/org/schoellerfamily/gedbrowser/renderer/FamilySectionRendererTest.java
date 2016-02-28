package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public class FamilySectionRendererTest {
    /** */
    private transient RenderingContext anonymousContext;
    /** */
    private transient RenderingContext userContext;
    /** */
    private transient RenderingContext adminContext;

    /** */
    @Before
    public final void init() {
        final Root root = new Root(null, "root");
        final Family family = new Family(root, new ObjectId("F1"));
        root.insert(family);
        anonymousContext = RenderingContext.anonymous();
        userContext = RenderingContext.user();
        final User user = new User();
        user.setUsername("dick");
        adminContext = new RenderingContext(user, true, true);
    }

    /**
     * @throws IOException because reader can.
     */
    @Test
    public final void testRenderDickAsSection() throws IOException {
        final GedObject ged = TestDataReader.getInstance().readBigTestSource();
        final Person dick = (Person) ged.find("I2");
        final PersonRenderer pRenderer = new PersonRenderer(dick,
                new GedRendererFactory(), userContext);
        final Family family1 =
                dick.getFamilies(new ArrayList<Family>()).get(0);
        final FamilyRenderer familyRenderer = new FamilyRenderer(family1,
                new GedRendererFactory(),
                userContext);
        final FamilySectionRenderer fsr = (FamilySectionRenderer) familyRenderer
                .getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        fsr.renderAsSection(builder, pRenderer, false, 0, 1);
        assertEquals("\n"
                + "<div class=\"family\">\n"
                + "  <hr class=\"family\"/>\n"
                + "  <h3 class=\"family\">Family 1</h3>\n"
                + "  <p class=\"spouse\">\n"
                + "    <span class=\"spouse label\">Spouse:</span> "
                + "<a href=\"person?db=null&amp;id=I3\" "
                + "class=\"name\">Lisa Hope "
                + "<span class=\"surname\">Robinson</span> (I3)</a>\n"
                + "  </p>\n"
                + "\n"
                + "  <ul>\n"
                + "<li><span class=\"label\">Marriage:</span> "
                + "27 MAY 1984, Temple Emanu-el, Providence, Providence "
                + "County, Rhode Island, USA, The ceremony performed by "
                + "Rabbi Wayne Franklin and Cantor Ivan\n"
                + "Perlman.  The best man and matron of honor were Dale "
                + "Matcovitch\n"
                + "and Carol Robinson Sacerdote.,  "
                + "[<a href=\"source?db=null&amp;id=S4\">S4</a>]</li>\n"
                + "\n"
                + "  </ul>\n"
                + "\n"
                + "  <span class=\"children label\">Children:</span>\n"
                + "  <ol class=\"children\">\n"
                + "    <li><a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                + "Melissa Robinson <span class=\"surname\">Schoeller</span> "
                + "(I1)</a></li>\n"
                + "  </ol>\n"
                + "</div>\n", builder.toString());
    }

    /**
     * @throws IOException because reader can.
     */
    @Test
    public final void testRenderDickAsSectionAnonymous() throws IOException {
        final GedObject ged = TestDataReader.getInstance().readBigTestSource();
        final Person dick = (Person) ged.find("I2");
        final PersonRenderer pRenderer = new PersonRenderer(dick,
                new GedRendererFactory(), anonymousContext);
        final Family family1 =
                dick.getFamilies(new ArrayList<Family>()).get(0);
        final FamilyRenderer familyRenderer = new FamilyRenderer(family1,
                new GedRendererFactory(),
                anonymousContext);
        final FamilySectionRenderer fsr = (FamilySectionRenderer) familyRenderer
                .getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        fsr.renderAsSection(builder, pRenderer, false, 0, 1);

        final String expected = "\n"
                + "<div class=\"family\">\n"
                + "  <hr class=\"family\"/>\n"
                + "  <h3 class=\"family\">Family 1</h3>\n"
                + "  <p class=\"spouse\">\n"
                + "    <span class=\"spouse label\">Spouse:</span> Living\n"
                + "  </p>\n"
                + "\n"
                + "  <ul>\n"
                + "<li><span class=\"label\">Marriage:</span> 27 MAY 1984,"
                + " Temple Emanu-el, Providence, Providence County, Rhode"
                + " Island, USA, The ceremony performed by Rabbi Wayne"
                + " Franklin and Cantor Ivan\n"
                + "Perlman.  The best man and matron of honor were Dale"
                + " Matcovitch\n"
                + "and Carol Robinson Sacerdote.,"
                + "  [<a href=\"source?db=null&amp;id=S4\">S4</a>]</li>\n"
                + "\n" + "  </ul>\n"
                + "\n"
                + "  <span class=\"children label\">Children:</span>\n"
                + "  <ol class=\"children\">\n" + "    <li>Living</li>\n"
                + "  </ol>\n" + "</div>\n";

        assertEquals(expected, builder.toString());
    }

    /**
     * @throws IOException because reader can.
     */
    @Test
    public final void testRenderVivianAsSection() throws IOException {
        final GedObject ged = TestDataReader.getInstance().readBigTestSource();
        final Person vivian = (Person) ged.find("I5");
        final PersonRenderer pRenderer = new PersonRenderer(vivian,
                new GedRendererFactory(),
                adminContext);
        final String[] outstrings = {
                "\n"
                        + "<div class=\"family\">\n"
                        + "  <hr class=\"family\"/>\n"
                        + "  <h3 class=\"family\">Family 1</h3>\n"
                        + "  <p class=\"spouse\">\n"
                        + "    <span class=\"spouse label\">Spouse:</span> "
                        + "<a href=\"person?db=null&amp;id=I4248\" "
                        + "class=\"name\">Sabino "
                        + "<span class=\"surname\">Figliuolo</span> "
                        + "(I4248)</a>\n"
                        + "  </p>\n"
                        + "\n"
                        + "  <ul>\n"
                        + "\n"
                        + "  </ul>\n"
                        + "\n"
                        + "  <span class=\"children label\">Children:</span>\n"
                        + "  <ol class=\"children\">\n"
                        + "    <li><a href=\"person?db=null&amp;id=I752\" "
                        + "class=\"name\">"
                        + "Ciara Jean <span class=\"surname\">Figliuolo"
                        + "</span> (I752)</a></li>\n"
                        + "    <li><a href=\"person?db=null&amp;id=I5266\" "
                        + "class=\"name\">"
                        + "Cecilia Caterina <span class=\"surname\">"
                        + "Figliuolo</span> (I5266)</a></li>\n"
                        + "  </ol>\n" + "</div>\n", "" };
        int index = 0;
        final StringBuilder builder = new StringBuilder();
        final List<Family> families =
                vivian.getFamilies(new ArrayList<Family>());
        for (final Family vFam : families) {
            final FamilyRenderer famRenderer = new FamilyRenderer(vFam, // NOPMD
                    new GedRendererFactory(),
                    adminContext);
            final FamilySectionRenderer fsRenderer =
                    (FamilySectionRenderer) famRenderer.getSectionRenderer();
            builder.setLength(0);
            fsRenderer.renderAsSection(builder, pRenderer, false, 0, 1);
            assertEquals(outstrings[index++], builder.toString());
        }
    }

    /**
     * @throws IOException because reader can.
     */
    @Test
    public final void testRenderVivianAsSectionAnonymous() throws IOException {
        final GedObject ged = TestDataReader.getInstance().readBigTestSource();
        final Person vivian = (Person) ged.find("I5");
        final PersonRenderer pRenderer = new PersonRenderer(vivian,
                new GedRendererFactory(), anonymousContext);
        final String[] outstrings = {
                "\n"
                        + "<div class=\"family\">\n"
                        + "  <hr class=\"family\"/>\n"
                        + "  <h3 class=\"family\">Family 1</h3>\n"
                        + "  <p class=\"spouse\">\n"
                        + "    <span class=\"spouse label\">Spouse:</span>"
                        + " Confidential\n"
                        + "  </p>\n"
                        + "\n"
                        + "  <ul>\n"
                        + "\n"
                        + "  </ul>\n"
                        + "\n"
                        + "  <span class=\"children label\">Children:</span>\n"
                        + "  <ol class=\"children\">\n"
                        + "    <li>Living</li>\n"
                        + "    <li>Living</li>\n"
                        + "  </ol>\n" + "</div>\n", "" };
        int index = 0;
        final StringBuilder builder = new StringBuilder();
        final List<Family> families =
                vivian.getFamilies(new ArrayList<Family>());
        for (final Family vFam : families) {
            final FamilyRenderer famRenderer = new FamilyRenderer(vFam, // NOPMD
                    new GedRendererFactory(),
                    anonymousContext);
            final FamilySectionRenderer fsRenderer =
                    (FamilySectionRenderer) famRenderer.getSectionRenderer();
            builder.setLength(0);
            fsRenderer.renderAsSection(builder, pRenderer, false, 0, 1);
            assertEquals(outstrings[index++], builder.toString());
        }
    }

    /** */
    @Test
    public final void testMinimalFamilyAsSectionUser() {
        final Root root1 = new Root(null);
        final Family fam = new Family(root1, new ObjectId("F1"));
        root1.insert(fam);
        final Person person = new Person(root1, new ObjectId("I1"));
        root1.insert(person);
        final FamS fams = new FamS(person, "FAMS", new ObjectId("F1"));
        person.insert(fams);
        final Husband husband = new Husband(fam, "HUSB", new ObjectId("I1"));
        fam.insert(husband);

        final PersonRenderer pRenderer = new PersonRenderer(person,
                new GedRendererFactory(), userContext);
        final FamilyRenderer familyRenderer = new FamilyRenderer(fam,
                new GedRendererFactory(), userContext);
        final FamilySectionRenderer fsr = (FamilySectionRenderer) familyRenderer
                .getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        fsr.renderAsSection(builder, pRenderer, false, 0, 1);
        assertEquals("\n"
                + "<div class=\"family\">\n"
                + "  <hr class=\"family\"/>\n"
                + "  <h3 class=\"family\">Family 1</h3>\n"
                + "  <p class=\"spouse\">\n"
                + "    <span class=\"spouse label\">Spouse:</span> \n"
                + "  </p>\n"
                + "\n"
                + "  <ul>\n"
                + "\n"
                + "  </ul>\n"
                + "\n"
                + "</div>\n", builder.toString());
    }

    /** */
    @Test
    public final void testMinimalFamilyAsSectionAnonymous() {
        final Root root1 = new Root(null);
        final Family fam = new Family(root1, new ObjectId("F1"));
        root1.insert(fam);
        final Person person = new Person(root1, new ObjectId("I1"));
        root1.insert(person);
        final FamS fams = new FamS(person, "FAMS", new ObjectId("F1"));
        person.insert(fams);
        final Husband husband = new Husband(fam, "HUSB", new ObjectId("I1"));
        fam.insert(husband);

        final PersonRenderer pRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final FamilyRenderer familyRenderer = new FamilyRenderer(fam,
                new GedRendererFactory(), anonymousContext);
        final FamilySectionRenderer fsr = (FamilySectionRenderer) familyRenderer
                .getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        fsr.renderAsSection(builder, pRenderer, false, 0, 1);
        assertEquals("\n"
                + "<div class=\"family\">\n"
                + "  <hr class=\"family\"/>\n"
                + "  <h3 class=\"family\">Family 1</h3>\n"
                + "  <p class=\"spouse\">\n"
                + "    <span class=\"spouse label\">Spouse:</span> \n"
                + "  </p>\n"
                + "\n"
                + "  <ul>\n"
                + "\n"
                + "  </ul>\n"
                + "\n"
                + "</div>\n", builder.toString());
    }
}
