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
public class IndexControllerTest {
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
    public final void testIndexControllerOK() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/surnames?db=gl120368&letter=A";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Index - A - gl120368</title>")
            .contains("<span><a href=\"surnames?db=gl120368&amp;letter=?\" cl"
                    + "ass=\"name\">[?]</a>   </span>")
            .contains("<li><a href=\"person?db=gl120368&amp;id=");
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
            .contains("<span><a href=\"surnames?db=gl120368&amp;letter=?\" cl"
                    + "ass=\"name\">[?]</a>   </span>")
            .doesNotContain("<li><a href=\"person?db=gl120368&amp;id=");
    }
}
