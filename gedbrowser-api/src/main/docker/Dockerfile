FROM java:8
VOLUME /tmp
ADD gedbrowser-api-1.3.0-M2-SNAPSHOT.jar gedbrowser-api.jar
EXPOSE 8080
EXPOSE 8081
ENV spring.data.mongodb.host=mongo
RUN bash -c 'touch /gedbrowser-api.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/gedbrowser-api.jar"]
