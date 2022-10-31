## Integrantes
Igor Carvalho e Osmane Aparecido

## Versão em C#
https://github.com/locdown2311/TrabalhoFinal/tree/trabalho-c%23
Essa versão está incompleta mas ao menos foi realizado a tentativa de adaptação ao C#, linguagem que os integrantes tem muito menos conhecimento.

## Biblioteca
A biblioteca criada em sala de aula e as adicionais usando lista de adjacência se encontram em /src

## Requer Docker ou Podman (31/10)
Com a configuração atual do programa, todos os testes foram executados utilizando um servidor virtual usando Docker

```
docker build -t dij-grafos .
docker run -it dij-grafos 
```

## Utilização do compila.sh
Ao chegar no diretório /src, utiliza-se

Para métodos de caminhos:

```
./compila.sh txt/cm/.. metodo
```
Metodos disponíveis: dij, bf, bfm, fw
Para solução de labirintos:

```
./compila.sh txt/maze/.. maze
```

Dentre os métodos, estão: maze
