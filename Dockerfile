FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y \
    curl \
    && curl -fsSL https://get.docker.com -o get-docker.sh \
    && sh get-docker.sh \
    && rm get-docker.sh

WORKDIR /app

COPY build/libs/PTBOX_Challenge_for_Software_Developer-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
