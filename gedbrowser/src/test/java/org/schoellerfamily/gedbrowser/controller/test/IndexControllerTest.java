package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.Application;
import org.schoellerfamily.gedbrowser.test.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class IndexControllerTest implements MenuTestHelper {
    private static final String URL_TEMPLATE = "http://localhost:%d/gedbrowser/surnames?db=%s&letter=%s";

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
    public final void testIndexControllerC() {
        final String url = URL_TEMPLATE.formatted(port, "gl120368", "C");
        final ResponseEntity<String> entity =
            testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Index - C - gl120368</title>")
            .contains("<span><a id=\"letter-?\""
                    + " href=\"surnames?db=gl120368&amp;letter=?\""
                    + " class=\"name\">[?]</a>   </span>")
            .contains("<li id=\"I2508\"><a href=\"person?db=gl120368&amp;id=")
            .contains(getMenu("C"));
    }

    /** */
    @Test
    public final void testIndexControllerB() {
        final String url = URL_TEMPLATE.formatted(port, "gl120368", "B");
        final ResponseEntity<String> entity =
            testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Index - B - gl120368</title>")
            .contains("<span><a id=\"letter-?\""
                    + " href=\"surnames?db=gl120368&amp;letter=?\""
                    + " class=\"name\">[?]</a>   </span>")
            .contains("<li id=\"I2561\"><a href=\"person?db=gl120368&amp;id=")
            .contains(getMenu("B"));
    }

    /** */
    @Test
    public final void testIndexControllerBadDataSet() {
        String url = URL_TEMPLATE.formatted(port, "XYZZY", "A");
		final ResponseEntity<String> entity =
		    testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Data set not found");
    }

    /** */
    @Test
    public final void testIndexControllerLetter() {
        String url = URL_TEMPLATE.formatted(port, "gl120368", "q");
		final ResponseEntity<String> entity =
            testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody())
            .contains("<title>Index - q - gl120368</title>")
            .contains("<span><a id=\"letter-?\""
                    + " href=\"surnames?db=gl120368&amp;letter=?\""
                    + " class=\"name\">[?]</a>   </span>")
            .doesNotContain("<li><a href=\"person?db=gl120368&amp;id=")
            .contains(getMenu("q"));
    }
}