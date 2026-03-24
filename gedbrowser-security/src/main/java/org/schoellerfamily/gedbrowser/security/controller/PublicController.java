package org.schoellerfamily.gedbrowser.security.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;



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
    @GetMapping(value = "/foo")
    public Map<String, String> getFoo() {
        return Map.of("foo", "bar");
    }
}
