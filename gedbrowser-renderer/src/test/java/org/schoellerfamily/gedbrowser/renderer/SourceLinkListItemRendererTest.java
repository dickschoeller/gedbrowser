package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;

/**
 * @author Dick Schoeller
 */
public class SourceLinkListItemRendererTest {
    /** */
    private transient Person person;

    /** */
    @Before
    public final void init() {
        final Root root = new Root(null, "root");
        person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
    }

    /** */
    @Test
    public final void testRenderAsListItem() {
        final SourceLink sourceLink =
                new SourceLink(person, "SOUR", new ObjectId("S1"));
        person.addAttribute(sourceLink);
        final SourceLinkRenderer slRenderer =
                new SourceLinkRenderer(sourceLink, new GedRendererFactory(),
                        RenderingContext.anonymous());
        final SourceLinkListItemRenderer lir =
                (SourceLinkListItemRenderer) slRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 0);
        assertEquals("Wrong html string",
                " [<a href=\"source?db=null&amp;id=S1\">S1</a>]",
                builder.toString());
    }
}
