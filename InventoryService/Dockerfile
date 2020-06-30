FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/InventoryService-0.0.1-SNAPSHOT.jar InventoryService.jar
ENTRYPOINT ["java" ,"-jar" , "/InventoryService.jar"]