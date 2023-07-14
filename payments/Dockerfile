FROM eclipse-temurin:17-jdk-alpine
LABEL authors="gdesouzacrispim"
VOLUME /tmp
COPY target/payments-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]