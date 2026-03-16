package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Note;

/**
 * @author Dick Schoeller
 *
 */
public class NoteNameIndexRenderer implements NameIndexRenderer {
    /** */
    private final transient NoteRenderer noteRenderer;

    /**
     * Creates a new NoteNameIndexRenderer.
     *
     * @param noteRenderer the note renderer
     */
    public NoteNameIndexRenderer(final NoteRenderer noteRenderer) {
        this.noteRenderer = noteRenderer;
    }

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
