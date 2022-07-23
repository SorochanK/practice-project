FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app/product-service

RUN apk update && apk --no-cache add curl

COPY target/product-service-*.jar product-service.jar
ENTRYPOINT ["java","-jar","product-service.jar"]
ENV HEALTHCHECK_URL=http://localhost:8091/actuator/health


HEALTHCHECK --interval=10s --timeout=3s --retries=2 CMD curl --fail --silent ${HEALTHCHECK_URL} || exit 1