package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * Factory to create the appropriate type of renderer based on the type of the
 * GedObject.
 *
 * @author Dick Schoeller
 */
public final class GedRendererFactory { // NOPMD
    /**
     * Creates the appropriate renderer for the GedObject provided.
     *
     * @param gedObject the GedObject to be rendered
     * @return the renderer
     */
    public GedRenderer<? extends GedObject> create(//NOPMD
            final GedObject gedObject) {
        return create(gedObject, RenderingContext.anonymous());
    }

    /**
     * Creates the appropriate renderer for the GedObject provided.
     *
     * @param gedObject the GedObject to be rendered
     * @param renderingContext the user context we are rendering in
     * @return the renderer
     */
    public GedRenderer<? extends GedObject> create(// NOPMD
            final GedObject gedObject,
            final RenderingContext renderingContext) {
        if (gedObject instanceof Husband) {
            return new HusbandRenderer(
                    (Husband) gedObject, this, renderingContext);
        } else if (gedObject instanceof Wife) {
            return new WifeRenderer(
                    (Wife) gedObject, this, renderingContext);
        } else if (gedObject instanceof Person) {
            return new PersonRenderer(
                    (Person) gedObject, this, renderingContext);
        } else if (gedObject instanceof Place) {
            return new PlaceRenderer(
                    (Place) gedObject, this, renderingContext);
        } else if (gedObject instanceof Name) {
            return new NameRenderer(
                    (Name) gedObject, this, renderingContext);
        } else if (gedObject instanceof Attribute) {
            return new AttributeRenderer(
                    (Attribute) gedObject, this, renderingContext);
        } else if (gedObject instanceof Multimedia) {
            return new MultimediaRenderer(
                    (Multimedia) gedObject, this, renderingContext);
        } else if (gedObject instanceof Child) {
            return new ChildRenderer(
                    (Child) gedObject, this, renderingContext);
        } else if (gedObject instanceof Date) {
            return new DateRenderer(
                    (Date) gedObject, this, renderingContext);
        } else if (gedObject instanceof FamC) {
            return new FamCRenderer(
                    (FamC) gedObject, this, renderingContext);
        } else if (gedObject instanceof Family) {
            return new FamilyRenderer(
                    (Family) gedObject, this, renderingContext);
        } else if (gedObject instanceof FamS) {
            return new FamSRenderer(
                    (FamS) gedObject, this, renderingContext);
        } else if (gedObject instanceof Head) {
            return new HeadRenderer(
                    (Head) gedObject, this, renderingContext);
        } else if (gedObject instanceof Root) {
            return new RootRenderer(
                    (Root) gedObject, this, renderingContext);
        } else if (gedObject instanceof Source) {
            return new SourceRenderer(
                    (Source) gedObject, this, renderingContext);
        } else if (gedObject instanceof SourceLink) {
            return new SourceLinkRenderer(
                    (SourceLink) gedObject, this, renderingContext);
        } else if (gedObject instanceof Submittor) {
            return new SubmittorRenderer(
                    (Submittor) gedObject, this, renderingContext);
        } else if (gedObject instanceof SubmittorLink) {
            return new SubmittorLinkRenderer(
                    (SubmittorLink) gedObject, this, renderingContext);
        } else if (gedObject instanceof Trailer) {
            return new TrailerRenderer(
                    (Trailer) gedObject, this, renderingContext);
        } else if (gedObject instanceof Link) {
            return new LinkRenderer(
                    (Link) gedObject, this, renderingContext);
        }
        return new DefaultRenderer(gedObject, this, renderingContext);
    }
}
