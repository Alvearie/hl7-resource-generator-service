FROM openjdk:8-jdk-alpine

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar /app/hl7-resource-generator-service.jar
RUN chmod +x /app/hl7-resource-generator-service.jar

ENTRYPOINT ["java", "-jar", "/app/hl7-resource-generator-service.jar"]