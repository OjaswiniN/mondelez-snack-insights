FROM openjdk:17-jdk-slim

# Add run.sh
ADD run.sh /bin/run.sh
RUN chmod +x /bin/run.sh

# Copying jar
VOLUME /tmp
RUN mkdir /opt/app
COPY build/libs/*-SNAPSHOT.jar /opt/app/app.jar

ENTRYPOINT ["/bin/run.sh"]