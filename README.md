# gedbrowser

[![Build Status](https://api.travis-ci.org/dickschoeller/gedbrowser.svg?branch=development)](https://travis-ci.org/dickschoeller/gedbrowser)
[![Coverage Status](https://coveralls.io/repos/github/dickschoeller/gedbrowser/badge.svg?branch=development)](https://coveralls.io/github/dickschoeller/gedbrowser?branch=development)
[![Code Climate](https://codeclimate.com/github/dickschoeller/gedbrowser/badges/gpa.svg)](https://codeclimate.com/github/dickschoeller/gedbrowser)
[![codebeat badge](https://codebeat.co/badges/0a10c645-cc88-4f2f-9058-df89e8dc408f)](https://codebeat.co/projects/github-com-dickschoeller-gedbrowser-development)
[![Dependency Status](https://dependencyci.com/github/dickschoeller/gedbrowser/badge)](https://tidelift.com/repo/github/dickschoeller/gedbrowser)
[![Known Vulnerabilities](https://snyk.io/test/github/dickschoeller/gedbrowser/badge.svg)](https://snyk.io/org/dickschoeller)
[![Build Status](https://saucelabs.com/buildstatus/dickschoeller)](https://saucelabs.com/open_sauce/user/dickschoeller)

Spring Boot application to display the genealogy data from a
[GEDCOM](http://wiki-en.genealogy.net/GEDCOM) file. Once loaded, the data is
managed in a [MongoDB](https://www.mongodb.org/) database.

Check it out by perusing [my genealogy
database](http://www.schoellerfamily.org/gedbrowser/surnames?db=schoeller). You can
browse the database anonymously, but you will need a login with the appropriate
role to see living people.

## Installing

The easist way to run is to directly run the Docker images.

Running with Docker requires running the MongoDB with Docker. The following
commands allow you to do this without conflicting ports with a native MongoDB
service. Note that these commands will conflict with a running native Tomcat
server. Naturally, in order to this you have to install Docker.

The following steps follow after installing Docker and verifying that the service
is running.

### Create data directory for mongodb

```bash
mkdir ~/data
```

### Create home directory for gedbrowser

```bash
mkdir /var/lib/gedbrowser
```

### Put data files in home directory

```bash
cp *.ged /var/lib/gedbrowser
```

### Prepare Google Geocoding and Google Maps Keys

You will have to go over to Google's developer site and get your own keys.

```bash
cat YOURGEOCODINGKEY > /var/lib/gedbrowser/google-geocoding-key
cat YOURGOOGLEMAPKEY >> /var/lib/gedbrowser/google-geocoding-key
```

### Prepare your user file

The file sits in the gedbrowser home directory and is called userFile.csv. The
format is:   
username,first name,last name,email,password,role,role,...   
The supported roles are USER and ADMIN.

```bash
emacs /var/lib/gedbrowser/userFile.csv
```

### Assign environment variables

```bash
export DATA_DIR=~/data
export GEDBROWSER_HOME=/var/lib/gedbrowser
```

### Run the 3 required applications

You start with MongoDB, then geoservice and then gedbrowser.

```bash
docker run --rm -v ${DATA_DIR}:/data/db --name mongo -p 28001:27017 -d mongo
docker run --link mongo:mongo -v ${GEDBROWSER_HOME}:/var/lib/gedbrowser -p  8086:8080 -p 8087:8081 --name geoservice -d dickschoeller/geoservice
docker run --link geoservice:geoservice --link mongo:mongo -v ${GEDBROWSER_HOME}:/var/lib/gedbrowser -p 8080:8080 -p 8081:8081 --name gedbrowser -d dickschoeller/gedbrowser
```

Once this is complete, gedbrowser should be up and running and reachable at port
8080 from your browser. See Docker documentation to change ports or to
distribute the services across different hosts. See your webrowser documentation
if you intend to run this behind a webserver.

As users refer to each of the GEDCOM files, they will be loaded into your
instance of MongoDB. Subsequent access will be from the database and not from
the GEDCOM file. There is a reload function on the management port if you wish
to reset the database from the files.

## Getting started developing

* Prerequisites are a JDK, Maven, Git, and MongoDB
  * Optional Docker, which can be used to get MongoDB as well as to run
    gedbrowsser
  * IDE of your choice. We prefer [Eclipse](https://eclipse.org).
* Follow the installation instruction regarding preparing the gedbrowser home
  directory
* Clone this repository and cd into it
* From the top 'mvn clean install'
* java -jar gedbrowser/target/gedbrowser-1.3.0-M5.jar or run from your IDE

The location of gedbrowser.home defaults to /var/lib/gedbrowser. However that
can be adjusted in the file application.yml. When running in Docker the data
file can be redirected where you want from the run command.

You are now ready to try making changes and pull requests. :smile:

## Tooling and Development Operations

We are big fans of DevOps, tooling to automate as many processes as can be done.
Take a look at our
[Development Operations](https://github.com/dickschoeller/gedbrowser/wiki/Development-Operations)
wiki page to get an idea of what tools we are using, where we stand on their use
and some ideas of how much further we have to go.

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

