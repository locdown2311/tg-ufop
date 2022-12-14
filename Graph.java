import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Graph {
  private int _countNodes;
  private int _countEdges;
  private int[][] _adjMatrix;

  public Graph(int numNodes) {
    this._countNodes = numNodes;
    this._countEdges = 0;
    this._adjMatrix = new int[numNodes][numNodes];
  }

  public Graph(String fileName) throws IOException {
    File file = new File(fileName);
    FileReader reader = new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(reader);

    // Read header
    String[] line = bufferedReader.readLine().split(" ");
    this._countNodes = (Integer.parseInt(line[0]));
    int fileLines = (Integer.parseInt(line[1]));

    // Create and fill adjMatrix with read edges
    this._adjMatrix = new int[this._countNodes][this._countNodes];
    for (int i = 0; i < fileLines; ++i) {
      String[] edgeInfo = bufferedReader.readLine().split(" ");
      int source = Integer.parseInt(edgeInfo[0]);
      int sink = Integer.parseInt(edgeInfo[1]);
      int weight = Integer.parseInt(edgeInfo[2]);
      addEdge(source, sink, weight);
    }
    bufferedReader.close();
    reader.close();
  }

  int minDistance(int path_array[], Boolean predSet[]) {
    int min = Integer.MAX_VALUE, min_index = -1;
    for (int v = 0; v < this._countNodes; v++)
      if (predSet[v] == false && path_array[v] <= min) {
        min = path_array[v];
        min_index = v;
      }

    return min_index;
  }

  void printMinpath(int path_array[]) {
    System.out.println("Nó# \t Distancia da Origem");
    for (int i = 0; i < this._countNodes; i++)
      System.out.println(i + " \t\t\t " + path_array[i]);
  }

  public void dijkstra(int origem) {
    int dist[] = new int[this._countNodes];
    Boolean pred[] = new Boolean[this._countNodes];

    for (int i = 0; i < dist.length; i++) {
      dist[i] = Integer.MAX_VALUE;
      pred[i] = false;
    }
    dist[origem] = 0;
    for (int count = 0; count < this._countNodes - 1; count++) {
      int u = minDistance(dist, pred);
      pred[u] = true;
      for (int v = 0; v < this._countNodes; v++)
        if (!pred[v] && this._adjMatrix[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u]
            + this._adjMatrix[u][v] < dist[v])
          dist[v] = dist[u] + this._adjMatrix[u][v];
    }
    printMinpath(dist);
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

  public int verificaAdjacente(int u, int[] desc) {
    for (int v = 0; v < this._adjMatrix[u].length; ++v) {
      if (this._adjMatrix[u][v] != 0) {
        if (desc[v] == 0) {
          return v;
        }
      }
    }
    return -1;
  }

  /*
   * public int verificaAdjacentev2(int u, int[] desc) {
   * for (int v = 0; v < this._adjMatrix[u].length; ++v) {
   * if (this._adjMatrix[u][v] != 0) {
   * return v;
   * }
   * }
   * return -1;
   * }
   */

  public List<Integer> BuscaLargura(int origem) {
    List<Integer> q = new ArrayList<Integer>();
    List<Integer> r = new ArrayList<Integer>();
    int desc[] = new int[this._countNodes];
    q.add(origem);
    r.add(origem);
    desc[origem] = 1;

    while (!q.isEmpty()) {
      int u = q.remove(0);
      for (int v = 0; v < _adjMatrix[u].length; ++v) {
        if (this._adjMatrix[u][v] != 0) { // verifica adjacente
          if (desc[v] == 0) {
            q.add(v);
            r.add(v);
            desc[v] = 1;
          }
        }
      }
    }
    return r;
  }

  public List<Integer> BuscaLarguraV2(int origem) {
    List<Integer> q = new ArrayList<Integer>();
    List<Integer> r = new ArrayList<Integer>();
    int desc[] = new int[this._countNodes];
    q.add(origem);
    r.add(origem);
    desc[origem] = 1;

    while (!q.isEmpty()) {
      int u = q.remove(0);
      int v = verificaAdjacente(u, desc);
      if (v != -1) {
        q.add(v);
        r.add(v);
        desc[v] = 1;
      }
    }
    return r;
  }

  // ordenação topológica só se aplica a DAE
  // TOP SORT
  public ArrayList<Integer> topSort() {
    int[] desc = new int[this._countNodes];
    ArrayList<Integer> R = new ArrayList<>();
    for (int v = 0; v < this._adjMatrix.length; ++v) {
      if (desc[v] == 0)
        topSortAux(v, desc, R);
    }
    return R;
  }

  public void topSortAux(int u, int[] desc, ArrayList<Integer> R) {
    desc[u] = 1;
    for (int v = 0; v < this._adjMatrix[u].length; ++v) {
      if (this._adjMatrix[u][v] != 0 && desc[v] == 0)
        topSortAux(v, desc, R);
    }
    R.add(0, u);
  }

  public int[] connectedComp() {
    int[] desc = new int[this._countNodes];
    int comp = 0;
    for (int v = 0; v < this._adjMatrix.length; ++v) {
      if (desc[v] == 0) {
        comp++;
        connectedCompAux(v, desc, comp);
      }
    }
    return desc;
  }

  public void connectedCompAux(int u, int[] desc, int comp) {
    desc[u] = comp;
    for (int v = 0; v < this._adjMatrix[u].length; ++v) {
      if (this._adjMatrix[u][v] != 0) {
        if (desc[v] == 0) {
          connectedCompAux(v, desc, comp);
        }
      }
    }
  }

  public boolean hasCycleOriented(int origem) {
    int desc[] = new int[this._countNodes];

    List<Integer> q = new ArrayList<Integer>();
    List<Integer> r = new ArrayList<Integer>();
    for (int s = 0; s < this._countNodes; ++s) {
      if (desc[s] == 0) {
        q.add(origem);
        r.add(origem);
        desc[origem] = 1;

        while (!q.isEmpty()) {
          int u = q.remove(0);
          int v = verificaAdjacente(u, desc);
          if (v != -1) {
            q.add(v);
            r.add(v);
            desc[v] = 1;
          } else {
            return true;
          }
        }
      }
    }

    return false;
  }

  public List<Integer> descRec(int origem) {
    int desc[] = new int[this._countNodes];
    List<Integer> r = new ArrayList<Integer>();
    descRecAux(origem, desc, r);
    return r;
  }

  public void descRecAux(int u, int[] desc, List<Integer> r) {
    desc[u] = 1;
    r.add(u);
    int v = verificaAdjacente(u, desc);
    if (v != -1) {
      descRecAux(v, desc, r);
    }
  }

  /*
   * public List<Integer> BuscaProfundidade(int origem) {
   * Stack<Integer> s = new Stack<Integer>();
   * List<Integer> r = new ArrayList<Integer>();
   * int desc[] = new int[this._countNodes];
   * desc[origem] = 1;
   * while (!s.empty()) {
   * int u = s.peek();
   * int v = verificaAdjacente(u, desc);
   * if (v != -1) {
   * s.push(v);
   * r.add(v);
   * desc[v] = 1;
   * } else {
   * s.pop();
   * }
   * }
   * return r;
   * }
   */
  public ArrayList<Integer> dfs(int s) {
    // initialization
    int[] desc = new int[this._countNodes];
    ArrayList<Integer> S = new ArrayList<>();
    S.add(s);
    ArrayList<Integer> R = new ArrayList<>();
    R.add(s);
    desc[s] = 1;
    // main loop
    while (S.size() > 0) {
      int u = S.get(S.size() - 1);
      int v = verificaAdjacente(u, desc);
      if (v != -1) {
        S.add(v);
        R.add(v);
        desc[v] = 1;
      } else {
        S.remove(S.size() - 1);
      }
    }
    return R;
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

  public boolean connected() {
    return this.BuscaLargura(0).size() == this._countNodes;
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