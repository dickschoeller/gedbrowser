package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ImageUtils;

/**
 * @author Dick Schoeller
 */
public class ImageUtilsTest {
    /** */
    private ImageUtils imageUtils;

    /** */
    @Before
    public void setUp() {
        this.imageUtils = new ImageUtils();
    }

    /** */
    @Test
    public void testWrapperIsWrapper() {
        final ApiAttribute multimedia = new ApiAttribute(
                "multimedia", "Multimedia");
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.jpg");
        multimedia.getAttributes().add(file);
        assertTrue("Should be an image wrapper",
                imageUtils.isImageWrapper(multimedia));
    }

    /** */
    @Test
    public void testWrapperWrapperIsWrapper() {
        final ApiAttribute multimedia = new ApiAttribute(
                "multimedia", "Multimedia");
        final ApiAttribute inter = new ApiAttribute("attribute", "Something");
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.jpg");
        multimedia.getAttributes().add(inter);
        inter.getAttributes().add(file);
        assertTrue("Should be an image wrapper",
                imageUtils.isImageWrapper(multimedia));
    }

    /** */
    @Test
    public void testBMPIsImage() {
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.bmp");
        assertTrue("Should be an image", imageUtils.isImage(file));
    }

    /** */
    @Test
    public void testGIFIsImage() {
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.gif");
        assertTrue("Should be an image", imageUtils.isImage(file));
    }

    /** */
    @Test
    public void testICOIsImage() {
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.ico");
        assertTrue("Should be an image", imageUtils.isImage(file));
    }

    /** */
    @Test
    public void testJPGIsImage() {
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.jpg");
        assertTrue("Should be an image", imageUtils.isImage(file));
    }

    /** */
    @Test
    public void testJPEGIsImage() {
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.jpeg");
        assertTrue("Should be an image", imageUtils.isImage(file));
    }

    /** */
    @Test
    public void testPNGIsImage() {
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.png");
        assertTrue("Should be an image", imageUtils.isImage(file));
    }

    /** */
    @Test
    public void testTIFFIsImage() {
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.tiff");
        assertTrue("Should be an image", imageUtils.isImage(file));
    }

    /** */
    @Test
    public void testTIFIsImage() {
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.tif");
        assertTrue("Should be an image", imageUtils.isImage(file));
    }

    /** */
    @Test
    public void testSVGIsImage() {
        final ApiAttribute file = new ApiAttribute(
                "attribute", "File", "foo.svg");
        assertTrue("Should be an image", imageUtils.isImage(file));
    }

    /** */
    @Test
    public void testIsNotImage() {
        final ApiAttribute multimedia = new ApiAttribute(
                "multimedia", "Multimedia");
        assertFalse("Should not be image", imageUtils.isImage(multimedia));
    }

    /** */
    @Test
    public void testIsNotWrapper() {
        final ApiAttribute multimedia = new ApiAttribute(
                "multimedia", "Multimedia");
        assertFalse("Should not be image",
                imageUtils.isImageWrapper(multimedia));
    }
}
