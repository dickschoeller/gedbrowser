package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/**
 * Factory for creating notes.
 *
 * @author Richard Schoeller
 */
/* default */ class NoteFactory extends AbstractGedObjectFactory {
    /** */
    /* default */ static final NoteLinkFactory NOTELINK_FACTORY = new NoteLinkFactory();

    static {
        put("NOTE", "Note", new NoteFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        if (parent.getParent() == null) {
            return new Note(parent, xref, tail);
        } else {
            if (tail.contains("@")) {
                return NOTELINK_FACTORY.create(parent, xref, tag, tail);
            } else {
                return AttributeFactory.ATTR_FACTORY.create(parent, xref, tag, tail);
            }
        }
    }
}
