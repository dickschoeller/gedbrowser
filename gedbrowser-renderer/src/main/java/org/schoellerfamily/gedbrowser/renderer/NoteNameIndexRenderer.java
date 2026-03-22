package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Note;

import lombok.RequiredArgsConstructor;

/**
 * Renders note name index output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class NoteNameIndexRenderer implements NameIndexRenderer {
    /** */
    private final NoteRenderer noteRenderer;

    /**
     * Returns the index name.
     *
     * @return the index name
     */
    @Override
    public final String getIndexName() {
        final Note note = noteRenderer.getGedObject();
        if (!note.isSet()) {
            return "";
        }

        final String nameHtml = noteRenderer.getTitleString();

        return "<a href=\"note?db=" + note.getDbName() + "&amp;id="
            + note.getString() + "\" class=\"name\" id=\"note-"
            + note.getString() + "\">" + nameHtml + " ("
            + note.getString() + ")</a>";
    }
}
