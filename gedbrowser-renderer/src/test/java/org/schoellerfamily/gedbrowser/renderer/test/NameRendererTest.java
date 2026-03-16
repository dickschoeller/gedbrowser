package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NameListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NamePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for name renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class NameRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @ParameterizedTest
    @MethodSource("rendererTypeCases")
    void testRendererTypes(final Function<NameRenderer, Object> accessor,
            final Class<?> expectedType) {
        final NameRenderer renderer = new NameRenderer(new Name(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(expectedType.isInstance(accessor.apply(renderer)),
            "Wrong renderer type");
    }

    private static Stream<Arguments> rendererTypeCases() {
        return Stream.of(
            Arguments.of(
                (Function<NameRenderer, Object>)
                    NameRenderer::getAttributeListOpenRenderer,
                SimpleAttributeListOpenRenderer.class),
            Arguments.of(
                (Function<NameRenderer, Object>)
                    NameRenderer::getListItemRenderer,
                NameListItemRenderer.class),
            Arguments.of(
                (Function<NameRenderer, Object>)
                    NameRenderer::getNameHtmlRenderer,
                NameNameHtmlRenderer.class),
            Arguments.of(
                (Function<NameRenderer, Object>)
                    NameRenderer::getNameIndexRenderer,
                NameNameIndexRenderer.class),
            Arguments.of(
                (Function<NameRenderer, Object>)
                    NameRenderer::getPhraseRenderer,
                NamePhraseRenderer.class));
    }
}
