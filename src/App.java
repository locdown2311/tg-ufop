import List.GraphList;

public class App {
    public static void main(String[] args) throws Exception {
        GraphList g1 = new GraphList(5);
        g1.addEdgeOriented(0, 1);
        g1.addEdgeOriented(0, 2);
        g1.addEdgeOriented(1, 3);
        g1.printGraph();
    }
}
