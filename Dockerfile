FROM openjdk:8-jdk-alpine
ARG FILE_NAME
ARG METHOD
COPY . /usr/src/grafos
WORKDIR /usr/src/grafos/src
RUN javac App.java
CMD ["java", "App", ${FILE_NAME}, ${METHOD}]