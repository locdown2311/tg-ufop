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
                graph.bellmanford(0, 0);
                endTime = System.nanoTime();
                duration[0] = (endTime - startTime);
                System.out.println("Tempo de execução Bellman-Ford: " + duration[0] + "ns");
                break;
            case "bfm":
                startTime = System.nanoTime();
                graph.bellmanFordMelhorado(0, 100);
                endTime = System.nanoTime();
                duration[0] = (endTime - startTime);
                System.out.println("Tempo de execução Bellman-Ford Melhorado: " + duration[0] + "ns");
                break;
            case "fw":
                break;
            default:
                // opções disponíveis
                System.out.println("Opções disponíveis: dij, bf, bfm, fw");
                break;
        }
    }
}
