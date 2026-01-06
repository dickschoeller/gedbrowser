package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.DatePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.DateRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class DatePhraseRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Attribute attribute;

    /** */
    private transient Date date;

    /** */
    private transient Date date2;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    public void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson();
        attribute = builder.createPersonEvent(person, "String");
        date = builder.addDateToGedObject(attribute, "14 December 1958");
        builder.createAttribute(date, "Time", "12:00");
        final Attribute a2 = builder.createPersonEvent(person, "String2");
        date2 = builder.addDateToGedObject(a2, "14 December 1958");
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    void testGetRenderAsPhrase() {
        final DateRenderer dRenderer = new DateRenderer(date, new GedRendererFactory(),
            anonymousContext);
        final DatePhraseRenderer dpRenderer = (DatePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = dpRenderer.renderAsPhrase();
        assertEquals("14 December 1958 12:00", string,
            "Rendered date string doesn't match expectation");
    }

    /** */
    @Test
    void testGetRenderAsPhrase2() {
        final DateRenderer dRenderer = new DateRenderer(date2, new GedRendererFactory(),
            anonymousContext);
        final DatePhraseRenderer dpRenderer = (DatePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = dpRenderer.renderAsPhrase();
        assertEquals("14 December 1958", string, "Rendered date string doesn't match expectation");
    }

    /** */
    @Test
    void testGetRenderAsPhraseEmpty() {
        final DateRenderer dRenderer = new DateRenderer(new Date(attribute, ""),
            new GedRendererFactory(), anonymousContext);
        final DatePhraseRenderer dpRenderer = (DatePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = dpRenderer.renderAsPhrase();
        assertEquals("", string, "Expected empty string");
    }

    /** */
    @Test
    void testGetRenderAsPhraseNull() {
        final DateRenderer dRenderer = new DateRenderer(new Date(attribute, null),
            new GedRendererFactory(), anonymousContext);
        final DatePhraseRenderer dpRenderer = (DatePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = dpRenderer.renderAsPhrase();
        assertEquals("", string, "Expected empty string");
    }
}
