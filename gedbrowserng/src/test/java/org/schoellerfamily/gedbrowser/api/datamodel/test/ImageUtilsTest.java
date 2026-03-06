package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ImageUtils;

/**
 * @author Dick Schoeller
 */
class ImageUtilsTest {
    /** */
    private ImageUtils imageUtils;

    /** */
    @BeforeEach
    void setUp() {
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

    /**
     * Parameterized test for common image file extensions.
     *
     * @param ext the file extension to test
     */
    @ParameterizedTest
    @ValueSource(strings = {"bmp", "gif", "ico", "jpg", "jpeg", "png", "tiff", "tif", "svg"})
    void testIsImage(final String ext) {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("foo." + ext)
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isImage(file), "Should be an image: " + ext);
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
