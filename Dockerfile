FROM openjdk:11
ADD target/microservice-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar" "microservice-0.0.1-SNAPSHOT.jar"]