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
public class SourceControllerTest {

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
    public final void test1() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/source?db=gl120368&id=S33750";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Source: S33750 - ");
    }

    /** */
    @Test
    public final void testBadDataSet() {
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(
                        "http://localhost:" + port
                                + "/gedbrowser/source?db=XYZZY&id=S33750",
                        String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Data set not found");
    }

    /** */
    @Test
    public final void testBadPerson() {
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(
                        "http://localhost:" + port
                                + "/gedbrowser/source?db=gl120368&id=XYZZY",
                        String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Source not found");
    }
}
