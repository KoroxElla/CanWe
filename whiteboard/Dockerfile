FROM eclipse-temurin:21-jre

WORKDIR /app

# copy the jar that Gradle just built
COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
