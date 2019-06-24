package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class UploadServiceTest {
    /**
     * Not sure what this is good for.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /** */
    @Test
    public final void testRealUpload() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/upload";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "mini-schoeller.ged");
        body.add("file", getFile("mini-schoeller.ged"));
        final HttpEntity<MultiValueMap<String, Object>> entity =
                new HttpEntity<>(body, headers);
        final ResponseEntity<ApiHead> response = testRestTemplate
                .postForEntity(url, entity, ApiHead.class);
        assertEquals("Status mismatch",
                HttpStatus.OK, response.getStatusCode());
        assertEquals("Type mismatch", "Header", response.getBody().getString());
    }

    /** */
    @Test
    public final void testDotDotUpload() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/upload";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "mini..schoeller.ged");
        body.add("file", getFile("mini..schoeller.ged"));
        final HttpEntity<MultiValueMap<String, Object>> entity =
                new HttpEntity<>(body, headers);
        final ResponseEntity<ApiHead> response = testRestTemplate
                .postForEntity(url, entity, ApiHead.class);
        assertEquals("Status mismatch",
                HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Type mismatch", "", response.getBody().getString());
    }

    /** */
    @Test
    public final void testEmptyUpload() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/upload";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "empty.ged");
        body.add("file", getFile("empty.ged"));
        final HttpEntity<MultiValueMap<String, Object>> entity =
                new HttpEntity<>(body, headers);
        final ResponseEntity<ApiHead> response = testRestTemplate
                .postForEntity(url, entity, ApiHead.class);
        assertEquals("Status mismatch",
                HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Type mismatch", "", response.getBody().getString());
    }

    /**
     * @param name file name to open
     * @return the ged file as a classpath resource
     */
    private ClassPathResource getFile(final String name) {
        return new ClassPathResource(name);
    }
}
