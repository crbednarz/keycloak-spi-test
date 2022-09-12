FROM jboss/keycloak:16.1.0

COPY ./target/openlogic-spi-demo.jar /opt/jboss/keycloak/standalone/deployments/
