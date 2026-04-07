FROM gradle:8.14.4-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/build/libs/user-0.0.1-SNAPSHOT.jar /app/user.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/user.jar"]