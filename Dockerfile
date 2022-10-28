FROM openjdk:11
COPY . /usr/src/grafos
COPY compila.sh /usr/src/grafos/src
RUN chmod +x /usr/src/grafos/src/compila.sh
WORKDIR /usr/src/grafos/src
ENTRYPOINT [ "/bin/bash" ]