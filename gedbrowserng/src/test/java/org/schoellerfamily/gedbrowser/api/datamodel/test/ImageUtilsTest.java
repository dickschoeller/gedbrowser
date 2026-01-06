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
    void testWrapperIsWrapper() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.jpg")
            .build();
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attribute(file)
            .build();
        assertTrue(imageUtils.isImageWrapper(multimedia), "Should be an image wrapper");
    }

    /** */
    @Test
    void testWrapperWrapperIsWrapper() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.jpg")
            .build();
        final ApiAttribute inter = ApiAttribute.builder()
            .type("attribute")
            .string("Something")
            .attribute(file)
            .build();
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attribute(inter)
            .build();
        assertTrue(imageUtils.isImageWrapper(multimedia), "Should be an image wrapper");
    }

    /** */
    @Test
    void testBMPIsImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.bmp")
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    void testGIFIsImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.gif")
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    void testICOIsImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.ico")
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    void testJPGIsImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.jpg")
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    void testJPEGIsImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.jpeg")
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    void testPNGIsImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.png")
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    void testTIFFIsImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.tiff")
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    void testTIFIsImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.tif")
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    void testSVGIsImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo.svg")
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image");
    }

    /** */
    @Test
    void testIsNotImage() {
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attributes(java.util.List.of())
            .build();
        assertFalse(imageUtils.isImage(multimedia), "Should not be image");
    }

    /** */
    @Test
    void testIsNotWrapper() {
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attributes(java.util.List.of())
            .build();
        assertFalse(imageUtils.isImageWrapper(multimedia), "Should not be image");
    }
}
