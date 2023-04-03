FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder
WORKDIR /build
COPY . .
RUN ls -l
#RUN mvn clean package -DskipTests
 
FROM eclipse-temurin:17.0.5_8-jre-alpine
WORKDIR /opt/app
RUN addgroup --system javauser && adduser -S -s /usr/sbin/nologin -G javauser javauser
COPY --from=builder . .
RUN ls -l
RUN chown -R javauser:javauser .
USER javauser

#ENTRYPOINT ["java","-jar","communicatie-engine-backend.jar"]
ENTRYPOINT ["/opt/app/mvn","spring-boot:run"]
