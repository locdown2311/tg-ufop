import List.GraphList;

public class App {
    public static void main(String[] args) throws Exception {
        GraphList graph = new GraphList("./src/g1.txt");
        graph.addEdge(0, 1, 2);
        System.out.println(graph);

    }
}
