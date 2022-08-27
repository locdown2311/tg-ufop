import java.util.List;

class Main {
  public static void main(String[] args) {
    Graph g1 = new Graph(8);
    g1.addEdgeUnoriented(6, 3, 1);
    g1.addEdgeUnoriented(6, 7, 1);
    g1.addEdgeUnoriented(6, 1, 1);
    g1.addEdgeUnoriented(3, 4, 1);
    g1.addEdgeUnoriented(7, 0, 1);
    g1.addEdgeUnoriented(1, 2, 1);
    g1.addEdgeUnoriented(0, 5, 1);
    System.out.println(g1);

    List<Integer> buscalarg = g1.BuscaLarguraV2(2);
    List<Integer> buscaprof = g1.dfs(2);
    List<Integer> buscaprofRec = g1.descRec(2);

    System.out.println(buscalarg);
    System.out.println(buscaprof);
    System.out.println(buscaprofRec);
    /*
     * Graph complementar;
     * 
     * grafo.addEdge(0, 1, 3);
     * grafo.addEdge(1, 0, 3);
     * grafo.addEdge(0, 3, 4);
     * grafo.addEdge(3, 0, 4);
     * // grafo.addEdge(0, 4, 1);
     * System.out.println(grafo);
     * // System.out.println(grafo.degree(0));
     * // System.out.println(grafo.degree(1));
     * // System.out.println(grafo.degree(2));
     * // System.out.println(grafo.degree(3));
     * // System.out.println(grafo.highestDegree());
     * 
     * complementar = grafo.complement();
     * System.out.println(complementar.toString());
     * 
     * 
     * //Sub-grafos
     * Graph g2,g3,g4;
     * g2 = new Graph(4);
     * System.out.println("Densidade: ");
     * 
     * System.out.println(grafo.density());
     * System.out.println("Orientado? ");
     * System.out.println(grafo.oriented());
     * g3 = new Graph(4);
     * g4 = new Graph(4);
     */
  }
}