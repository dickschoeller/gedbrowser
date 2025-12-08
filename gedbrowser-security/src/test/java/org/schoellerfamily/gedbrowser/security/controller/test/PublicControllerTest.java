package org.schoellerfamily.gedbrowser.security.controller.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.security.test.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@Slf4j
public class PublicControllerTest {

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
    public final void testFooController() {
        final String url = "http://localhost:" + port + "/v1/foo";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);
        final String actual = entity.getBody();
        log.debug("string: " + actual);
        assertEquals("unexpected controller response",
                "{\"foo\":\"bar\"}", actual);
    }
}
