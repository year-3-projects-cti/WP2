FROM openjdk:21-jdk-slim

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests


EXPOSE 8080

CMD ["java", "-jar", "target/crm-0.0.1-SNAPSHOT.jar"]

