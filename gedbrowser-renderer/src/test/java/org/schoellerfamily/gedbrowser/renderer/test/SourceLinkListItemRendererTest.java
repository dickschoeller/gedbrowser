package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SourceLinkListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Person person;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.createPerson("I1");
        final Root root = builder.getRoot();
        final Source source1 = new Source(root, new ObjectId("S1"));
        root.insert(source1);
        final Source source2 = new Source(root, new ObjectId("S2"));
        builder.createAttribute(source2, "Title", "The title of S2");

        root.insert(source2);
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsListItem() {
        final SourceLink sourceLink =
                new SourceLink(person, "SOUR", new ObjectId("S1"));
        person.addAttribute(sourceLink);
        final SourceLinkRenderer slRenderer = new SourceLinkRenderer(sourceLink,
                new GedRendererFactory(), anonymousContext);
        final SourceLinkListItemRenderer lir =
                (SourceLinkListItemRenderer) slRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 0);
        assertEquals("Rendered html doesn't match expectation",
                "<span class=\"label\">Source:</span> <a href=\"source?db=null"
                + "&amp;id=S1\" class=\"name\" id=\"source-S1\">S1 (S1)</a>",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderAsListItemWithTitle() {
        final SourceLink sourceLink =
                new SourceLink(person, "SOUR", new ObjectId("S2"));
        person.addAttribute(sourceLink);
        final SourceLinkRenderer slRenderer = new SourceLinkRenderer(sourceLink,
                new GedRendererFactory(), anonymousContext);
        final SourceLinkListItemRenderer lir =
                (SourceLinkListItemRenderer) slRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 0);
        assertEquals("Rendered html doesn't match expectation",
                "<span class=\"label\">Source:</span>"
                + " <a href=\"source?db=null&amp;id=S2\""
                + " class=\"name\" id=\"source-S2\">The title of S2 (S2)</a>",
                builder.toString());
    }
}
