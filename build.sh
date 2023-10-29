#!/bin/bash
echo $PWD
cd userCommonsService
./mvnw clean install
cd ..
cd eurekaServer
./mvnw clean install
cd ..
cd gatewayService
./mvnw clean install
cd ..
cd oauthService
./mvnw clean install
cd ..
cd orderDetailService
./mvnw clean install
cd ..
cd orderService
./mvnw clean install
cd ..
cd paymentService
./mvnw clean install
cd ..
cd userService
./mvnw clean install
cd ..
docker-compose up -d