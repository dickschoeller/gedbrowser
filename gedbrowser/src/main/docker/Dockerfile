FROM openjdk:8
VOLUME /tmp
ADD gedbrowser-1.3.0-RC3-SNAPSHOT.jar gedbrowser.jar
ADD config/letsencrypt.cer letsencrypt.cer
EXPOSE 8080
EXPOSE 8081
ENV spring.data.mongodb.host=mongo
ENV geoservice.host=geoservice
ENV geoservice.port=8080
RUN bash -c 'touch /gedbrowser.jar'
RUN echo "yes" | keytool -import -trustcacerts -file letsencrypt.cer -alias letsencrypt -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/gedbrowser.jar"]
