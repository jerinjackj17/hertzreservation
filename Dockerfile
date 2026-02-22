#use lightweight jdk image with Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app
#Copy the built JAR file into the container
COPY target/hertzreservation-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
