# Utilise l'image Java officielle
FROM openjdk:17-jdk-slim

# Crée un répertoire pour l'app
WORKDIR /app

# Copie le .jar généré par Maven dans l'image
COPY target/*.jar app.jar

# Expose le port utilisé par Spring Boot
EXPOSE 8089

# Point d’entrée : exécute l’application
ENTRYPOINT ["java", "-jar", "app.jar"]
