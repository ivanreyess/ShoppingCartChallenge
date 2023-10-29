FROM openjdk:17
VOLUME /tmp
ADD ./target/productService-0.0.1-SNAPSHOT.jar product-service.jar
ENTRYPOINT ["java", "-jar", "product-service.jar"]


