#!/bin/bash
echo 'Stopping services'

docker container stop $(docker ps -a | grep oauth-service:v1 | awk '{print $1}')
docker container stop $(docker ps -a | grep user-service:v1 | awk '{print $1}')
docker container stop $(docker ps -a | grep order-service:v1 | awk '{print $1}')
docker container stop $(docker ps -a | grep order-detail-service:v1 | awk '{print $1}')
docker container stop $(docker ps -a | grep payment-service:v1 | awk '{print $1}')
docker container stop $(docker ps -a | grep product-service:v1 | awk '{print $1}')
docker container stop $(docker ps -a | grep gateway-service:v1 | awk '{print $1}')
docker container stop $(docker ps -a | grep eureka-server:v1 | awk '{print $1}')

echo 'Services stopped'

