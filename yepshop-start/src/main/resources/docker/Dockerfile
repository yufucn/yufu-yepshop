#FROM openjdk:8-jdk-alpine
FROM openjdk:8u181-jre-slim-stretch
VOLUME /tmp
ARG JAR_FILE
COPY *.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

