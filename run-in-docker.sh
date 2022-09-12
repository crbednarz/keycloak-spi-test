#!/bin/bash

DIR=$(dirname -- "`readlink -f -- "$0";`")
set -ex
mvn clean package
docker run \
    -e KEYCLOAK_USER=admin \
    -e KEYCLOAK_PASSWORD=password \
    -p 8080:8080 \
    -p 9990:9990 \
    -v $DIR/target/openlogic-spi-demo.jar:/opt/jboss/keycloak/standalone/deployments/openlogic-spi-demo.jar \
    --rm -it \
    jboss/keycloak:16.1.0
