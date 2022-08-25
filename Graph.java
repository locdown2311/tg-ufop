import java.util.List;
import java.util.ArrayList;

class Graph {
  //
  private int _countNodes;
  private int _countEdges;
  private int[][] _adjMatrix;

  public Graph(int numNodes) {
    this._countNodes = numNodes;
    this._countEdges = 0;
    this._adjMatrix = new int[numNodes][numNodes];
  }

  public void addEdge(int source, int sink, int weight) {
    if (source < 0 || source > this._adjMatrix.length - 1 ||
        sink < 0 || sink > this._adjMatrix.length - 1 ||
        weight <= 0) {
      System.err.printf("Invalid edge: %d %d %d\n", source, sink, weight);
      return;
    }
    this._countEdges++;
    this._adjMatrix[source][sink] = weight;
  }

  public void addEdgeUnoriented(int source, int sink, int weight) {
    if (source < 0 || source > this._adjMatrix.length - 1 ||
        sink < 0 || sink > this._adjMatrix.length - 1 ||
        weight <= 0) {
      System.err.printf("Invalid edge: %d %d %d\n", source, sink, weight);
      return;
    }
    this._countEdges += 2;
    this._adjMatrix[source][sink] = weight;
    this._adjMatrix[sink][source] = weight;
  }

  public int degree(int node) {
    int grau = 0;
    for (int j = 0; j < this._adjMatrix[node].length; ++j) {
      if (this._adjMatrix[node][j] != 0) {
        ++grau;
      }
    }
    return grau;
  }

  public List<Integer> BuscaLargura(Graph pesquisa,int origem){
    List<Integer> q = new ArrayList<Integer>();
    List<Integer> r = new ArrayList<Integer>();
    int desc[] = new int[pesquisa._countNodes] ;
//    for(int v = 0;v<pesquisa._adjMatrix[v].length;++v){
//      desc[v] = 0;
//    }
    q.add(origem);
    r.add(origem);
    desc[origem] = 1;

    while(!q.isEmpty()){
      int u = q.remove(0);
      for(int v = 0;v<_adjMatrix[u].length;++v){
        if(this._adjMatrix[u][v] != 0){ //verifica adjacente
          if(desc[v]  == 0){
            q.add(v);
            r.add(v);
            desc[v] = 1;
          }
        }
      }
    }
    return r;
  }

  public int highestDegree() {
    int highest = 0;
    for (int i = 0; i < this._adjMatrix.length; ++i) {
      int maiorGrau = degree(i);
      if (maiorGrau > highest)
        highest = maiorGrau;
    }
    return highest;
  }

  public int lowestDegree() {
    int menorGrau = 50000;
    for (int i = 0; i < this._adjMatrix.length; i++) {
      if (menorGrau > degree(i)) {
        menorGrau = degree(i);
      }
    }
    return menorGrau;
  }

  public Graph complement() {
    // O que falta para o grafo ficar completo
    Graph copia = new Graph(this._countNodes);
    for (int i = 0; i < this._adjMatrix.length; ++i) {
      for (int j = 0; j < this._adjMatrix[i].length; ++j) {
        if (this._adjMatrix[i][j] == 0 && i != j) {
          copia.addEdge(i, j, 1);
        }
      }
    }
    return copia;
  }

  public boolean subGraph(Graph g2) {

    Graph g1 = this;

    if (g2._countNodes > g1._countNodes || g2._countEdges > g1._countEdges) {
      return false;
    }
    for (int i = 0; i < g1._adjMatrix.length; ++i) {
      for (int j = 0; j < g1._adjMatrix[i].length; ++j) {
        if (g2._adjMatrix[i][j] != 0 && g1._adjMatrix[i][j] == 0) {
          return false;
        }
      }
    }
    return true;
  }

  public float density() {
    float densidade = Math.abs(this._countEdges) / Math.abs(this._countNodes) * (Math.abs(this._countNodes) - 1);
    return densidade;
  }

  public boolean oriented() {

    for (int i = 0; i < this._adjMatrix.length; i++) {
      for (int j = 0; j < this._adjMatrix.length; j++) {
        if (i == j && this._adjMatrix[i][j] != 0) {
          return true;
        }
        if (this._adjMatrix[i][j] != this._adjMatrix[j][i]) {
          return true;
        }
      }
    }
    return false;
  }

  public String toString() {
    String str = "";
    for (int i = 0; i < this._adjMatrix.length; ++i) {
      for (int j = 0; j < this._adjMatrix[i].length; ++j) {
        str += this._adjMatrix[i][j] + "\t";
      }
      str += "\n";
    }
    return str;
  }

}