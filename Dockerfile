FROM openjdk:11
COPY . /usr/src/grafos
COPY compila.sh /usr/src/grafos/src
WORKDIR /usr/src/grafos/src
ENTRYPOINT [ "/bin/bash" ]