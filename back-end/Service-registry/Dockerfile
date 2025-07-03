FROM eclipse-temurin:21-jdk-alpine
EXPOSE 8181
WORKDIR /Service-registry
COPY target/Service-registry-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]