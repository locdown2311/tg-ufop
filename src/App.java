import java.util.ArrayList;

import List.GraphList;
import Matrix.Graph;

public class App {
    public static void main(String[] args) throws Exception {
        //GraphList graph = new GraphList("./src/txt/toy.txt");

        //long[] duration = new long[3];
        // measure time for djikstra
        
        /* long startTime = System.nanoTime();
        graph.djikstra(0,3);
        long endTime = System.nanoTime();
        duration[0] = (endTime - startTime);

        startTime = System.nanoTime();
        graph.floydWarshall(0, 3);
        endTime = System.nanoTime();
        duration[1] = (endTime - startTime);

        startTime = System.nanoTime();
        graph.bellmanFord(0, 3);
        endTime = System.nanoTime();
        duration[2] = (endTime - startTime);
 */

        GraphList maze = GraphList.asciiMazeToGraphList("./src/txt/maze3_blocks.txt");
        System.out.println(maze.djikstra(0, 68));
        

        // print time for djikstra, floyd warshall and bellman ford
        /* System.out.println("Djikstra: " + duration[0]+" ns");
        System.out.println("Floyd Warshall: " + duration[1] + " ns");
        System.out.println("Bellman Ford: " + duration[2]+ " ns"); */
    }
}
