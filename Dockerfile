FROM openjdk:21-jdk-slim

WORKDIR /app

COPY language-trainer-*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]
