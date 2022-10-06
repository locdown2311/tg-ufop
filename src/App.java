import java.util.ArrayList;

import List.GraphList;

public class App {
    public static void main(String[] args) throws Exception {
        GraphList graph = new GraphList("./src/g1.txt");
        ArrayList<Integer>caminho =  graph.djikstra(0,2127);
        System.out.println(caminho);
    }
}
