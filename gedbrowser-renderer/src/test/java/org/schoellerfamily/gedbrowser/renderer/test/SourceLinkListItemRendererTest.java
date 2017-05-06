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
        final Root root = new Root("Root");
        person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
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
                " [<a href=\"source?db=null&amp;id=S1\">S1</a>]",
                builder.toString());
    }
}
