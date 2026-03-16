package org.schoellerfamily.gedbrowser.security.controller;

import lombok.NoArgsConstructor;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Handles requests for public.
 *
 * @author Richard Schoeller
 */
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@NoArgsConstructor
public class PublicController {

    /**
     * Controller to just support pinging.
     *
     * @return map of foo to bar
     */
    @RequestMapping(method = GET, value = "/foo")
    public Map<String, String> getFoo() {
        return Map.of("foo", "bar");
    }
}
