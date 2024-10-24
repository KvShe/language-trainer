# Используем базовый образ с JDK 21
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл jar приложения в контейнер
# COPY target/myapp.jar app.jar
COPY target/language-trainer-1.0.0-SNAPSHOT.jar app.jar

# Открываем порт, на котором будет работать приложение
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
