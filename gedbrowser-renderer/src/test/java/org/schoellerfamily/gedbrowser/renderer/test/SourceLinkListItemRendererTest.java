package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkRenderer;

/**
 * @author Dick Schoeller
 */
public final class SourceLinkListItemRendererTest {
    /** */
    private transient Person person;

    /** */
    private CalendarProvider provider;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        final Root root = new Root(null, "root");
        person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        provider = new CalendarProviderStub();
        final ApplicationInfo appInfo = new ApplicationInfoStub();
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsListItem() {
        final SourceLink sourceLink =
                new SourceLink(person, "SOUR", new ObjectId("S1"));
        person.addAttribute(sourceLink);
        final SourceLinkRenderer slRenderer = new SourceLinkRenderer(sourceLink,
                new GedRendererFactory(), anonymousContext,
                provider);
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
