package org.schoellerfamily.gedbrowser.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;



/**
 * Provides services for gedbrowser properties.
 *
 * @author Richard Schoeller
 */
@Service
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class GedbrowserPropertiesService {
    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private final String gedbrowserHome;
}
