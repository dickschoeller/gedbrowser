package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

/* default */ class NoteLinkFactory extends AbstractGedObjectFactory {
    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return new NoteLink(parent, tag, new ObjectId(tail));
    }
}
