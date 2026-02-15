FROM eclipse-temurin:21-jre

LABEL maintainer="akseleumaksat"

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]