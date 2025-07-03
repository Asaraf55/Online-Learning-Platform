FROM eclipse-temurin:21-jdk-alpine
EXPOSE 8081
WORKDIR /student-service
COPY target/student-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]