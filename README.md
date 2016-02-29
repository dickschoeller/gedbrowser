# gedbrowser

Web application to display the genealogy data from a GEDCOM file.

A running example can be accessed at
http://www.schoellerfamily.org/gedbrowser/surnames?db=schoeller&letter=A

## Getting started

* clone this repository
* from the top 'mvn clean install'
* deploy WAR file to application server
* place GEDCOM files in /var/lib/gedbrowser
* create /var/lib/gedbrowser/userFile.csv rows are: username,firstname,lastname,email,password,role,role...

## Tooling

* ![](overvio.png) [Overv.io](https://overv.io/workspace/dickschoeller/comfortable-seahorse/board/) task board
* [ReviewNinja](https://app.review.ninja/dickschoeller/gedbrowser) code reviews

## Technology

* Spring
* MongoDB
* Thymeleaf
