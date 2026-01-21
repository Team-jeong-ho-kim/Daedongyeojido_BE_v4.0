FROM gradle:8.14.3-jdk17 AS build

WORKDIR /app

COPY gradlew ./
COPY gradle/wrapper ./gradle/wrapper
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew
RUN --mount=type=cache,target=/home/gradle/.gradle \
    gradle --no-daemon dependencies

COPY src ./src

RUN --mount=type=cache,target=/home/gradle/.gradle \
    gradle --no-daemon build -x test

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
