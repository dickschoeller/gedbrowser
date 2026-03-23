package org.schoellerfamily.gedobject.datamodel.factory;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

import lombok.NoArgsConstructor;

/**
 * Top level factory class for creating GedObjects. Extracted from
 * AbstractGedObjectFactory as a public, instantiable class so callers can
 * construct it directly instead of referring to the nested class.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class GedObjectStarterFactory extends AbstractGedObjectFactory {
    static {
        try {
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.AttributeFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.ChildFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.ConcatenationFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.ContinuationFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.DateFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.FamCFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.FamilyFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.FamSFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.HeadFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.HusbandFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.LinkFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.MultimediaFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.NameFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.NoteFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.NoteLinkFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.PersonFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.PlaceFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.RootFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.SourceFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.SourceLinkFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.SubmissionFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.SubmissionLinkFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.SubmitterFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.SubmitterLinkFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.TrailerFactory");
            Class.forName("org.schoellerfamily.gedobject.datamodel.factory.WifeFactory");
        } catch (ClassNotFoundException _) {
            // punt
        }
        put("NULL", null, new GedObjectStarterFactory());
    }

    @Override
    public GedObject create(final GedObject parent, final ObjectId xref, final String tag,
            final String tail) {
        return null;
    }
}
