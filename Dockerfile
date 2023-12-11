FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests
FROM maven:3.8.5-openjdk-17
WORKDIR /app
COPY --from=build /app/target/carrentalapp.jar /app/carrentalapp.jar
EXPOSE 8080
CMD ["java", "-jar", "carrentalapp.jar"]