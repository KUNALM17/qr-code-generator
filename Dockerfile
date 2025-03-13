# Stage 1: Build the app with Maven
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

# Stage 2: Run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
