FROM openjdk:17 as builder
LABEL maintainer="Brayan Martinez (brayanmartinez827@gmail.com)"

ENV WORKDIR_BASE=/home/application

EXPOSE 8080:8080

WORKDIR $WORKDIR_BASE

COPY ./target/Java-SpringBoot-WebFlux-0.0.1-SNAPSHOT.jar Java-SpringBoot-WebFlux-0.0.1-SNAPSHOT.jar

ENTRYPOINT java -jar Java-SpringBoot-WebFlux-0.0.1-SNAPSHOT.jar

