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
public class SourcesControllerTest implements MenuTestHelper {
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
    public final void testSourcesControllerOK() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/sources?db=gl120368";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Sources - gl120368</title>")
            .contains("Sources for dataset: gl120368</h2>")
            .contains("href=\"source?db=gl120368&amp;id=S2050\" class=\"name\""
                    + " id=\"source-S2050\">Parish records (S2050)")
            .contains("href=\"source?db=gl120368&amp;id=S2124\" class=\"name\""
                    + " id=\"source-S2124\">Www.peake.net (S2124)")
            .contains("href=\"source?db=gl120368&amp;id=S2122\" class=\"name\""
                    + " id=\"source-S2122\">Will of R Harris (S2122)")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    public final void testSourcesControllerBadDataSet() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/gedbrowser/sources?db=XYZZY",
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Data set not found");
    }
}
