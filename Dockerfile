FROM openjdk:21-jdk-slim

WORKDIR /app

COPY language-trainer-1.0.1-SNAPSHOT.jar app.jar

#EXPOSE 8080
EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]
