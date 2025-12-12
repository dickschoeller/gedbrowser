package org.schoellerfamily.gedbrowser.renderer;

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
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
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
    private static Map<Class<?>, RendererBuilder> builders = Map.ofEntries(
        Map.entry((Class<?>)Husband.class, (RendererBuilder)(g, f, r) -> new HusbandRenderer((Husband) g, f, r)),
        Map.entry((Class<?>)Wife.class, (RendererBuilder)(g, f, r) -> new WifeRenderer((Wife) g, f, r)),
        Map.entry((Class<?>)Person.class, (RendererBuilder)(g, f, r) -> new PersonRenderer((Person) g, f, r)),
        Map.entry((Class<?>)Place.class, (RendererBuilder)(g, f, r) -> new PlaceRenderer((Place) g, f, r)),
        Map.entry((Class<?>)Note.class, (RendererBuilder)(g, f, r) -> new NoteRenderer((Note) g, f, r)),
        Map.entry((Class<?>)NoteLink.class, (RendererBuilder)(g, f, r) -> new NoteLinkRenderer((NoteLink) g, f, r)),
        Map.entry((Class<?>)Attribute.class, (RendererBuilder)(g, f, r) -> new AttributeRenderer((Attribute) g, f, r)),
        Map.entry((Class<?>)Multimedia.class, (RendererBuilder)(g, f, r) -> new MultimediaRenderer((Multimedia) g, f, r)),
        Map.entry((Class<?>)Child.class, (RendererBuilder)(g, f, r) -> new ChildRenderer((Child) g, f, r)),
        Map.entry((Class<?>)Date.class, (RendererBuilder)(g, f, r) -> new DateRenderer((Date) g, f, r)),
        Map.entry((Class<?>)FamC.class, (RendererBuilder)(g, f, r) -> new FamCRenderer((FamC) g, f, r)),
        Map.entry((Class<?>)Family.class, (RendererBuilder)(g, f, r) -> new FamilyRenderer((Family) g, f, r)),
        Map.entry((Class<?>)FamS.class, (RendererBuilder)(g, f, r) -> new FamSRenderer((FamS) g, f, r)),
        Map.entry((Class<?>)Head.class, (RendererBuilder)(g, f, r) -> new HeadRenderer((Head) g, f, r)),
        Map.entry((Class<?>)Root.class, (RendererBuilder)(g, f, r) -> new RootRenderer((Root) g, f, r)),
        Map.entry((Class<?>)Source.class, (RendererBuilder)(g, f, r) -> new SourceRenderer((Source) g, f, r)),
        Map.entry((Class<?>)SourceLink.class, (RendererBuilder)(g, f, r) -> new SourceLinkRenderer((SourceLink) g, f, r)),
        Map.entry((Class<?>)Submission.class, (RendererBuilder)(g, f, r) -> new SubmissionRenderer((Submission) g, f, r)),
        Map.entry((Class<?>)SubmissionLink.class, (RendererBuilder)(g, f, r) -> new SubmissionLinkRenderer((SubmissionLink) g, f, r)),
        Map.entry((Class<?>)Submitter.class, (RendererBuilder)(g, f, r) -> new SubmitterRenderer((Submitter) g, f, r)),
        Map.entry((Class<?>)SubmitterLink.class, (RendererBuilder)(g, f, r) -> new SubmitterLinkRenderer((SubmitterLink) g, f, r)),
        Map.entry((Class<?>)Trailer.class, (RendererBuilder)(g, f, r) -> new TrailerRenderer((Trailer) g, f, r)),
        Map.entry((Class<?>)Link.class, (RendererBuilder)(g, f, r) -> new LinkRenderer((Link) g, f, r)),
        Map.entry((Class<?>)Name.class, (RendererBuilder)(g, f, r) -> {
            if (g.getParent() != null
                    && g.getParent().getClass().equals(Submitter.class)) {
                return new SimpleNameRenderer((Name) g, f, r);
            }
            return new NameRenderer((Name) g, f, r);
        }));
    

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
