package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class IndexControllerTest implements MenuTestHelper {
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
    public final void testIndexControllerA() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/surnames?db=gl120368&letter=A";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Index - A - gl120368</title>")
            .contains("<span><a id=\"letter-?\""
                    + " href=\"surnames?db=gl120368&amp;letter=?\""
                    + " class=\"name\">[?]</a>   </span>")
            .contains("<li id=\"I1983\"><a href=\"person?db=gl120368&amp;id=")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    public final void testIndexControllerB() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/surnames?db=gl120368&letter=B";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);

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
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port
                        + "/gedbrowser/surnames?db=XYZZY&letter=A",
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Data set not found");
    }

    /** */
    @Test
    public final void testIndexControllerLetter() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port
                        + "/gedbrowser/surnames?db=gl120368&letter=q",
                String.class);

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
