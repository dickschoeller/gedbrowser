FROM java:8
VOLUME /tmp
ADD gedbrowserng-1.3.0-M3.jar gedbrowserng.jar
EXPOSE 8080
EXPOSE 8081
ENV spring.data.mongodb.host=mongo
RUN bash -c 'touch /gedbrowserng.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/gedbrowserng.jar"]
