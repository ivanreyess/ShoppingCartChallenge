FROM openjdk:17
VOLUME /tmp
EXPOSE 8090
ADD ./target/gatewayService-0.0.1-SNAPSHOT.jar gateway.jar
ENTRYPOINT ["java", "-jar", "gateway.jar"]


