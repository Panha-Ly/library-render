FROM maven:3.9.9-amazoncorretto-21-debian-bookworm AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:21.0.5-al2023
WORKDIR /app
COPY --from=build /app/target/library-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
