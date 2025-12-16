Sauce Labs test integration

This document explains how the Selenium tests in this module interact with Sauce Bindings (saucebindings-junit5) and how to run tests locally or on Sauce Labs.

Overview
- Tests register the `com.saucelabs.saucebindings.junit5.SauceBindingsExtension` with `@RegisterExtension`.
- At test setup, tests first attempt to use the extension-provided WebDriver via `sauceExtension.getDriver()`.
- If the extension does not create a driver (for example, when Sauce credentials are not configured), tests fall back to `WebDriverFactory.webDriver(...)`, which creates a RemoteWebDriver pointing to the local Selenium Grid (default `http://localhost:4445/wd/hub`) and includes a minimal `sauce:options` capability if SAUCE env vars are present.

Environment
- To run tests against Sauce Labs set these environment variables:
  - SAUCE_USERNAME - your Sauce Labs username
  - SAUCE_ACCESS_KEY - your Sauce Labs access key
  - Optional: TRAVIS_BUILD_NUMBER (or set your CI build identifier) will be used for the Sauce build/name metadata.

Running tests
- Run tests locally (falls back to local WebDriver):

```bash
mvn -Dskip.unit.tests=false -Dskip.integration.tests=false -pl gedbrowser test
```

- Run a single test class (example):

```bash
mvn -Dtest=org.schoellerfamily.gedbrowser.selenium.test.SauceBindingsExampleIT -pl gedbrowser test
```

- Run tests on Sauce Labs (ensure SAUCE_USERNAME and SAUCE_ACCESS_KEY are set):

```bash
export SAUCE_USERNAME=yourUser
export SAUCE_ACCESS_KEY=yourKey
mvn -Dtest=org.schoellerfamily.gedbrowser.selenium.test.SauceBindingsExampleIT -pl gedbrowser test
```

Notes
- When the SauceBindingsExtension creates the driver, it manages session lifecycle and reports success/failure to Sauce automatically. When tests use the local `WebDriverFactory`, tests are responsible for quitting the driver; test code only quits drivers created by the factory.
- If you'd rather always run with Sauce Bindings and never fall back, edit tests to remove the fallback logic and require the extension-created driver.

Troubleshooting
- If you see "SauceBindingsExtension did not create a WebDriver" and you expect Sauce to be used, ensure SAUCE_USERNAME and SAUCE_ACCESS_KEY are set in the environment where Maven runs.
- For local runs, start a Selenium Grid at the configured URL (default http://localhost:4445/wd/hub) or change the URL in `WebDriverFactory.getRemoteUrl()`.
