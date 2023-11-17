FROM amazoncorretto:17.0.7-alpine
WORKDIR /springboot-carrentalapp
EXPOSE 8080
COPY target/CarRentalApp-0.0.1-SNAPSHOT.jar CarRentalApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/CarRentalApp-0.0.1-SNAPSHOT.jar"]
