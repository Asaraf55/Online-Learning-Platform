FROM eclipse-temurin:21-jdk-alpine
EXPOSE 8082
WORKDIR /course-service
COPY target/course-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]