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

    @BeforeEach
    void setUp() {
        this.imageUtils = new ImageUtils();
    }

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

    @Test
    void testIsNotImage() {
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attributes(java.util.List.of())
            .build();
        assertFalse(imageUtils.isImage(multimedia), "Should not be image");
    }

    @ParameterizedTest
    @ValueSource(strings = {"avi", "m4v", "mov", "mp4", "mpg", "mpeg", "webm"})
    void testIsVideo(final String ext) {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("clip." + ext)
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isVideo(file), "Should be a video: " + ext);
    }

    @Test
    void testIsNotVideo() {
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("photo.jpg")
            .attributes(java.util.List.of())
            .build();
        assertFalse(imageUtils.isVideo(multimedia), "jpg should not be a video");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
        "https://youtu.be/dQw4w9WgXcQ"
    })
    void testIsYouTube(final String url) {
        final ApiAttribute attr = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail(url)
            .attributes(java.util.List.of())
            .build();
        assertTrue(imageUtils.isYouTube(attr), "Should be a YouTube URL: " + url);
    }

    @Test
    void testIsNotYouTube() {
        final ApiAttribute attr = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("clip.mp4")
            .attributes(java.util.List.of())
            .build();
        assertFalse(imageUtils.isYouTube(attr), "mp4 should not be a YouTube URL");
    }

    @Test
    void testVideoIsNotImage() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("clip.mp4")
            .attributes(java.util.List.of())
            .build();
        assertFalse(imageUtils.isImage(file), "mp4 should not be an image");
    }

    @Test
    void testWrapperWithVideoIsWrapper() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("clip.mp4")
            .build();
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attribute(file)
            .build();
        assertTrue(imageUtils.isImageWrapper(multimedia), "Should be a media wrapper for video");
    }

    @Test
    void testWrapperWithYouTubeIsWrapper() {
        final ApiAttribute file = ApiAttribute.builder()
            .type("attribute")
            .string("File")
            .tail("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
            .build();
        final ApiAttribute multimedia = ApiAttribute.builder()
            .type("multimedia")
            .string("Multimedia")
            .attribute(file)
            .build();
        assertTrue(imageUtils.isImageWrapper(multimedia), "Should be a media wrapper for YouTube");
    }

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
