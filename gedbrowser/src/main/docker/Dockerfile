FROM java:8
VOLUME /tmp
ADD gedbrowser-1.1.1-SNAPSHOT.jar gedbrowser.jar
EXPOSE 8080
EXPOSE 8081
ENV spring.data.mongodb.host=mongo
ENV geoservice.host=geoservice
ENV geoservice.port=8080
RUN bash -c 'touch /gedbrowser.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/gedbrowser.jar"]
