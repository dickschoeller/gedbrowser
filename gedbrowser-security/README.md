gedbrowser-security
===================

This module provides JWT-based token helpers used by the gedbrowser application.

JWT secret and key size
-----------------------

- The module uses JJWT 0.13.0. That API requires a signing key (`java.security.Key`) when
  creating/verifying tokens.

- The default configuration property is `jwt.secret`. For production you should provide
  a strong secret (HMAC key material) of sufficient length. For HS512 the key must be
  at least 512 bits (64 bytes).

- If you set `jwt.secret` to a short string (for convenience in development), the
  `TokenHelper` will derive a 64-byte key by hashing the configured secret with
  SHA-512 and then constructing an HMAC key with `io.jsonwebtoken.security.Keys.hmacShaKeyFor`.
  This allows the module to work with short dev secrets while still producing a key
  of the required size for HS512.

Recommended production configuration
------------------------------------

- Prefer providing a secure, random key as a Base64 string with at least 64 bytes of raw
  key material (e.g. a 512-bit random key). For example, you can generate a key with OpenSSL:

```bash
# generate 64 random bytes and print base64
openssl rand -base64 64
```

- Set the resulting value in your Spring configuration (application.properties / YAML) as:

```properties
jwt.secret=<base64-or-raw-secret-here>
jwt.expires_in=3600
```

Notes for developers
--------------------

- We've updated the code to use JJWT 0.13.0 (Key-based signWith and `parserBuilder()`).
- If your IDE shows errors such as "The method parserBuilder() is undefined for the type Jwts",
  refresh the Maven project classpath (e.g. in Eclipse: Maven -> Update Project). The module's
  `pom.xml` declares the required `jjwt-api`, `jjwt-impl`, and `jjwt-jackson` dependencies.

Running tests
-------------

From the repository root you can run tests for this module only:

```bash
cd gedbrowser/gedbrowser-security
mvn -Dtest=TokenHelperSigningTest test
```

Or run the full module test suite:

```bash
cd gedbrowser/gedbrowser-security
mvn test
```

If you want me to add support for asymmetric keys (RSA/EC) or to change token format, tell me and I will implement it.