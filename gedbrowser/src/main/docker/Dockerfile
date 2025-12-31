FROM eclipse-temurin:25
VOLUME /tmp
ADD gedbrowser-1.3.0-RC3-SNAPSHOT.jar gedbrowser.jar
ADD config/letsencrypt.cer letsencrypt.cer
EXPOSE 8080
EXPOSE 8081
ENV spring.data.mongodb.host=mongo
ENV geoservice.host=geoservice
ENV geoservice.port=8080
ENV USE_SYSTEM_CA_CERTS=true
RUN bash -c 'touch /gedbrowser.jar'
COPY config/letsencrypt.cer /usr/local/share/ca-certificates
COPY config/letsencrypt.cer /usr/local/share/certificates
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/gedbrowser.jar"]
