package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for submitter link list item renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class SubmitterLinkListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient SubmitterLink submitterLink;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        final Root root = new Root("Root");
        final Head head = new Head(root, "Head");
        root.insert(head);

        final Submitter submitter = new Submitter(root, new ObjectId("S1"));
        final Name name = new Name(submitter, "Richard/Schoeller/");
        root.insert(submitter);
        submitter.insert(name);

        submitterLink = new SubmitterLink(head, "SUBM", new ObjectId("S1"));
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @ParameterizedTest(name = "should render list item for newline={0}, pad={1}")
    @MethodSource("listItemCases")
    void testRenderAsListItem(final boolean newLine, final int pad) {
        final SubmitterLinkRenderer slr = new SubmitterLinkRenderer(submitterLink,
            new GedRendererFactory(), anonymousContext);
        final SubmitterLinkListItemRenderer lir = (SubmitterLinkListItemRenderer) slr
            .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, newLine, pad);
        assertEquals(
            "<span class=\"label\">Submitter:</span> <a class=\"name\""
                + " href=\"submitter?db=null&amp;id=S1\">" + "Richard Schoeller [S1]</a>",
            builder.toString(), "Rendered html doesn't match expectation");
    }

    private static Stream<Arguments> listItemCases() {
        return Stream.of(
            Arguments.of(false, 0),
            Arguments.of(true, 0),
            Arguments.of(false, 2)
        );
    }
}
