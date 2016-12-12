package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * @author Dick Schoeller
 */
public class NamePhraseRenderer implements PhraseRenderer {
    /**
     * The renderer that we are associated with.
     */
    private final transient NameRenderer nameRenderer;

    /**
     * Constructor.
     *
     * @param nameRenderer the renderer that we are associated with.
     */
    public NamePhraseRenderer(final NameRenderer nameRenderer) {
        this.nameRenderer = nameRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String renderAsPhrase() {
        final Name name = nameRenderer.getGedObject();
        final String prefix = GedRenderer.escapeString(name.getPrefix());
        final String surname = GedRenderer.escapeString(name.getSurname());
        final String suffix = GedRenderer.escapeString(name.getSuffix());
        final StringBuilder builder = new StringBuilder();
        if (prefix.isEmpty() && surname.isEmpty() && suffix.isEmpty()) {
            builder.append(name.getString());
        } else {
            final boolean hasPrefixAndMore = !prefix.isEmpty()
                    && (!surname.isEmpty() || !suffix.isEmpty());
            final boolean hasSurnameSuffix =
                    !surname.isEmpty() && !suffix.isEmpty();
            builder.append(prefix);
            if (hasPrefixAndMore) {
                builder.append(' ');
            }
            builder.append(surname);
            if (hasSurnameSuffix) {
                builder.append(' ');
            }
            builder.append(suffix);
        }
        return builder.toString();
    }
}
