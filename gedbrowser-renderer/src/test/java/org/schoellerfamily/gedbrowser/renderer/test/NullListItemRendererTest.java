package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
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
final class NullListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient NullListItemRenderer nsr;

    @BeforeEach
    void setUp() {
        final RenderingContext anonymousContext = RenderingContext.anonymous(appInfo);
        final DefaultRenderer renderer = new DefaultRenderer(createGedObject(),
            new GedRendererFactory(), anonymousContext);
        nsr = (NullListItemRenderer) renderer.getListItemRenderer();
    }

    private GedObject createGedObject() {
        return new GedObject() {
            /**
             * Executes accept.
             *
             * @param visitor the visitor
             */
            @Override
            public void accept(final GedObjectVisitor visitor) {
                visitor.visit(this);
            }
        };
    }

    @Test
    void testRenderAsListItemFalse0() {
        final StringBuilder builder = new StringBuilder();
        final String string = nsr.renderAsListItem(builder, false, 0).toString();
        assertEquals("", string, "Expected empty string");
    }

    @ParameterizedTest
    @MethodSource("renderAsListItemCases")
    void testRenderAsListItem(final boolean newLine, final int pad) {
        final StringBuilder builder = new StringBuilder();
        final String string = nsr.renderAsListItem(builder, newLine, pad).toString();
        assertEquals("", string, "Expected empty string");
    }

    private static Stream<Arguments> renderAsListItemCases() {
        return Stream.of(
            Arguments.of(false, 2),
            Arguments.of(true, 0),
            Arguments.of(true, 2));
    }
}
