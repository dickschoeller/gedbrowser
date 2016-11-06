package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
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
import org.schoellerfamily.gedbrowser.renderer.AttributeRenderer;
import org.schoellerfamily.gedbrowser.renderer.ChildRenderer;
import org.schoellerfamily.gedbrowser.renderer.DateRenderer;
import org.schoellerfamily.gedbrowser.renderer.DefaultRenderer;
import org.schoellerfamily.gedbrowser.renderer.FamCRenderer;
import org.schoellerfamily.gedbrowser.renderer.FamSRenderer;
import org.schoellerfamily.gedbrowser.renderer.FamilyRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.HeadRenderer;
import org.schoellerfamily.gedbrowser.renderer.HusbandRenderer;
import org.schoellerfamily.gedbrowser.renderer.LinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RootRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorRenderer;
import org.schoellerfamily.gedbrowser.renderer.TrailerRenderer;
import org.schoellerfamily.gedbrowser.renderer.WifeRenderer;

/**
 * @author Dick Schoeller
 */
public class GedRendererFactoryTest {
    /** */
    private transient GedRendererFactory grf;

    /** */
    @Before
    public final void init() {
        grf = new GedRendererFactory();
    }

    /** */
    @Test
    public final void testGetHusbandRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Husband());
        assertTrue("Expected HusbandRenderer",
                gedRenderer instanceof HusbandRenderer);
    }

    /** */
    @Test
    public final void testGetWifeRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Wife());
        assertTrue("Expected WifeRenderer",
                gedRenderer instanceof WifeRenderer);
    }

    /** */
    @Test
    public final void testGetPersonRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Person());
        assertTrue("Expected PersonRenderer",
                gedRenderer instanceof PersonRenderer);
    }

    /** */
    @Test
    public final void testGetPlaceRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Place(null));
        assertTrue("Expected PlaceRenderer",
                gedRenderer instanceof PlaceRenderer);
    }

    /** */
    @Test
    public final void testGetNameRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Name(null));
        assertTrue("Expected NameRenderer",
                gedRenderer instanceof NameRenderer);
    }

    /** */
    @Test
    public final void testGetAttributeRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Attribute(null));
        assertTrue("Expected AttributeRenderer",
                gedRenderer instanceof AttributeRenderer);
    }

    /** */
    @Test
    public final void testGetChildRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Child());
        assertTrue("Expected ChildRenderer",
                gedRenderer instanceof ChildRenderer);
    }

    /** */
    @Test
    public final void testGetDateRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Date(null));
        assertTrue("Expected DateRenderer",
                gedRenderer instanceof DateRenderer);
    }

    /** */
    @Test
    public final void testGetFamCRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new FamC(null));
        assertTrue("Expected FamCRenderer",
                gedRenderer instanceof FamCRenderer);
    }

    /** */
    @Test
    public final void testGetFamilyRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Family());
        assertTrue("Expected FamilyRenderer",
                gedRenderer instanceof FamilyRenderer);
    }

    /** */
    @Test
    public final void testGetFamSRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new FamS(null));
        assertTrue("Expected FamSRenderer",
                gedRenderer instanceof FamSRenderer);
    }

    /** */
    @Test
    public final void testGetHeadRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Head(null));
        assertTrue("Expected HeadRenderer",
                gedRenderer instanceof HeadRenderer);
    }

    /** */
    @Test
    public final void testGetRootRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Root(null));
        assertTrue("Expected RootRenderer",
                gedRenderer instanceof RootRenderer);
    }

    /** */
    @Test
    public final void testGetSourceRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Source(null));
        assertTrue("Expected SourceRenderer",
                gedRenderer instanceof SourceRenderer);
    }

    /** */
    @Test
    public final void testGetSourceLinkRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new SourceLink(null));
        assertTrue("Expected SourceLinkRenderer",
                gedRenderer instanceof SourceLinkRenderer);
    }

    /** */
    @Test
    public final void testGetSubmittorRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Submittor(null));
        assertTrue("Expected SubmittorRenderer",
                gedRenderer instanceof SubmittorRenderer);
    }

    /** */
    @Test
    public final void testGetSubmittorLinkRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new SubmittorLink(null));
        assertTrue("Expected SubmittorLinkRenderer",
                gedRenderer instanceof SubmittorLinkRenderer);
    }

    /** */
    @Test
    public final void testGetTrailerRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Trailer(null));
        assertTrue("Expected TrailerRenderer",
                gedRenderer instanceof TrailerRenderer);
    }

    /** */
    @Test
    public final void testGetLinkRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new Link(null));
        assertTrue("Expected LinkRenderer",
                gedRenderer instanceof LinkRenderer);
    }

    /** */
    @Test
    public final void testGetDefaultRenderer() {
        final GedRenderer<?> gedRenderer = grf.create(new GedObject() {
        });
        assertTrue("Expected DefaultRenderer",
                gedRenderer instanceof DefaultRenderer);
    }
}
