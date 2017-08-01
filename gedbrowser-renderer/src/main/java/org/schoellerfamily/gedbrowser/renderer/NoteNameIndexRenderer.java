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
     * Constructor.
     *
     * @param noteRenderer the associated NoteRenderer
     */
    public NoteNameIndexRenderer(final NoteRenderer noteRenderer) {
        this.noteRenderer = noteRenderer;
    }

    /**
     * {@inheritDoc}
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
