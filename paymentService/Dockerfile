FROM openjdk:17
VOLUME /tmp
ADD ./target/paymentService-0.0.1-SNAPSHOT.jar payment-service.jar
ENTRYPOINT ["java", "-jar", "payment-service.jar"]


