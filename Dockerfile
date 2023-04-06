FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder
EXPOSE 8080
WORKDIR /build
#COPY . .
#RUN chmod 777 entrypoint.sh
RUN ls -l
RUN pwd
#COPY /target/communicatie-engine-backend-0.0.1-SNAPSHOT-spring-boot.jar communicatie-engine-backend.jar
COPY /target/communicatie-engine-backend-0.0.1-SNAPSHOT-jar-with-dependencies.jar communicatie-engine-backend.jar

RUN ls -l
RUN pwd

#RUN mvn install -DskipTests

#ENTRYPOINT ["/build/entrypoint.sh"]
CMD ["entrypoint.sh"]
#ENTRYPOINT ["java","-jar","/build/communicatie-engine-backend.jar"]
#CMD ["java -jar communicatie-engine-backend.jar"]