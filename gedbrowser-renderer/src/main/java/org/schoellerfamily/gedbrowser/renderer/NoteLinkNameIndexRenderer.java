package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;

/**
 * @author Dick Schoeller
 */
public class NoteLinkNameIndexRenderer implements NameIndexRenderer {
    /** */
    private final NoteLinkRenderer noteLinkRenderer;

    /**
     * Creates a new NoteLinkNameIndexRenderer.
     *
     * @param noteLinkRenderer the note link renderer
     */
    public NoteLinkNameIndexRenderer(final NoteLinkRenderer noteLinkRenderer) {
        this.noteLinkRenderer = noteLinkRenderer;
    }

    /**
     * Returns the index name.
     *
     * @return the index name
     */
    @Override
    public String getIndexName() {
        final NoteLink noteLink = noteLinkRenderer
                .getGedObject();
        if (!noteLink.isSet()) {
            return "";
        }
        final Note note =
                (Note) noteLink.find(noteLink.getToString());
        if (note == null) {
            // Prevents problems with malformed file
            return noteLink.getToString();
        }
        final NoteRenderer noteRenderer =
                (NoteRenderer) new GedRendererFactory().create(note,
                        noteLinkRenderer.getRenderingContext());
        return noteRenderer.getIndexNameHtml();
    }

}
