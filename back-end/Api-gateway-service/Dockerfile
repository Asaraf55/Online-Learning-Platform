FROM eclipse-temurin:21-jdk-alpine
EXPOSE 8080
WORKDIR /Api-gateway-service
COPY target/Api-gateway-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]