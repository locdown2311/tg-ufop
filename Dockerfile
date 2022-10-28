FROM openjdk:8-jdk-alpine
COPY . /usr/src/grafos
WORKDIR /usr/src/grafos/src
ENTRYPOINT [ "./compila.sh" ]