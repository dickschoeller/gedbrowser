# gedbrowser

Web application to display the genealogy data from a GEDCOM file.

Check it out by perusing [my genealogy database](http://www.schoellerfamily.org/gedbrowser/surnames?db=schoeller). You can browse the database anonymously, but you will need a login with the appropriate role to see living people.

## Getting started

* Prerequisistes are a JDK, Maven, Git, and MongoDB
* Clone this repository
* From the top 'mvn clean install'
* Place GEDCOM files in /var/lib/gedbrowser
* Create /var/lib/gedbrowser/userFile.csv rows are: username,firstname,lastname,email,password,role,role...
* Deploy WAR file to application server

As each GEDCOM file is referred to, it will be loaded into your instance of MongoDB. More explicit management
of data loading is planned for the future.

Note that the location of gedbrowser.home defaults to /var/lib/gedbrowser. However that can be adjusted in
the file application.yml.

## Tooling

* ![](overvio.png) [Overv.io](https://overv.io/workspace/dickschoeller/comfortable-seahorse/board/) task board
* ![](reviewninja-25.png) [ReviewNinja](https://app.review.ninja/dickschoeller/gedbrowser) code reviews
* ![](jenkins-25.png) [Jenkins](http://www.schoellerfamily.org/jenkins/) builds

## Technology

* ![](spring-25.png) [Spring](https://spring.io/)
* ![](mongodb-25.png) [MongoDB](https://www.mongodb.org/)
* ![](thymeleaf-25.png) [Thymeleaf](http://www.thymeleaf.org/)
* ![](genealogy-net-25.png) [GEDCOM](http://wiki-en.genealogy.net/GEDCOM)
* ![](selenium-25.png) [Selenium WebDriver](http://www.seleniumhq.org/projects/webdriver/)

