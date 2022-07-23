FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app/order-service

RUN apk update && apk --no-cache add curl

COPY target/order-service-*.jar order-service.jar
ENTRYPOINT ["java","-jar","order-service.jar"]
ENV HEALTHCHECK_URL=http://localhost:8080/actuator/health


HEALTHCHECK --interval=10s --timeout=3s --retries=2 CMD curl --fail --silent ${HEALTHCHECK_URL} || exit 1