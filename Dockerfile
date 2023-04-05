FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder
EXPOSE 8080
WORKDIR /build
COPY . .
RUN chmod 777 entrypoint.sh

#RUN mvn install -DskipTests

ENTRYPOINT ["entrypoint.sh"]

