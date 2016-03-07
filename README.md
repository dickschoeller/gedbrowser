# gedbrowser

Web application to display the genealogy data from a GEDCOM file.

A running example can be accessed at
http://www.schoellerfamily.org/gedbrowser/surnames?db=schoeller&letter=A

## Getting started

* clone this repository
* from the top 'mvn clean install'
* place GEDCOM files in /var/lib/gedbrowser
* create /var/lib/gedbrowser/userFile.csv rows are: username,firstname,lastname,email,password,role,role...
* deploy WAR file to application server

As each GEDCOM file is referred to, it will be loaded into your instance of MongoDB. More explicit management
of data loading is planned for the future.

Note that the location of gedbrowser.home defaults to /var/lib/gedbrowser. However that can be adjusted in
the file application.yml.

## Tooling

* ![](overvio.png) [Overv.io](https://overv.io/workspace/dickschoeller/comfortable-seahorse/board/) task board
* ![](reviewninja-25.png) [ReviewNinja](https://app.review.ninja/dickschoeller/gedbrowser) code reviews

## Technology

* ![](spring-25.png) [Spring](https://spring.io/)
* ![](mongodb-25.png) [MongoDB](https://www.mongodb.org/)
* ![](thymeleaf-25.png) [Thymeleaf](http://www.thymeleaf.org/)
* ![](genealogy-net-25.png) [GEDCOM](http://wiki-en.genealogy.net/GEDCOM)
