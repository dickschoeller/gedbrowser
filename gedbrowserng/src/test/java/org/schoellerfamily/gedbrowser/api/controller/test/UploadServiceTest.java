package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@SuppressWarnings({ "PMD.JUnitTestsShouldIncludeAssert", "null" })
@AutoConfigureRestTestClient
public class UploadServiceTest {
    /**
     * RestTestClient injected by Spring's test support.
     */
    @Autowired
    private RestTestClient restTestClient;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /** */
    @Test
    public final void testRealUpload() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/upload";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "mini-schoeller.ged");
        body.add("file", getFile("mini-schoeller.ged"));
        final EntityExchangeResult<ApiHead> response = restTestClient.post()
            .uri(url)
            .headers(h -> h.addAll(headers))
            .body(body)
            .exchange()
            .returnResult(ApiHead.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.OK.value()), response.getStatus(),
            "Status mismatch");
        assertEquals("Header",
            java.util.Optional.ofNullable(response.getResponseBody())
                .map(b -> b.getString())
                .orElse(null),
            "Type mismatch");
    }

    /** */
    @Test
    public final void testDotDotUpload() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/upload";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "mini..schoeller.ged");
        body.add("file", getFile("mini..schoeller.ged"));
        final EntityExchangeResult<ApiHead> response = restTestClient.post()
            .uri(url)
            .headers(h -> h.addAll(headers))
            .body(body)
            .exchange()
            .returnResult(ApiHead.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), response.getStatus(),
            "Status mismatch");
        assertEquals("",
            java.util.Optional.ofNullable(response.getResponseBody())
                .map(b -> b.getString())
                .orElse(null),
            "Type mismatch");
    }

    /** */
    @Test
    public final void testEmptyUpload() {
        final String url = "http://localhost:" + port + "/gedbrowserng/v1/upload";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "empty.ged");
        body.add("file", getFile("empty.ged"));
        final EntityExchangeResult<ApiHead> response = restTestClient.post()
            .uri(url)
            .headers(h -> h.addAll(headers))
            .body(body)
            .exchange()
            .returnResult(ApiHead.class);
        assertEquals(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), response.getStatus(),
            "Status mismatch");
        assertEquals("",
            java.util.Optional.ofNullable(response.getResponseBody())
                .map(b -> b.getString())
                .orElse(null),
            "Type mismatch");
    }

    /**
     * @param name file name to open
     * @return the ged file as a classpath resource
     */
    private ClassPathResource getFile(final String name) {
        return new ClassPathResource(name);
    }
}
