# Imagine oficială cu OpenJDK 21
FROM openjdk:21-jdk-slim

# Setăm directorul de lucru
WORKDIR /app

# Copiem fișierele și construim aplicația
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copiem codul sursă și îl compilăm
COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests


# Expunem portul 8080
EXPOSE 8080

# Comanda de rulare
CMD ["java", "-jar", "target/crm-0.0.1-SNAPSHOT.jar"]

