FROM eclipse-temurin:17.0.5_8-jre-alpine
EXPOSE 8080
WORKDIR /opt/app
RUN addgroup --system javauser && adduser -S -s /usr/sbin/nologin -G javauser javauser
RUN ls -l
COPY target/communicatie-engine-backend-0.0.1-SNAPSHOT.jar communicatie-engine-backend.jar
RUN chown -R javauser:javauser .
USER javauser
RUN ls -l
RUN pwd

ENTRYPOINT ["java","-jar","/opt/app/communicatie-engine-backend.jar"]
