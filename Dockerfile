FROM --platform=$TARGETPLATFORM gradle:8.14.3-jdk17 AS build

WORKDIR /app

COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/

RUN chmod +x /app/gradlew

COPY src /app/src

RUN gradle --no-daemon build -x test

FROM --platform=$TARGETPLATFORM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
