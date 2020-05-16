FROM java:8
VOLUME /tmp
ADD geoservice-1.3.0-RC1.jar geoservice.jar
EXPOSE 8080
EXPOSE 8081
ENV spring.data.mongodb.host=mongo
RUN bash -c 'touch /geoservice.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/geoservice.jar"]
