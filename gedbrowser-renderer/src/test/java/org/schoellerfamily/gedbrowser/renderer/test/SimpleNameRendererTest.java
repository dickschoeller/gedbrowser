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
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNamePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class SimpleNameRendererTest {
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
    void testRendererTypes(
            final Function<SimpleNameRenderer, Object> accessor,
            final Class<?> expectedType) {
        final SimpleNameRenderer renderer = new SimpleNameRenderer(new Name(null),
            new GedRendererFactory(), anonymousContext);
        assertTrue(expectedType.isInstance(accessor.apply(renderer)),
            "Wrong renderer type");
    }

    private static Stream<Arguments> rendererTypeCases() {
        return Stream.of(
            Arguments.of(
                (Function<SimpleNameRenderer, Object>)
                    SimpleNameRenderer::getAttributeListOpenRenderer,
                SimpleAttributeListOpenRenderer.class),
            Arguments.of(
                (Function<SimpleNameRenderer, Object>)
                    SimpleNameRenderer::getListItemRenderer,
                SimpleNameListItemRenderer.class),
            Arguments.of(
                (Function<SimpleNameRenderer, Object>)
                    SimpleNameRenderer::getNameHtmlRenderer,
                SimpleNameNameHtmlRenderer.class),
            Arguments.of(
                (Function<SimpleNameRenderer, Object>)
                    SimpleNameRenderer::getNameIndexRenderer,
                SimpleNameNameIndexRenderer.class),
            Arguments.of(
                (Function<SimpleNameRenderer, Object>)
                    SimpleNameRenderer::getPhraseRenderer,
                SimpleNamePhraseRenderer.class));
    }
}
