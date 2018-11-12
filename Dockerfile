FROM openjdk:8-jdk-alpine

RUN mkdir /app
WORKDIR /app

COPY target/vibeville-user-registration-1.0-SNAPSHOT.jar vibeville-user-registration.jar
EXPOSE 8080

CMD ["java", "-jar", "vibeville-user-registration.jar"]
