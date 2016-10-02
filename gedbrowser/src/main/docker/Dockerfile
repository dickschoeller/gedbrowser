FROM java:8
VOLUME /tmp
ADD gedbrowser-1.1.0-SNAPSHOT.jar gedbrowser.jar
EXPOSE 8080
ENV spring.data.mongodb.host=mongo
RUN bash -c 'touch /gedbrowser.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/gedbrowser.jar"]
