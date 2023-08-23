FROM amazoncorretto:11-alpine-jdk
COPY target/post-office-0.0.1-SNAPSHOT.jar post-office.jar
ENTRYPOINT ["java","-jar","/post-office.jar"]