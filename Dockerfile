FROM openjdk:17-jdk-slim
VOLUME /tmp
WORKDIR /app
COPY target/4TWIN1-G5-Kaddem-1.0.0.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","app.jar"]
