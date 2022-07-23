FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app/user-service

RUN apk update && apk --no-cache add curl

COPY target/user-service-*.jar user-service.jar
ENTRYPOINT ["java","-jar","user-service.jar"]
ENV HEALTHCHECK_URL=http://localhost:8092/actuator/health


HEALTHCHECK --interval=10s --timeout=3s --retries=2 CMD curl --fail --silent ${HEALTHCHECK_URL} || exit 1