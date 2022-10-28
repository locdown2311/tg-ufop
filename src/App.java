import java.util.ArrayList;

import List.GraphList;
import Matrix.Graph;

public class App {
    public static void main(String[] args) throws Exception {
        // get textfile name from args
        String filename = args[0];
        String metodo = args[1];
        long[] duration = new long[1];
        long startTime;
        long endTime;
        GraphList graph = new GraphList(filename);
        switch (metodo) {
            case "dij":
                startTime = System.nanoTime();
                graph.djikstra(0,100);
                endTime = System.nanoTime();
                duration[0] = (endTime - startTime);
                System.out.println("Tempo de execução Djikstra: " + duration[0] + "ns");
                break;
            case "bf":
                startTime = System.nanoTime();
                graph.bellmanFord(0,100);
                endTime = System.nanoTime();
                duration[0] = (endTime - startTime);
                System.out.println("Tempo de execução Bellman-Ford: " + duration[0] + "ns");
                break;
            case "bfm":
                break;
            case "fw":
                break;
            default:
                // opções disponíveis
                System.out.println("Opções disponíveis: dij, bf, bfm, fw");
                break;
        }
        
        System.out.println("Djikstra: " + duration[0] + " ns");
        System.out.println("Floyd Warshall: " + duration[1] + " ns");
        System.out.println("Bellman Ford: " + duration[2] + " ns");
    }
}
