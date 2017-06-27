package org.schoellerfamily.gedbrowser.renderer;

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
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
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

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
        builders.put(Husband.class, (g, f, r) -> {
            return new HusbandRenderer((Husband) g, f, r);
        });
        builders.put(Wife.class, (g, f, r) -> {
            return new WifeRenderer((Wife) g, f, r);
        });
        builders.put(Person.class, (g, f, r) -> {
            return new PersonRenderer((Person) g, f, r);
        });
        builders.put(Place.class, (g, f, r) -> {
            return new PlaceRenderer((Place) g, f, r);
        });
        builders.put(Name.class, (g, f, r) -> {
            if (g.getParent() != null
                    && g.getParent().getClass().equals(Submittor.class)) {
                return new SimpleNameRenderer((Name) g, f, r);
            }
            return new NameRenderer((Name) g, f, r);
        });
        builders.put(Attribute.class, (g, f, r) -> {
            return new AttributeRenderer((Attribute) g, f, r);
        });
        builders.put(Multimedia.class, (g, f, r) -> {
            return new MultimediaRenderer((Multimedia) g, f, r);
        });
        builders.put(Child.class, (g, f, r) -> {
            return new ChildRenderer((Child) g, f, r);
        });
        builders.put(Date.class, (g, f, r) -> {
            return new DateRenderer((Date) g, f, r);
        });
        builders.put(FamC.class, (g, f, r) -> {
            return new FamCRenderer((FamC) g, f, r);
        });
        builders.put(Family.class, (g, f, r) -> {
            return new FamilyRenderer((Family) g, f, r);
        });
        builders.put(FamS.class, (g, f, r) -> {
            return new FamSRenderer((FamS) g, f, r);
        });
        builders.put(Head.class, (g, f, r) -> {
            return new HeadRenderer((Head) g, f, r);
        });
        builders.put(Root.class, (g, f, r) -> {
            return new RootRenderer((Root) g, f, r);
        });
        builders.put(Source.class, (g, f, r) -> {
            return new SourceRenderer((Source) g, f, r);
        });
        builders.put(SourceLink.class, (g, f, r) -> {
            return new SourceLinkRenderer((SourceLink) g, f, r);
        });
        builders.put(Submittor.class, (g, f, r) -> {
            return new SubmittorRenderer((Submittor) g, f, r);
        });
        builders.put(SubmittorLink.class, (g, f, r) -> {
            return new SubmittorLinkRenderer((SubmittorLink) g, f, r);
        });
        builders.put(Trailer.class, (g, f, r) -> {
            return new TrailerRenderer((Trailer) g, f, r);
        });
        builders.put(Link.class, (g, f, r) -> {
            return new LinkRenderer((Link) g, f, r);
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
         * @return the appropriate renderer
         */
        GedRenderer<? extends GedObject> build(
                GedObject gedObject,
                GedRendererFactory factory,
                RenderingContext renderingContext);
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
        return create(gedObject, RenderingContext.anonymous(appInfo, provider));
    }

    /**
     * Creates the appropriate renderer for the GedObject provided.
     *
     * @param gedObject the GedObject to be rendered
     * @param renderingContext the user context we are rendering in
     * @return the renderer
     */
    public GedRenderer<? extends GedObject> create(
            final GedObject gedObject,
            final RenderingContext renderingContext) {
        if (gedObject != null) {
            final RendererBuilder builder = builders.get(gedObject.getClass());
            if (builder != null) {
                return builder.build(gedObject, this, renderingContext);
            }
        }
        return new DefaultRenderer(
                gedObject, this, renderingContext);
    }
}
