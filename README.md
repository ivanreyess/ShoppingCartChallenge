# Shopping cart Challenge

Sample backend application for shopping cart based on *Spring boot* using microservice architecture.

## Directory Structure
```bash
├── eureka-server
├── gateway-service
├── oauth-service
├── order-detail-service
├── order-service
├── payment-service
├── product-service
├── user-commons-service
├── build.sh
├── docker-compose.yml
```



#### eureka-server :star:
This directory contains the service discovery microservice.

#### gateway :star:
Contains the gateway microservice and also provides security for the other microservices.

#### oauth-service
Contains the oauth microservice for token generation and user authentication.

#### order-detail-service
Contains order-details-service that provides info on every product purchased.

#### order-service
Contains order microservice that deals with order CRUD operations.

#### payment-service
Contains the payment microservice for dealing with payments.

#### product-service
Contains the product microservice that provides information and details about products.

#### user-commnons-service
Contains user library that is used in different microservices.


### build.sh
script for building the projects and run them on containers.


### docker-compose.yml
docker-compose file to run all microservices.

### Requirements

* jdk 17
* docker
* docker-compose

### Running  the application

To run the application  you can manually build all the projects and run them individually or executing the scripts provided inside this project there is a build.sh file, do not forget to make them executable in order to run it.

```
chmod +x build.sh
chmod +x start.sh
chmod +x stop.sh
```

  * build.sh: Builds all the projects and then deploys them using docker-compose.
  * stop.sh: Stop all docker containers with one script.
  * start.sh: Start all containers previously built.

To execute the desired script you can copy and paste on your terminal.

```
./build.sh
./start.sh
./stop.sh
```

### Consuming APIs

* Main entry point is on http://localhost:8090/
* All microservices are protected except for products on http://localhost:8090/api/products
* Order details entry point: http://localhost:8090/api/order-details
* Order entry point: http://localhost:8090/api/orders
* Payment details entry point: http://localhost:8090/api/payments
* User entrypoint: http://localhost:8090/api/users/

* Obtain token to make authorized calls to microservices, you can use postman to do the call, on Authorization tab, choose Basic Auth(**Username:** frontendapp, **Password:** 12345). On Body tab x-www-form-urlencoded option, **username:** ivan, **password:** 12345, **grant_type:** password : POST call to http://localhost:8090/api/oauth/token
