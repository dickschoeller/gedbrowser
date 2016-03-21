# gedbrowser

Spring Boot application to display the genealogy data from a [GEDCOM](http://wiki-en.genealogy.net/GEDCOM) file. Once loaded, the data is managed in a [MongoDB](https://www.mongodb.org/) database.

Check it out by perusing [my genealogy database](http://www.schoellerfamily.org/gedbrowser/surnames?db=schoeller). You can browse the database anonymously, but you will need a login with the appropriate role to see living people.

## Getting started

* Prerequisistes are a JDK, Maven, Git, and MongoDB
  * Optional Docker, which can be used to get MongoDB
* Clone this repository
* From the top 'mvn clean install'
* Place GEDCOM files in /var/lib/gedbrowser
* Create /var/lib/gedbrowser/userFile.csv rows are: username,firstname,lastname,email,password,role,role...
* java -jar gedbrowser/target/gedbrowser-1.0.0-SNAPSHOT.jar

Running with Docker requires running the MongoDB with Docker. The following command allows you to do this without conflicting ports with a native mongod service.

* docker run --rm -v /home/dick/data:/data/db --name mongo -p 28001:27017 -d mongo
* docker run --link mongo:mongo -v /var/lib/gedbrowser:/var/lib/gedbrowser -p 8080:8080 --name gedbrowser -d schoellerfamily/gedbrowser

As each GEDCOM file is referred to, it will be loaded into your instance of MongoDB. More explicit management
of data loading is planned for the future.

Note that the location of gedbrowser.home defaults to /var/lib/gedbrowser. However that can be adjusted in
the file application.yml.

## Tooling

* ![](overvio.png) [Overv.io](https://overv.io/workspace/dickschoeller/comfortable-seahorse/board/) task board
* ![](reviewninja-25.png) [ReviewNinja](https://app.review.ninja/dickschoeller/gedbrowser) code reviews
* ![](jenkins-25.png) [Jenkins](http://www.schoellerfamily.org/jenkins/) builds

## Technology

* ![](spring-boot-25.png) [Spring Boot](http://projects.spring.io/spring-boot/)
* ![](thymeleaf-25.png) [Thymeleaf](http://www.thymeleaf.org/)
* ![](mongodb-25.png) [MongoDB](https://www.mongodb.org/)
* ![](docker-25.png) [Docker](https://www.docker.com/)
* ![](genealogy-net-25.png) [GEDCOM](http://wiki-en.genealogy.net/GEDCOM)
* ![](selenium-25.png) [Selenium WebDriver](http://www.seleniumhq.org/projects/webdriver/)

## Architecture

TBD
