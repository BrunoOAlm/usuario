FROM gradle:8.14-jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/usuario.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/usuario.jar"]