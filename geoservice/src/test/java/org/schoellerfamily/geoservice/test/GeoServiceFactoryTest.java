package org.schoellerfamily.geoservice.test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.geoservice.GeoServiceFactory;
import org.schoellerfamily.geoservice.geocoder.GeoCoder;
import org.schoellerfamily.geoservice.geocoder.GoogleGeoCoder;
import org.schoellerfamily.geoservice.geocoder.StubGeoCoder;
import org.schoellerfamily.geoservice.keys.KeyManager;
import org.schoellerfamily.geoservice.keys.KeyManagerImpl;
import org.schoellerfamily.geoservice.keys.KeyManagerStub;
import org.schoellerfamily.geoservice.persistence.GeoCode;

/**
 * Contains tests for {@link GeoServiceFactory}.
 */
public final class GeoServiceFactoryTest {
    @Test
    void shouldUseStubImplementationsWhenConfigured() throws Exception {
        final GeoServiceFactory factory = new GeoServiceFactory(null);
        setKeyfile(factory, "stub");

        final KeyManager keyManager = factory.keyManager();
        final GeoCoder geoCoder = factory.geoCoder();

        assertInstanceOf(KeyManagerStub.class, keyManager);
        assertInstanceOf(StubGeoCoder.class, geoCoder);
        assertSame(geoCoder, factory.geoCoder());
    }

    @Test
    void shouldCreateSingletonPersistenceManagerAndLoader() throws Exception {
        final GeoServiceFactory factory = new GeoServiceFactory(null);
        setKeyfile(factory, "stub");

        final GeoCode first = factory.persistenceManager();
        final GeoCode second = factory.persistenceManager();

        assertNotNull(factory.appInfo());
        assertNotNull(factory.loader());
        assertSame(first, second);
    }

    @Test
    void shouldReturnRealKeyManagerWhenNotStub() throws Exception {
        final GeoServiceFactory factory = new GeoServiceFactory(null);
        setKeyfile(factory, "/tmp/does-not-matter");

        assertInstanceOf(KeyManagerImpl.class, factory.keyManager());
    }

    @Test
    void shouldCreateGoogleGeoCoderWhenKeyFileExists() throws Exception {
        final GeoServiceFactory factory = new GeoServiceFactory(null);
        final Path keyFile = Files.createTempFile("geoservice-key", ".txt");
        try {
            Files.writeString(keyFile, "test-key\nmap-key\n", StandardCharsets.UTF_8);
            setKeyfile(factory, keyFile.toString());

            final GeoCoder geoCoder = factory.geoCoder();

            assertInstanceOf(GoogleGeoCoder.class, geoCoder);
            assertSame(geoCoder, factory.geoCoder());
        } finally {
            Files.deleteIfExists(keyFile);
        }
    }

    private static void setKeyfile(final GeoServiceFactory factory, final String keyfile)
            throws NoSuchFieldException, IllegalAccessException {
        final Field field = GeoServiceFactory.class.getDeclaredField("keyfile");
        field.setAccessible(true);
        field.set(factory, keyfile);
    }
}
