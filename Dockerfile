FROM openjdk:11
VOLUME /test
COPY target/star-wars.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]