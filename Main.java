class Main {
  public static void main(String[] args) {
    Graph grafo = new Graph(4);
    Graph complementar;

    grafo.addEdge(0, 1, 3);
    grafo.addEdge(1, 0, 3);
    grafo.addEdge(0, 3, 4);
    grafo.addEdge(3, 0, 4);
    // grafo.addEdge(0, 4, 1);
    System.out.println(grafo);
    // System.out.println(grafo.degree(0));
    // System.out.println(grafo.degree(1));
    // System.out.println(grafo.degree(2));
    // System.out.println(grafo.degree(3));
    // System.out.println(grafo.highestDegree());

    complementar = grafo.complement();
    System.out.println(complementar.toString());


    //Sub-grafos
    Graph g2,g3,g4;
    g2 = new Graph(4);
    g3 = new Graph(4);
    g4 = new Graph(4);
  }
}