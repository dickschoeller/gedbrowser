package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ImageUtils;

/**
 * @author Dick Schoeller
 */
public class ImageUtilsTest {
    /** */
    private ImageUtils imageUtils;

    /** */
    @BeforeEach
    public void setUp() {
        this.imageUtils = new ImageUtils();
    }

    /** */
    @Test
    public void testWrapperIsWrapper() {
        final ApiAttribute multimedia = new ApiAttribute("multimedia", "Multimedia");
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.jpg");
        multimedia.getAttributes().add(file);
        assertTrue(imageUtils.isImageWrapper(multimedia), "Should be an image wrapper");
    }

    /** */
    @Test
    public void testWrapperWrapperIsWrapper() {
        final ApiAttribute multimedia = new ApiAttribute("multimedia", "Multimedia");
        final ApiAttribute inter = new ApiAttribute("attribute", "Something");
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.jpg");
        multimedia.getAttributes().add(inter);
        inter.getAttributes().add(file);
        assertTrue(imageUtils.isImageWrapper(multimedia), "Should be an image wrapper");
    }

    /** */
    @Test
    public void testBMPIsImage() {
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.bmp");
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    public void testGIFIsImage() {
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.gif");
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    public void testICOIsImage() {
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.ico");
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    public void testJPGIsImage() {
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.jpg");
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    public void testJPEGIsImage() {
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.jpeg");
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    public void testPNGIsImage() {
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.png");
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    public void testTIFFIsImage() {
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.tiff");
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    public void testTIFIsImage() {
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.tif");
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    public void testSVGIsImage() {
        final ApiAttribute file = new ApiAttribute("attribute", "File", "foo.svg");
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    public void testIsNotImage() {
        final ApiAttribute multimedia = new ApiAttribute("multimedia", "Multimedia");
        assertFalse(imageUtils.isImage(multimedia), "Should not be image");
    }

    /** */
    @Test
    public void testIsNotWrapper() {
        final ApiAttribute multimedia = new ApiAttribute("multimedia", "Multimedia");
        assertFalse(imageUtils.isImageWrapper(multimedia), "Should not be image");
    }
}