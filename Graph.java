import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

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

  public boolean hasCycle(int origem) {
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
      } else {
        return true;
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