# gedbrowser

Spring Boot application to display the genealogy data from a [GEDCOM](http://wiki-en.genealogy.net/GEDCOM) file. Once loaded, the data is managed in a [MongoDB](https://www.mongodb.org/) database.

Check it out by perusing [my genealogy database](http://www.schoellerfamily.org/gedbrowser/surnames?db=schoeller). You can browse the database anonymously, but you will need a login with the appropriate role to see living people.

| Jenkins Build | Travis Build | Coverage | Dependencies | Selenium |
| :--- | :--- | :--- | :--- | :--- |
| [![Build Status](http://www.schoellerfamily.org/jenkins/buildStatus/icon?job=gedbrowser)](http://www.schoellerfamily.org/jenkins/job/gedbrowser/) | [![Build Status](https://api.travis-ci.org/dickschoeller/gedbrowser.svg?branch=master)](https://travis-ci.org/dickschoeller/gedbrowser) | [![Coverage Status](https://coveralls.io/repos/github/dickschoeller/gedbrowser/badge.svg?branch=master)](https://coveralls.io/github/dickschoeller/gedbrowser?branch=master) | [![Dependency Status](https://www.versioneye.com/user/projects/586bf6913ab148003228ac5d/badge.svg)](https://www.versioneye.com/user/projects/586bf6913ab148003228ac5d?child=summary#tab-dependencies) | [![Selenium Test Status](http://www.schoellerfamily.org/jenkins/buildStatus/icon?job=gedbrowser-selenium)](http://www.schoellerfamily.org/jenkins/job/gedbrowser-selenium) |

## Getting started

* Prerequisistes are a JDK, Maven, Git, and MongoDB
  * Optional Docker, which can be used to get MongoDB
* Clone this repository
* From the top 'mvn clean install'
* Place GEDCOM files in /var/lib/gedbrowser
* Create /var/lib/gedbrowser/userFile.csv rows are: username,firstname,lastname,email,password,role,role...
* java -jar gedbrowser/target/gedbrowser-1.1.0-SNAPSHOT.jar

Running with Docker requires running the MongoDB with Docker. The following command allows you to do this without conflicting ports with a native mongod service.

* docker run --rm -v /home/dick/data:/data/db --name mongo -p 28001:27017 -d mongo
* docker run --link mongo:mongo -v /var/lib/gedbrowser:/var/lib/gedbrowser -p 8086:8080 -p 8087:8081 --name geoservice -d schoellerfamily/geoservice
* docker run --link mongo:mongo -v /var/lib/gedbrowser:/var/lib/gedbrowser -p 8080:8080 -p 8081:8081 --name gedbrowser -d schoellerfamily/gedbrowser

As each GEDCOM file is referred to, it will be loaded into your instance of MongoDB. More explicit management
of data loading is planned for the future.

The location of gedbrowser.home defaults to /var/lib/gedbrowser. However that can be adjusted in
the file application.yml.

## Tooling

* ![](images/overvio.png) [Overv.io](https://overv.io/dickschoeller/gedbrowser/) task board
* ![](images/reviewninja-25.png) [ReviewNinja](https://app.review.ninja/dickschoeller/gedbrowser) code reviews
* ![](images/jenkins-25.png) [Jenkins](http://www.schoellerfamily.org/jenkins/) builds
* ![](images/travis-ci-25.png) [Travis CI](https://travis-ci.org/dickschoeller/gedbrowser) builds
* ![](images/coveralls-25.png) [Coveralls](https://coveralls.io/github/dickschoeller/gedbrowser) coverage analysis
* ![](images/versioneye-25.png) [VersionEye](https://www.versioneye.com/user/projects/586bf6913ab148003228ac5d?child=summary#tab-dependencies) dependency analysis

## Technology

* ![](images/spring-boot-25.png) [Spring Boot](http://projects.spring.io/spring-boot/)
* ![](images/thymeleaf-25.png) [Thymeleaf](http://www.thymeleaf.org/)
* ![](images/mongodb-25.png) [MongoDB](https://www.mongodb.org/)
* ![](images/docker-25.png) [Docker](https://www.docker.com/)
* ![](images/genealogy-net-25.png) [GEDCOM](http://wiki-en.genealogy.net/GEDCOM)
* ![](images/selenium-25.png) [Selenium WebDriver](http://www.seleniumhq.org/projects/webdriver/)

## Architecture

TBD

## License

Gedbrowser is Open Source software released under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html)
