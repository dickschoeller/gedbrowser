# Frontend

This project was generated with
[Angular CLI](https://github.com/angular/angular-cli) version 1.5.0.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The
app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can
also use `ng generate
directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the
`dist/` directory. Use the `-prod` flag for a production build.

IMPORTANT: If you want the `gedbrowserng` Java application to include this
frontend build you must install the frontend artifact into your local Maven
repository. Building with `npm`/`ng` alone will not make the artifact available
for the multi-module Maven build.

To build and install the frontend artifact into your local Maven repository
(skip unit and integration tests) run:

mvn -DskipTests -DskipITs clean install

(You can also run `mvn -DskipTests -DskipITs -DskipIntegrationTests clean install`)

After the frontend is installed, build or run `gedbrowserng` (the Java app) and
it will pick up the new frontend artifact from your local Maven repository.

## Running unit tests

Run `ng test` to execute the unit tests via
[Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via
[Protractor](http://www.protractortest.org/).

## Troubleshooting

- If `gedbrowserng` fails to start because port 8080 is in use, stop the other
  process (for example `lsof -i :8080` then `kill <PID>`), or change the
  application's port in `application.properties` / `application.yml`.
- If the Java app still serves an older frontend, double-check that the
  `gedbrowserng-frontend` artifact version in the local Maven repository
  (`~/.m2/repository/org/schoellerfamily/gedbrowser/gedbrowserng-frontend/...`) matches the version referenced by the `gedbrowserng` module.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the
[Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).