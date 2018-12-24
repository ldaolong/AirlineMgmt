FROM openjdk:10-jre-slim
MAINTAINER darren

COPY target/darren*.jar /app.jar

CMD ["java", \
    "-agentlib:jdwp=transport=dt_socket,address=8000,suspend=n,server=y", \
    "-Duser.timezone=UTC", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", \
    "/app.jar"]
