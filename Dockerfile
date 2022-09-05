FROM amazoncorretto:18
COPY target/LibraryManager-0.0.1-SNAPSHOT.jar prog.jar
COPY src/main/resources/static src/main/resources/static
EXPOSE 5757
ENTRYPOINT ["java", "-jar", "prog.jar"]