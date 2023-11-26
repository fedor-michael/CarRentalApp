##FROM amazoncorretto:17.0.7-alpine
#FROM maven:3.8.5-openjdk-17
#WORKDIR /carrentalapp
#COPY . .
#RUN mvn clean install -DskipTests
#COPY /target/CarRentalApp-0.0.1-SNAPSHOT.jar app.jar
##CMD mvn spring-boot:run
##dzialajace:
##CMD ["java", "-jar", "app.jar"]
#ENTRYPOINT ["./wait-for-it.sh", "db:3306", "--", "java", "-jar", "app.jar"]
##FROM maven:3.8.5-openjdk-17 AS build
##COPY src /home/app/src
##COPY pom.xml /home/app
##RUN mvn -f /home/app/pom.xml clean package
##EXPOSE 8080
##ENTRYPOINT ["java","-jar","/home/app/target/CarRentalApp-0.0.1-SNAPSHOT.jar"]