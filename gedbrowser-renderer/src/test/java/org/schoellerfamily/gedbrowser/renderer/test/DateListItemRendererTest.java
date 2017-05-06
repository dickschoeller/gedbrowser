package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.DateListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.DateRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class DateListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Attribute attribute;

    /** */
    private transient Date date;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        final Person person = new Person();
        attribute = new Attribute(person, "String", "");
        person.addAttribute(attribute);
        date = new Date(attribute, "14 December 1958");
        attribute.addAttribute(date);
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testGetRenderAsListItem() {
        final DateRenderer dRenderer = new DateRenderer(date,
                new GedRendererFactory(), anonymousContext);
        final DateListItemRenderer dlir = (DateListItemRenderer) dRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        dlir.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("Rendered string doesn't match expectation",
                "14 December 1958", string);
    }

    /** */
    @Test
    public void testGetRenderAsListItemEmpty() {
        final DateRenderer dRenderer = new DateRenderer(new Date(attribute, ""),
                new GedRendererFactory(), anonymousContext);
        final DateListItemRenderer dlir = (DateListItemRenderer) dRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        dlir.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testGetRenderAsListItemNull() {
        final DateRenderer dRenderer = new DateRenderer(
                new Date(attribute, null), new GedRendererFactory(),
                anonymousContext);
        final DateListItemRenderer dlir = (DateListItemRenderer) dRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        dlir.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }
}
