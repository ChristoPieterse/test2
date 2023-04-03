FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests
 
FROM eclipse-temurin:17.0.5_8-jre-alpine
WORKDIR /opt/app
RUN addgroup --system javauser && adduser -S -s /usr/sbin/nologin -G javauser javauser
COPY --from=builder /build/target/communicatie-engine-backend-0.0.1-SNAPSHOT.jar /communicatie-engine-backend.jar
RUN chown -R javauser:javauser .
USER javauser
RUN ls -l
RUN pwd
ENTRYPOINT ["java","-jar","communicatie-engine-backend.jar"]
