FROM gradle:8.14.3-jdk17 AS build

WORKDIR /app

COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/

RUN chmod +x /app/gradlew

RUN /app/gradlew --no-daemon dependencies

COPY src /app/src

RUN /app/gradlew --no-daemon build -x test --parallel --continue

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
