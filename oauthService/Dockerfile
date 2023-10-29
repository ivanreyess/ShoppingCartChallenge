FROM openjdk:17
VOLUME /tmp
EXPOSE 9100
ADD ./target/oauthService-0.0.1-SNAPSHOT.jar oauth-service.jar
ENTRYPOINT ["java", "-jar", "oauth-service.jar"]
