FROM openjdk:11
COPY . /usr/src/grafos
COPY compila.sh /usr/src/grafos
WORKDIR /usr/src/grafos/src
ENTRYPOINT [ "./compila.sh" ]