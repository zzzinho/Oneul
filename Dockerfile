FROM adoptopenjdk:11-jdk-hotspot
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} oneul.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/oneul.jar"]
