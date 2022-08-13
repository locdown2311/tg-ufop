class Graph {
  private int _countNodes;
  private int _countEdges;
  private int[][] _adjMatrix;

  public Graph(int numNodes) {
    this._countNodes = numNodes;
    this._countEdges = 0;
    this._adjMatrix = new int[numNodes][numNodes];
  }

  public void addEdge(int source, int sink, int weight) {
    if (source < 0 || source > _adjMatrix.length - 1 || sink < 0 || sink > _adjMatrix.length - 1 || weight <= 0) {
      System.err.println("Invalid edge: " + source + sink + weight);
      return;
    }
    this._countEdges++;
    this._adjMatrix[source][sink] = weight;
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

  public int highestDegree() {
    int maiorGrau = 0;
    for (int i = 0; i < _countNodes; i++) {
      if (maiorGrau < degree(i)) {
        maiorGrau = degree(i);
      }
    }
    return maiorGrau;
  }
  public int lowestDegree(){
    int menorGrau = 50000;
    for (int i = 0; i < _countNodes; i++) {
      if (menorGrau < degree(i)) {
        menorGrau = degree(i);
      }
    }
    return menorGrau;
  }

  public String toString() {
    String str = "";
    for (int i = 0; i < this._adjMatrix.length; ++i) {
      for (int j = 0; i < this._adjMatrix[i].length; ++j) {
        str += this._adjMatrix[i][j] + "\t";
      }
      str += "\n";
    }
    return str;
  }

}