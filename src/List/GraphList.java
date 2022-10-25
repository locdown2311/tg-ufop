package List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class GraphList {

    private int countNodes;
    private int countEdges;
    private ArrayList<ArrayList<Edge>> adjList;
    private ArrayList<Edge> edgeList;
    private ArrayList<ArrayList<Edge>> path;
    private static final int INF = 999999;

    public GraphList(int countNodes) {
        this.countNodes = countNodes;
        adjList = new ArrayList<>(this.countNodes);
        for (int i = 0; i < this.countNodes; ++i) {
            adjList.add(new ArrayList<Edge>());
        }
        edgeList = new ArrayList<>();
    }

    public GraphList(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);

        // Read header
        String[] line = bufferedReader.readLine().split(" ");
        this.countNodes = (Integer.parseInt(line[0]));
        int fileLines = (Integer.parseInt(line[1]));

        // Create and fill adjList with read edges
        adjList = new ArrayList<>(this.countNodes);
        for (int i = 0; i < this.countNodes; ++i) {
            adjList.add(new ArrayList<Edge>());
        }
        edgeList = new ArrayList<>();
        // Adds one edge at time
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

    public void addEdge(int source, int sink, int weight) {
        if (source < 0 || source > this.countNodes - 1
                || sink < 0 || sink > this.countNodes - 1 || weight <= 0) {
            System.err.println("Invalid edge: " + source + sink + weight);
            return;
        }
        adjList.get(source).add(new Edge(source, sink, weight));
        edgeList.add(new Edge(source, sink, weight));
        this.countEdges++;
    }

    public void addEdgeUnoriented(int source, int sink, int weight) {
        addEdge(source, sink, weight);
        addEdge(sink, source, weight);
    }

    public int degree(int u) {
        if (u < 0 || u > this.countNodes - 1)
            System.err.println("Invalid node: " + u);
        return this.adjList.get(u).size();
    }

    public int highestDegree() {
        int highest = 0;
        for (int u = 0; u < this.adjList.size(); ++u) {
            int degreeNodeU = this.degree(u);
            if (highest < degreeNodeU)
                highest = degreeNodeU;
        }
        return highest;
    }

    public int lowestDegree() {
        int lowest = this.countNodes;
        for (int u = 0; u < this.adjList.size(); ++u) {
            int degreeNodeU = this.degree(u);
            if (lowest > degreeNodeU)
                lowest = degreeNodeU;
        }
        return lowest;
    }

    public GraphList complement() {
        GraphList g2 = new GraphList(this.countNodes);
        for (int u = 0; u < this.adjList.size(); ++u) {
            for (int v = 0; v < this.countNodes; ++v) {
                boolean addEdgeUV = true;
                for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                    int v2 = this.adjList.get(u).get(idx).getSink();
                    if (v2 == v) { // Edge (u, v) exists and should not be added
                        addEdgeUV = false;
                        break;
                    }
                }
                if (addEdgeUV && u != v) {
                    g2.addEdge(u, v, 1); // It assumes edges are unweighted
                }
            }
        }
        return g2;
    }

    public float density() {
        return (float) this.countEdges / (this.countNodes * (this.countNodes - 1));
    }

    public boolean subgraph(GraphList g2) {
        if (g2.countNodes > this.countNodes || g2.countEdges > this.countEdges)
            return false;
        for (int u = 0; u < g2.adjList.size(); ++u) {
            boolean foundEdge = false;
            for (int idx = 0; idx < g2.adjList.get(u).size(); ++idx) {
                int v = g2.adjList.get(u).get(idx).getSink();
                // Check if edge (u,v) exists in this graph
                for (int idx2 = 0; idx2 < this.adjList.get(u).size(); ++idx2) {
                    int v2 = this.adjList.get(u).get(idx2).getSink();
                    if (v == v2) { // Edge exists
                        foundEdge = true;
                        break;
                    }
                }
                if (!foundEdge)
                    return false;
            }
        }
        return true;
    }

    // bfs
    public void bfs(int source) {
        if (source < 0 || source > this.countNodes - 1) {
            System.err.println("Invalid source: " + source);
            return;
        }
        boolean[] visited = new boolean[this.countNodes];
        int[] distance = new int[this.countNodes];
        int[] parent = new int[this.countNodes];
        for (int i = 0; i < this.countNodes; ++i) {
            visited[i] = false;
            distance[i] = INF;
            parent[i] = -1;
        }
        visited[source] = true;
        distance[source] = 0;
        parent[source] = -1;
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(source);
        while (!queue.isEmpty()) {
            int u = queue.remove(0);
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                if (!visited[v]) {
                    visited[v] = true;
                    distance[v] = distance[u] + 1;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }
        System.out.println("BFS:");
        for (int i = 0; i < this.countNodes; ++i) {
            System.out.println("Node " + i + " distance: " + distance[i] + " parent: " + parent[i]);
        }
    }

    // dfs
    public void dfs(int source) {
        if (source < 0 || source > this.countNodes - 1) {
            System.err.println("Invalid source: " + source);
            return;
        }
        boolean[] visited = new boolean[this.countNodes];
        int[] distance = new int[this.countNodes];
        int[] parent = new int[this.countNodes];
        for (int i = 0; i < this.countNodes; ++i) {
            visited[i] = false;
            distance[i] = INF;
            parent[i] = -1;
        }
        visited[source] = true;
        distance[source] = 0;
        parent[source] = -1;
        ArrayList<Integer> stack = new ArrayList<>();
        stack.add(source);
        while (!stack.isEmpty()) {
            int u = stack.remove(stack.size() - 1);
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                if (!visited[v]) {
                    visited[v] = true;
                    distance[v] = distance[u] + 1;
                    parent[v] = u;
                    stack.add(v);
                }
            }
        }
        System.out.println("DFS:");
        for (int i = 0; i < this.countNodes; ++i) {
            System.out.println("Node " + i + " distance: " + distance[i] + " parent: " + parent[i]);
        }
    }


    // connected
    public boolean connected() {
        boolean[] visited = new boolean[this.countNodes];
        for (int i = 0; i < this.countNodes; ++i) {
            visited[i] = false;
        }
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int u = queue.remove(0);
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }
        for (int i = 0; i < this.countNodes; ++i) {
            if (!visited[i])
                return false;
        }
        return true;
    }

    // is oriented
    public boolean isOriented() {
        for (int u = 0; u < this.countNodes; ++u) {
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                boolean foundEdge = false;
                for (int idx2 = 0; idx2 < this.adjList.get(v).size(); ++idx2) {
                    int u2 = this.adjList.get(v).get(idx2).getSink();
                    if (u == u2) {
                        foundEdge = true;
                        break;
                    }
                }
                if (!foundEdge)
                    return true;
            }
        }
        return false;
    }


    public ArrayList<Integer> dfsRec(int s) {
        int[] desc = new int[this.countNodes];
        ArrayList<Integer> R = new ArrayList<>();
        dfsRecAux(s, desc, R);
        return R;
    }

    public void dfsRecAux(int u, int[] desc, ArrayList<Integer> R) {
        desc[u] = 1;
        R.add(u);
        for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
            int v = this.adjList.get(u).get(idx).getSink();
            if (desc[v] == 0) {
                dfsRecAux(v, desc, R);
            }
        }
    }

    // return djikstra path
    public ArrayList<Integer> djikstra(int source, int sink) {
        if (source < 0 || source > this.countNodes - 1) {
            System.err.println("Invalid source: " + source);
            return null;
        }
        if (sink < 0 || sink > this.countNodes - 1) {
            System.err.println("Invalid sink: " + sink);
            return null;
        }
        boolean[] visited = new boolean[this.countNodes];
        int[] distance = new int[this.countNodes];
        int[] parent = new int[this.countNodes];
        for (int i = 0; i < this.countNodes; ++i) {
            visited[i] = false;
            distance[i] = INF;
            parent[i] = -1;
        }
        visited[source] = true;
        distance[source] = 0;
        parent[source] = -1;
        ArrayList<Integer> queue = new ArrayList<>();
        queue.add(source);
        while (!queue.isEmpty()) {
            int u = queue.remove(0);
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                if (!visited[v]) {
                    visited[v] = true;
                    distance[v] = distance[u] + 1;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }
        ArrayList<Integer> path = new ArrayList<>();
        int u = sink;
        while (u != -1) {
            path.add(u);
            u = parent[u];
        }
        return path;
    }


    
    //bellman-ford
    public ArrayList<Integer> bellmanFord(int source, int sink) {
        if (source < 0 || source > this.countNodes - 1) {
            System.err.println("Invalid source: " + source);
            return null;
        }
        if (sink < 0 || sink > this.countNodes - 1) {
            System.err.println("Invalid sink: " + sink);
            return null;
        }
        int[] distance = new int[this.countNodes];
        int[] parent = new int[this.countNodes];
        for (int i = 0; i < this.countNodes; ++i) {
            distance[i] = INF;
            parent[i] = -1;
        }
        distance[source] = 0;
        parent[source] = -1;
        for (int i = 0; i < this.countNodes - 1; ++i) {
            for (int u = 0; u < this.countNodes; ++u) {
                for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                    int v = this.adjList.get(u).get(idx).getSink();
                    int w = this.adjList.get(u).get(idx).getWeight();
                    if (distance[u] + w < distance[v]) {
                        distance[v] = distance[u] + w;
                        parent[v] = u;
                    }
                }
            }
        }
        ArrayList<Integer> path = new ArrayList<>();
        int u = sink;
        while (u != -1) {
            path.add(u);
            u = parent[u];
        }
        return path;
    }
    //return path from source to sink floyd-warshall
    public ArrayList<Integer> floydWarshall(int source, int sink) {
        if (source < 0 || source > this.countNodes - 1) {
            System.err.println("Invalid source: " + source);
            return null;
        }
        if (sink < 0 || sink > this.countNodes - 1) {
            System.err.println("Invalid sink: " + sink);
            return null;
        }
        int[][] distance = new int[this.countNodes][this.countNodes];
        int[][] parent = new int[this.countNodes][this.countNodes];
        for (int i = 0; i < this.countNodes; ++i) {
            for (int j = 0; j < this.countNodes; ++j) {
                distance[i][j] = INF;
                parent[i][j] = -1;
            }
        }
        for (int u = 0; u < this.countNodes; ++u) {
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                int w = this.adjList.get(u).get(idx).getWeight();
                distance[u][v] = w;
                parent[u][v] = u;
            }
        }
        for (int k = 0; k < this.countNodes; ++k) {
            for (int i = 0; i < this.countNodes; ++i) {
                for (int j = 0; j < this.countNodes; ++j) {
                    if (distance[i][k] + distance[k][j] < distance[i][j]) {
                        distance[i][j] = distance[i][k] + distance[k][j];
                        parent[i][j] = parent[k][j];
                    }
                }
            }
        }
        ArrayList<Integer> path = new ArrayList<>();
        int u = sink;
        while (u != -1) {
            path.add(u);
            u = parent[source][u];
        }
        return path;
    }

    // return path from source to sink kruskal
    public ArrayList<Integer> kruskal(int source, int sink) {
        if (source < 0 || source > this.countNodes - 1) {
            System.err.println("Invalid source: " + source);
            return null;
        }
        if (sink < 0 || sink > this.countNodes - 1) {
            System.err.println("Invalid sink: " + sink);
            return null;
        }
        ArrayList<Edge> edges = new ArrayList<>();
        for (int u = 0; u < this.countNodes; ++u) {
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                int w = this.adjList.get(u).get(idx).getWeight();
                edges.add(new Edge(u, v, w));
            }
        }
        Collections.sort(edges);
        int[] parent = new int[this.countNodes];
        for (int i = 0; i < this.countNodes; ++i) {
            parent[i] = i;
        }
        ArrayList<Integer> path = new ArrayList<>();
        for (int i = 0; i < edges.size(); ++i) {
            int u = edges.get(i).getSource();
            int v = edges.get(i).getSink();
            int w = edges.get(i).getWeight();
            int pu = find(u, parent);
            int pv = find(v, parent);
            if (pu != pv) {
                parent[pu] = pv;
                path.add(u);
                path.add(v);
            }
        }
        return path;
    }

    // ASCII MAZE to GraphList from file
    // # = wallW 
    // U+2588 = wall
    // whitespace = path
    // S = start
    // E = end
    // Find path from S to E
    // Return GraphList
    
    public static GraphList asciiMazeToGraphList(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            int countNodes = 0;
            int countEdges = 0;
            int source = -1;
            int sink = -1;
            int width = line.length();
            int height = 0;
            while (line != null) {
                for (int i = 0; i < line.length(); ++i) {
                    if (line.charAt(i) == 'S') {
                        source = countNodes;
                    } else if (line.charAt(i) == 'E') {
                        sink = countNodes;
                    }
                    ++countNodes;
                }
                line = br.readLine();
                ++height;
            }
            br.close();
            br = new BufferedReader(new FileReader(filename));
            line = br.readLine();
            GraphList graph = new GraphList(countNodes);
            int u = 0;
            while (line != null) {
                for (int i = 0; i < line.length(); ++i) {
                    if (line.charAt(i) == ('#' | '\u2588')) {
                        ++u;
                        continue;
                    }
                    if (i > 0 && line.charAt(i - 1) != ('#' | '\u2588')) {
                        graph.addEdge(u, u - 1, 1);
                        ++countEdges;
                    }
                    if (i < line.length() - 1 && line.charAt(i + 1) != ('#' | '\u2588')) {
                        graph.addEdge(u, u + 1, 1);
                        ++countEdges;
                    }
                    if (u >= width) {
                        graph.addEdge(u, u - width, 1);
                        ++countEdges;
                    }
                    if (u < countNodes - width) {
                        graph.addEdge(u, u + width, 1);
                        ++countEdges;
                    }
                    ++u;
                }
                line = br.readLine();
            }
            br.close();
            return graph;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    //find
    public int find(int u, int[] parent) {
        if (u != parent[u]) {
            parent[u] = find(parent[u], parent);
        }
        return parent[u];
    }
    public ArrayList<Edge> prim() {
        ArrayList<Edge> T = new ArrayList<Edge>(this.countNodes - 1);
        int s = 0;
        int[] dist = new int[this.countNodes];
        int[] parent = new int[this.countNodes];
        // Q represents the nodes that were not connected yet
        ArrayList<Integer> Q = new ArrayList<Integer>(this.countNodes);
        for (int u = 0; u < this.countNodes; ++u) {
            dist[u] = INF;
            parent[u] = -1;
            Q.add(u);
        }
        dist[s] = 0;
        while (Q.size() != 0) {
            int u = -1;
            int min = INF;
            for (int idx = 0; idx < Q.size(); ++idx) {
                int i = Q.get(idx);
                if (dist[i] < min) {
                    min = dist[i];
                    u = i;
                }
            }
            // Node u is gonna be connected
            Q.remove((Integer) u);
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                int v = this.adjList.get(u).get(idx).getSink();
                int w = this.adjList.get(u).get(idx).getWeight();
                if (Q.contains(v) && w < dist[v]) {
                    dist[v] = w;
                    parent[v] = u;
                }
            }
        }
        // Recover the tree from parent array
        for (int u = 0; u < parent.length; ++u) {
            if (parent[u] != -1) {
                T.add(new Edge(u, parent[u], 1));
            }
        }
        return T;
    }


    public int getCountNodes() {
        return countNodes;
    }

    public void setCountNodes(int countNodes) {
        this.countNodes = countNodes;
    }

    public int getCountEdges() {
        return countEdges;
    }

    public void setCountEdges(int countEdges) {
        this.countEdges = countEdges;
    }

    public ArrayList<ArrayList<Edge>> getAdjList() {
        return adjList;
    }

    public void setAdjList(ArrayList<ArrayList<Edge>> adjList) {
        this.adjList = adjList;
    }

    public String toString() {
        String str = "";
        for (int u = 0; u < this.adjList.size(); ++u) {
            str += u + ": ";
            for (int idx = 0; idx < this.adjList.get(u).size(); ++idx) {
                str += this.adjList.get(u).get(idx) + "\t";
            }
            str += "\n";
        }
        return str;
    }

}