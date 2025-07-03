FROM openjdk:21
EXPOSE 8084
WORKDIR /Feedback-Service
COPY target/Feedback-Service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]