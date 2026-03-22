package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.visitor.MultimediaVisitor;

import lombok.RequiredArgsConstructor;

/**
 * Renders multimedia phrase output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public final class MultimediaPhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the MultimediaRenderer that is using this helper.
     */
    private final MultimediaRenderer multimediaRenderer;

    /**
     * Executes render as phrase.
     *
     * @return the resulting string
     */
    @Override
    public String renderAsPhrase() {
        final StringBuilder builder = new StringBuilder("<a href=\"");
        final Multimedia multimedia = multimediaRenderer.getGedObject();
        final MultimediaVisitor visitor = new MultimediaVisitor();
        multimedia.accept(visitor);
        builder.append(visitor.getFilePath());
        builder.append("\">");
        builder.append(visitor.getTitle());
        builder.append("</a>");
        return builder.toString();
    }
}
