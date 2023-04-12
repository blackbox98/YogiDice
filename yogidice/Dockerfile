FROM openjdk:8-jdk-alpine as build-stage

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# Deploy Stage
FROM openjdk:8-jdk-alpine

WORKDIR /var/jenkins_home/workspace/yogidicedeploy/yogidice
# ARG JAR_FILE=build/libs/*.jar
# COPY ${JAR_FILE} app.jar
COPY --from=build-stage build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/var/jenkins_home/workspace/yogidicedeploy/yogidice/app.jar" ]
