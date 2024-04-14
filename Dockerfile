# openjdk:8 image is available in docker hub
FROM openjdk:8

# make port 8080 available outside the container
EXPOSE 8080

ADD target/*.jar book-app.jar

ENTRYPOINT ["java", "-jar", "/book-app.jar"]