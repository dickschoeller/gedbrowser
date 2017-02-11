package org.schoellerfamily.gedbrowser.renderer;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
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
@SuppressWarnings("PMD.CouplingBetweenObjects")
public final class GedRendererFactory {
    /**
     * Dispatcher for factory.
     */
    private static Map<Class<?>, RendererBuilder> builders = new HashMap<>();
    static {
        builders.put(Husband.class, (g, f, r, p) -> {
            return new HusbandRenderer((Husband) g, f, r, p);
        });
        builders.put(Wife.class, (g, f, r, p) -> {
            return new WifeRenderer((Wife) g, f, r, p);
        });
        builders.put(Person.class, (g, f, r, p) -> {
            return new PersonRenderer((Person) g, f, r, p);
        });
        builders.put(Place.class, (g, f, r, p) -> {
            return new PlaceRenderer((Place) g, f, r, p);
        });
        builders.put(Name.class, (g, f, r, p) -> {
            return new NameRenderer((Name) g, f, r, p);
        });
        builders.put(Attribute.class, (g, f, r, p) -> {
            return new AttributeRenderer((Attribute) g, f, r, p);
        });
        builders.put(Multimedia.class, (g, f, r, p) -> {
            return new MultimediaRenderer((Multimedia) g, f, r, p);
        });
        builders.put(Child.class, (g, f, r, p) -> {
            return new ChildRenderer((Child) g, f, r, p);
        });
        builders.put(Date.class, (g, f, r, p) -> {
            return new DateRenderer((Date) g, f, r, p);
        });
        builders.put(FamC.class, (g, f, r, p) -> {
            return new FamCRenderer((FamC) g, f, r, p);
        });
        builders.put(Family.class, (g, f, r, p) -> {
            return new FamilyRenderer((Family) g, f, r, p);
        });
        builders.put(FamS.class, (g, f, r, p) -> {
            return new FamSRenderer((FamS) g, f, r, p);
        });
        builders.put(Head.class, (g, f, r, p) -> {
            return new HeadRenderer((Head) g, f, r, p);
        });
        builders.put(Root.class, (g, f, r, p) -> {
            return new RootRenderer((Root) g, f, r, p);
        });
        builders.put(Source.class, (g, f, r, p) -> {
            return new SourceRenderer((Source) g, f, r, p);
        });
        builders.put(SourceLink.class, (g, f, r, p) -> {
            return new SourceLinkRenderer((SourceLink) g, f, r, p);
        });
        builders.put(Submittor.class, (g, f, r, p) -> {
            return new SubmittorRenderer((Submittor) g, f, r, p);
        });
        builders.put(SubmittorLink.class, (g, f, r, p) -> {
            return new SubmittorLinkRenderer((SubmittorLink) g, f, r, p);
        });
        builders.put(Trailer.class, (g, f, r, p) -> {
            return new TrailerRenderer((Trailer) g, f, r, p);
        });
        builders.put(Link.class, (g, f, r, p) -> {
            return new LinkRenderer((Link) g, f, r, p);
        });
    }

    /**
     * Interface for builders for the factory.
     *
     * @author Dick Schoeller
     */
    private interface RendererBuilder {
        /**
         * @param gedObject a gedobject to render
         * @param factory the factory that we're working with
         * @param renderingContext the current rendering context
         * @param provider provides information about "today"
         * @return the appropriate renderer
         */
        GedRenderer<? extends GedObject> build(
                GedObject gedObject,
                GedRendererFactory factory,
                RenderingContext renderingContext,
                CalendarProvider provider);
    }

    /**
     * Creates the appropriate renderer for the GedObject provided.
     *
     * @param gedObject the GedObject to be rendered
     * @param provider provides information about "today"
     * @param appInfo provides the information about the application
     * @return the renderer
     */
    public GedRenderer<? extends GedObject> create(final GedObject gedObject,
            final CalendarProvider provider, final ApplicationInfo appInfo) {
        return create(gedObject, RenderingContext.anonymous(appInfo), provider);
    }

    /**
     * Creates the appropriate renderer for the GedObject provided.
     *
     * @param gedObject the GedObject to be rendered
     * @param renderingContext the user context we are rendering in
     * @param provider provides information about "today"
     * @return the renderer
     */
    public GedRenderer<? extends GedObject> create(
            final GedObject gedObject,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        if (gedObject != null) {
            final RendererBuilder builder = builders.get(gedObject.getClass());
            if (builder != null) {
                return builder.build(gedObject, this, renderingContext,
                        provider);
            }
        }
        return new DefaultRenderer(
                gedObject, this, renderingContext, provider);
    }
}
