# gedbrowser

Spring Boot application to display the genealogy data from a
[GEDCOM](http://wiki-en.genealogy.net/GEDCOM) file. Once loaded,
the data is managed
in a [MongoDB](https://www.mongodb.org/) database.

Check it out by perusing [my genealogy
database](http://www.schoellerfamily.org/gedbrowser/surnames?db=schoeller). You can
browse the database anonymously, but you will need a login with the appropriate
role to see living people.

| Branch | Travis | Coverage | Code Climate | Codebeat | Dependencies | Sauce |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| Master | [![Build Status](https://api.travis-ci.org/dickschoeller/gedbrowser.svg?branch=master)](https://travis-ci.org/dickschoeller/gedbrowser) | [![Coverage Status](https://coveralls.io/repos/github/dickschoeller/gedbrowser/badge.svg?branch=master)](https://coveralls.io/github/dickschoeller/gedbrowser?branch=master) |
| Development | [![Build Status](https://api.travis-ci.org/dickschoeller/gedbrowser.svg?branch=development)](https://travis-ci.org/dickschoeller/gedbrowser) | [![Coverage Status](https://coveralls.io/repos/github/dickschoeller/gedbrowser/badge.svg?branch=development)](https://coveralls.io/github/dickschoeller/gedbrowser?branch=development) | [![Code Climate](https://codeclimate.com/github/dickschoeller/gedbrowser/badges/gpa.svg)](https://codeclimate.com/github/dickschoeller/gedbrowser) | [![codebeat badge](https://codebeat.co/badges/0a10c645-cc88-4f2f-9058-df89e8dc408f)](https://codebeat.co/projects/github-com-dickschoeller-gedbrowser-development) | [![Dependency Status](https://www.versioneye.com/user/projects/58987dc1f55eb2003257f7bd/badge.svg)](https://www.versioneye.com/user/projects/58987dc1f55eb2003257f7bd) | [![Build Status](https://saucelabs.com/buildstatus/dickschoeller)](https://saucelabs.com/open_sauce/user/dickschoeller) |

## Getting started

* Prerequisistes are a JDK, Maven, Git, and MongoDB
  * Optional Docker, which can be used to get MongoDB
* Clone this repository
* From the top 'mvn clean install'
* Place GEDCOM files in /var/lib/gedbrowser
* Create /var/lib/gedbrowser/userFile.csv rows are:
  username,firstname,lastname,email,password,role,role...
* java -jar gedbrowser/target/gedbrowser-1.1.2.jar

Running with Docker requires running the MongoDB with Docker. The following
commands allow you to do this without conflicting ports with a native mongod
service. Note that these commands will conflict with a running native Tomcat
server.

* Create data directory for mongodb and home directory for gedbrowser
* Put data files in home directory
  * Any GEDCOM files that you want to display
  * google-geocoding-key - file should contain your geocoding and mapping keys
    on 2 separate lines
  * userFile.csv - format is: username,first name,last
    name,email,password,role,role,... - supported roles are USER and ADMIN
* export DATA_DIR=&lt;your data directory&gt;
* export GEDBROWSER_HOME=&lt;your gedbrowser home&gt;
* docker run --rm -v ${DATA_DIR}:/data/db --name mongo -p 28001:27017 -d mongo
* docker run --link mongo:mongo -v ${GEDBROWSER_HOME}:/var/lib/gedbrowser -p
  8086:8080 -p 8087:8081 --name geoservice -d dickschoeller/geoservice
* docker run --link geoservice:geoservice --link mongo:mongo -v
  ${GEDBROWSER_HOME}:/var/lib/gedbrowser -p 8080:8080 -p 8081:8081 --name
  gedbrowser -d dickschoeller/gedbrowser

As each GEDCOM file is referred to, it will be loaded into your instance of
MongoDB. More explicit management of data loading is planned for the future.

The location of gedbrowser.home defaults to /var/lib/gedbrowser. However that
can be adjusted in the file application.yml.

## Tooling

* ![](images/overvio.png)
  [Overv.io](https://overv.io/dickschoeller/gedbrowser/)
  task board
* ![](images/jenkins-25.png)
  [Jenkins](http://www.schoellerfamily.org/jenkins/)
  builds
* ![](images/travis-ci-25.png)
  [Travis CI](https://travis-ci.org/dickschoeller/gedbrowser)
  builds
* ![](images/codeclimate-25.png)
  [Code Climate](https://codeclimate.com/github/dickschoeller/gedbrowser)
  static code analysis
* ![](images/codebeat-25.png)
  [Codebeat](https://codebeat.co/projects/github-com-dickschoeller-gedbrowser-development)
  static code analysis
* ![](images/coveralls-25.png)
  [Coveralls](https://coveralls.io/github/dickschoeller/gedbrowser)
  coverage analysis
* ![](images/saucelabs-25.png)
  [Sauce Labs](https://saucelabs.com/open_sauce/user/dickschoeller)
  cross-browser testing
* ![](images/versioneye-25.png)
  [VersionEye](https://www.versioneye.com/user/projects/58987dc1f55eb2003257f7bd?child=summary#tab-dependencies)
  dependency analysis

## Technology

* ![](images/spring-boot-25.png)
  [Spring Boot](http://projects.spring.io/spring-boot/)
* ![](images/thymeleaf-25.png)
  [Thymeleaf](http://www.thymeleaf.org/)
* ![](images/mongodb-25.png)
  [MongoDB](https://www.mongodb.org/)
* ![](images/docker-25.png)
  [Docker](https://www.docker.com/)
* ![](images/genealogy-net-25.png)
  [GEDCOM](http://wiki-en.genealogy.net/GEDCOM)
* ![](images/selenium-25.png)
  [Selenium WebDriver](http://www.seleniumhq.org/projects/webdriver/)

## Architecture

TBD

## License

Gedbrowser is Open Source software released under the
[Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html).
