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
                ArrayList<Integer> caminho = graph.bellmanFord(0, 100);
                endTime = System.nanoTime();
                duration[0] = (endTime - startTime);
                System.out.println("Tempo de execução Bellman-Ford (Normal): " + duration[0] + "ns");
                System.out.println("Caminho: " + caminho);
                break;
            case "bfm":
                startTime = System.nanoTime();
                graph.bellmanfordMelhorado(0, 100);
                endTime = System.nanoTime();
                duration[0] = (endTime - startTime);
                System.out.println("Tempo de execução Bellman-Ford Melhorado: " + duration[0] + "ns");
                break;
            case "fw":
                startTime = System.nanoTime();
                caminho = graph.floydWarshall(0, 100);
                endTime = System.nanoTime();
                duration[0] = (endTime - startTime);
                System.out.println("Tempo de execução Floyd-Warshall: " + duration[0] + "ns");
                System.out.println("Caminho: " + caminho);
                break;
            default:
                // opções disponíveis
                System.out.println("Opções disponíveis: dij, bf, bfm, fw");
                break;
        }
    }
}
