FROM openjdk:11
ADD target/disheap_backend-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar" "disheap_backend-0.0.1-SNAPSHOT.jar"]